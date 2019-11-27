package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLatestCommentFragmentComponent;
import com.shushan.manhua.di.modules.LatestCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.mvp.ui.activity.book.CommentDetailsActivity;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.ui.dialog.CommentSoftKeyPopupWindow;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.PicUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 最新评论
 */

public class LatestCommentFragment extends BaseFragment implements LatestCommentFragmentControl.LatestCommentView, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener,
        TakePhoto.TakeResultListener, InvokeListener {

    @Inject
    LatestCommentFragmentControl.LatestCommentFragmentPresenter mPresenter;
    @SuppressLint("StaticFieldLeak")
    private static LatestCommentFragment mLatestCommentFragment;
    @BindView(R.id.latest_comment_layout)
    RelativeLayout mLatestCommentLayout;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private ReadingCommentAdapter mReadingCommentAdapter;
    List<CommentBean> readingCommendResponseList = new ArrayList<>();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //成功取得照片
    Bitmap bitmap;
    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    private CommentSoftKeyPopupWindow mCommentSoftKeyPopupWindow;
    private int page = 1;
    /**
     * 上传成功后图片集合
     */
    private List<String> mPicList = new ArrayList<>();
    private String mBookId;
    private String mContent;//评论内容

    public static LatestCommentFragment getInstance(String bookId) {
        if (mLatestCommentFragment == null) {
            mLatestCommentFragment = new LatestCommentFragment();
        }
        Bundle bd = new Bundle();
        bd.putString("bookId", bookId);
        mLatestCommentFragment.setArguments(bd);
        return mLatestCommentFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_latest_comment, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.UPDATE_COMMENT_LIST)) {
                onRequestCommentInfo();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_COMMENT_LIST);
    }

    @Override
    public void initView() {
        File file = new File(Objects.requireNonNull(getActivity()).getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        if (getArguments() != null) {
            mBookId = getArguments().getString("bookId");
            onRequestCommentInfo();
        }
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mReadingCommentAdapter);
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.suggest_num_tv:
                    showToast("点赞");
                    break;
                case R.id.item_comment_layout:
                    startActivitys(CommentDetailsActivity.class);
                    break;
            }
        });

    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.comment_content_rl, R.id.publish_comment_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comment_content_rl:
                showCommentPopupWindow();
                break;
            case R.id.publish_comment_tv:
                break;
        }
    }

    private void onRequestCommentInfo() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.book_id = mBookId;
        commentRequest.type = "1";//1为漫画评论
        commentRequest.catalogue_id = "0";//0表示评价漫画
        commentRequest.state = "0";// 0最新1最热
        commentRequest.page = String.valueOf(page);
        commentRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestCommentInfo(commentRequest);
    }

    @Override
    public void getCommentInfoSuccess(CommentListBean commentListBean) {
        mReadingCommentAdapter.setNewData(commentListBean.getData());
    }

    /**
     * 发送评论
     */
    @Override
    public void CommentSendMessageBtnListener(List<TImage> tImageList, String content) {
        mContent = content;
        if (tImageList.size() > 0) {
            for (TImage tImage : tImageList) {
                Bitmap bitmap = BitmapFactory.decodeFile(tImage.getCompressPath());
                String path = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
                uploadImage(path);
            }
        } else {
            publishComment();
        }
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
        mPicList.add(picPath);
        if (mPicList != null && mPicList.size() == photoList.size()) {
            //上传完最后一张图片 发布评论
            publishComment();
        }
    }


    /**
     * 发布评论
     */
    private void publishComment() {
        PublishCommentRequest publishCommentRequest = new PublishCommentRequest();
        publishCommentRequest.token = mBuProcessor.getToken();
        publishCommentRequest.book_id = mBookId;
        publishCommentRequest.catalogue_id = "0";
        publishCommentRequest.comment = mContent;
        publishCommentRequest.pics = new Gson().toJson(mPicList);
        LogUtils.e("publishCommentRequest:" + new Gson().toJson(publishCommentRequest));
        mPresenter.onRequestPublishComment(publishCommentRequest);
    }


    @Override
    public void getPublishCommentSuccess() {
        showToast("发布成功");
        //刷新页面
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(new Intent(ActivityConstant.UPDATE_COMMENT_LIST));
    }

    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow() {
        mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(getActivity(), this, photoList);
        mCommentSoftKeyPopupWindow.initPopWindow(mLatestCommentLayout);
    }


    @Override
    public void switchFunctionByCommentSoftKeyBtnListener() {

    }

    @Override
    public void photoBtnListener() {
        //从相机获取图片(不裁剪)
        getTakePhoto().onPickFromCapture(uri);
    }

    @Override
    public void albumBtnListener(int maxPicNum) {
        //从相册中获取图片（不裁剪）
        getTakePhoto().onPickMultiple(maxPicNum);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
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
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), true);
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
//        photoList.add(null);
        photoList.addAll(result.getImages());
        //传到CommentSoftKeyPopupWindow
//        new CommentSoftKeyPopupWindow(ReadActivity.this, ReadActivity.this, photoList).initPopWindow(mReadLayout);
        mCommentSoftKeyPopupWindow.setListData(photoList, this);

    }


    private void initializeInjector() {
        DaggerLatestCommentFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .moreCommentModule(new MoreCommentModule((AppCompatActivity) getActivity()))
                .latestCommentFragmentModule(new LatestCommentFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
