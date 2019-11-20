package com.shushan.manhua.mvp.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerPersonalInfoComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.PersonalInfoModule;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.AvatarPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.EditNameDialog;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.utils.PicUtils;
import com.shushan.manhua.mvp.utils.SelectDialogUtil;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

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
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalInfoActivity extends BaseActivity implements PersonalInfoControl.PersonalInfoView, AvatarPopupWindow.PopupWindowListener, TakePhoto.TakeResultListener,
        InvokeListener, EditNameDialog.EditNameDialogListener {

    @BindView(R.id.personal_info_layout)
    ConstraintLayout mPersonalInfoLayout;
    @BindView(R.id.avatar_iv)
    ImageView mAvatarIv;
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
//        BuyAdapter
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_back_iv, R.id.save_tv, R.id.avatar_iv, R.id.nick_tv, R.id.birthday_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.save_tv://保存
                showToast("保存");
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
            case R.id.birthday_tv:
                showBirthdayDialog();
                break;
        }
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
//        UploadImage uploadImage = new UploadImage();
//        uploadImage.pic = filename;
//        mPresenter.uploadImageRequest(uploadImage);
    }

    @Override
    public void editNameBtnOkListener(String nameValue) {
        showToast(nameValue);
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
