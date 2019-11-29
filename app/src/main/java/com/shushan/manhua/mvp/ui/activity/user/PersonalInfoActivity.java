package com.shushan.manhua.mvp.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerPersonalInfoComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.PersonalInfoModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.PersonalInfoRequest;
import com.shushan.manhua.entity.request.UpdatePersonalInfoRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.entity.response.PersonalInfoResponse;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.AvatarPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.EditNameDialog;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.PicUtils;
import com.shushan.manhua.mvp.utils.SelectDialogUtil;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalInfoActivity extends BaseActivity implements PersonalInfoControl.PersonalInfoView, AvatarPopupWindow.PopupWindowListener, TakePhoto.TakeResultListener,
        InvokeListener, EditNameDialog.EditNameDialogListener {

    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;
    @BindView(R.id.personal_info_layout)
    ConstraintLayout mPersonalInfoLayout;
    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.nick_tv)
    TextView mNickTv;
    @BindView(R.id.sex_tv)
    TextView mSexTv;
    @BindView(R.id.birthday_tv)
    TextView mBirthdayTv;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //裁剪使用
    private CropOptions cropOptions;
    //成功取得照片
    Bitmap bitmap;
    private String mAvatarPicPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_personal_info);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();

    }

    @Override
    public void initView() {
        onRequestPersonalInfo();
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_back_iv, R.id.save_tv, R.id.avatar_iv, R.id.nick_tv, R.id.sex_tv, R.id.birthday_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.save_tv://保存
                updatePersonalInfoRequest();
                break;
            case R.id.avatar_iv://头像
                //弹出popupWindow框
                new AvatarPopupWindow(this, this).initPopWindow(mPersonalInfoLayout);
                break;
            case R.id.nick_tv:
                EditNameDialog editNameDialog = EditNameDialog.newInstance();
                editNameDialog.setListener(this);
                editNameDialog.setName(mNickTv.getText().toString());
                DialogFactory.showDialogFragment(getSupportFragmentManager(), editNameDialog, EditNameDialog.TAG);
                break;
            case R.id.sex_tv:
                showSexDialog();
                break;
            case R.id.birthday_tv:
                showBirthdayDialog();
                break;
        }
    }

    /**
     * 查询个人基本信息
     */
    private void onRequestPersonalInfo() {
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest();
        personalInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestPersonalInfo(personalInfoRequest);
    }

    @Override
    public void getPersonalInfoResponse(PersonalInfoResponse personalInfoResponse) {
        mImageLoaderHelper.displayImage(this, personalInfoResponse.getHead_portrait(), mAvatarIv, R.mipmap.head_default_01);
        mNickTv.setText(personalInfoResponse.getName());
        if (personalInfoResponse.getSex() == 0) {//0未填写 1男2女
            mSexTv.setHint(getString(R.string.PersonalInfoActivity_select));
        } else if (personalInfoResponse.getSex() == 1) {
            mSexTv.setText(getString(R.string.PersonalInfoActivity_sex_man));
        } else if (personalInfoResponse.getSex() == 2) {
            mSexTv.setText(getString(R.string.PersonalInfoActivity_sex_female));
        }
        if (personalInfoResponse.getBirthday() == 0) {
            mBirthdayTv.setHint(getString(R.string.PersonalInfoActivity_select));
        } else {
            mBirthdayTv.setText(DateUtil.getStrTime(personalInfoResponse.getBirthday(),DateUtil.TIME_YYMMDD));
        }

    }


    /**
     * 选择性别
     */
    private void showSexDialog() {
        List<String> sexStrList = new ArrayList<>();
        sexStrList.add(getString(R.string.PersonalInfoActivity_sex_man));
        sexStrList.add(getString(R.string.PersonalInfoActivity_sex_female));
        new SelectDialogUtil(this, new SelectDialogUtil.SelectPickerListener() {
            @Override
            public void getSelectText(String text) {
                mSexTv.setText(text);
            }

            @Override
            public void getSelectDate(Date date) {
            }
        }).selectText(getString(R.string.PersonalInfoActivity_sex_hint), sexStrList);
    }

    /**
     * 选择生日弹框
     */
    private void showBirthdayDialog() {
        new SelectDialogUtil(this, new SelectDialogUtil.SelectPickerListener() {
            @Override
            public void getSelectText(String text) {
            }

            @Override
            public void getSelectDate(Date date) {
                String dateTitle = DateUtil.dateTranString(date, DateUtil.TIME_YYMMDD);
                String selectDate = DateUtil.getTime(dateTitle, DateUtil.TIME_YYMMDD);
                mBirthdayTv.setText(dateTitle);
//                onRequestFeedback(selectDate, selectSubject);
            }
        }).showBirthdayDialog();
    }


    @Override
    public void takeSuccess(TResult result) {
        bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        String path = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
        mAvatarIv.setImageBitmap(bitmap);
//        LogUtils.d("path:" + result.getImage().getCompressPath());
        //上传图片
        uploadImage(path);
    }

    /**
     * 上传图片
     */
    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.pic = filename;
        mPresenter.uploadImageRequest(uploadImage);
    }

    @Override
    public void getUploadPicSuccess(String picPath) {
        LogUtils.e("picPath:" + picPath);
        mAvatarPicPath = picPath;
    }


    /**
     * 更新个人信息
     */
    private void updatePersonalInfoRequest() {
        UpdatePersonalInfoRequest updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
        updatePersonalInfoRequest.token = mBuProcessor.getToken();
        updatePersonalInfoRequest.name = mNickTv.getText().toString();
        updatePersonalInfoRequest.head_portrait = mAvatarPicPath;
        // 1男2女0未填写（可选）
        String sexValue = mSexTv.getText().toString();
        if (TextUtils.isEmpty(sexValue)) {
            updatePersonalInfoRequest.sex = "0";
        } else if (sexValue.equals(getString(R.string.PersonalInfoActivity_sex_man))) {
            updatePersonalInfoRequest.sex = "1";
        } else if (sexValue.equals(getString(R.string.PersonalInfoActivity_sex_female))) {
            updatePersonalInfoRequest.sex = "2";
        }
        String birthdayValue = mBirthdayTv.getText().toString();
        if (!TextUtils.isEmpty(birthdayValue)) {
            updatePersonalInfoRequest.birthday = DateUtil.getTime(birthdayValue, DateUtil.TIME_YYMMDD);
        }
        mPresenter.updatePersonalInfoRequest(updatePersonalInfoRequest);
    }

    @Override
    public void getUpdatePersonalInfoSuccess() {
        showToast("更新成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
        finish();
    }


    @Override
    public void editNameBtnOkListener(String nameValue) {
        mNickTv.setText(nameValue);
    }


    @Override
    public void takePhotoBtnListener() {
        //拍照进行裁剪
        takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
    }

    @Override
    public void albumBtnListener() {
        //从相册中选取进行裁剪
        takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), false);
        return takePhoto;
    }


    private void initInjectData() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
