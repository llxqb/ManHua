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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHotCommentFragmentComponent;
import com.shushan.manhua.di.modules.HotCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.ui.dialog.CommentSoftKeyPopupWindow;
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
 * 最热评论
 */

public class HotCommentFragment extends BaseFragment implements HotCommentFragmentControl.HotCommentView, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener, TakePhoto.TakeResultListener,
        InvokeListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    HotCommentFragmentControl.HotCommentFragmentPresenter mPresenter;
    @SuppressLint("StaticFieldLeak")
    private static HotCommentFragment mHotCommentFragment;
    Unbinder unbinder;
    @BindView(R.id.hot_comment_layout)
    RelativeLayout mHotCommentLayout;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
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
    CommentBean mCommentBean;
    private int clickPos;
    private View mEmptyView;

    public static HotCommentFragment getInstance(String bookId) {
        if (mHotCommentFragment == null) {
            mHotCommentFragment = new HotCommentFragment();
        }
        Bundle bd = new Bundle();
        bd.putString("bookId", bookId);
        mHotCommentFragment.setArguments(bd);
        return mHotCommentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_comment, container, false);
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
        mReadingCommentAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            clickPos = position;
            mCommentBean = (CommentBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.suggest_num_tv:
                    onCommentSuggestRequest();
                    break;
                case R.id.content_tv:
                    showCommentPopupWindow("@" + mCommentBean.getName());
                    break;
                case R.id.item_comment_layout:
                    showCommentPopupWindow("@" + mCommentBean.getName());
                    break;
            }
        });
    }

    @Override
    public void initData() {
        initEmptyView();
    }

    @OnClick({R.id.comment_content_rl, R.id.publish_comment_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comment_content_rl:
                showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
                break;
            case R.id.publish_comment_tv:
                showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
                break;
        }
    }


    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow(String editHintContent) {
        mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(getActivity(), this, photoList,editHintContent);
        mCommentSoftKeyPopupWindow.initPopWindow(mHotCommentLayout);
    }


    /**
     * 请求评论列表
     */
    private void onRequestCommentInfo() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.token = mBuProcessor.getToken();
        commentRequest.book_id = mBookId;
        commentRequest.type = "1";//1为漫画评论
        commentRequest.catalogue_id = "0";//0表示评价漫画
        commentRequest.state = "1";// 0最新1最热
        commentRequest.page = String.valueOf(page);
        commentRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestCommentInfo(commentRequest);
    }

    boolean isReqState = false;//加载更多 正在请求状态

    @Override
    public void onLoadMoreRequested() {
        if(!isReqState){
            if (!readingCommendResponseList.isEmpty()) {
                if (page == 1 && readingCommendResponseList.size() < Constant.PAGESIZE) {
                    mReadingCommentAdapter.loadMoreEnd(true);
                } else {
                    if (readingCommendResponseList.size() < Constant.PAGESIZE) {
                        mReadingCommentAdapter.loadMoreEnd();
                    } else {
                        //等于10条
                        page++;
                        mReadingCommentAdapter.loadMoreComplete();
                        onRequestCommentInfo();
                        isReqState = true;
                    }
                }
            } else {
                mReadingCommentAdapter.loadMoreEnd();
            }
        }

    }

    @Override
    public void getCommentInfoSuccess(CommentListBean commentListBean) {
        isReqState = true;
        readingCommendResponseList = commentListBean.getData();
        //加载更多这样设置
        if (!commentListBean.getData().isEmpty()) {
            if (page == 1) {
                mReadingCommentAdapter.setNewData(commentListBean.getData());
            } else {
                mReadingCommentAdapter.addData(commentListBean.getData());
            }
        } else {
            if (page == 1) {
                mReadingCommentAdapter.setNewData(null);
                mReadingCommentAdapter.setEmptyView(mEmptyView);
            }
        }
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
     * 回复评论
     */
    @Override
    public void ReplyCommentBtnListener(String content) {
        mContent = content;
        onPublishCommentUser();
    }

    @Override
    public void dismissBtnListenerByCommentSoftKey() {

    }

    /**
     * 评论用户评论
     */
    private void onPublishCommentUser() {
        PublishCommentUserRequest request = new PublishCommentUserRequest();
        request.token = mBuProcessor.getToken();
        request.comment_id = String.valueOf(mCommentBean.getComment_id());
        request.comment = mContent;
        request.be_user_id = String.valueOf(mCommentBean.getUser_id());
        request.reply_id = String.valueOf(mCommentBean.getComment_id());
        mPresenter.onPublishCommentUser(request);
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
        mPresenter.onRequestPublishComment(publishCommentRequest);
    }


    @Override
    public void getPublishCommentSuccess() {
        showToast("发布成功");
        //刷新页面
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(new Intent(ActivityConstant.UPDATE_COMMENT_LIST));
    }

    /**
     * 评论点赞
     */
    private void onCommentSuggestRequest() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = String.valueOf(mCommentBean.getComment_id());
        commentSuggestRequest.type = "3";
        mPresenter.onSupportRequest(commentSuggestRequest);
    }


    @Override
    public void getSupportSuccess() {
        mReadingCommentAdapter.notifyItemChanged(clickPos, mCommentBean.getLike());//局部刷新
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

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_comment);
        emptyTv.setText(getResources().getString(R.string.BookDetailFragment_empty_tv));
    }



    private void initializeInjector() {
        DaggerHotCommentFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .moreCommentModule(new MoreCommentModule((AppCompatActivity) getActivity()))
                .hotCommentFragmentModule(new HotCommentFragmentModule(this))
                .build().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
