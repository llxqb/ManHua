package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCommentDetailComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CommentDetailModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.mvp.ui.adapter.CommentDetailAdapter;
import com.shushan.manhua.mvp.ui.adapter.PicAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.CommentSoftKeyPopupWindow;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评论详情
 */
public class CommentDetailsActivity extends BaseActivity implements CommentDetailControl.CommentDetailView, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    CommentDetailControl.PresenterCommentDetail mPresenter;
    @BindView(R.id.comment_detail_layout)
    RelativeLayout mCommentDetailLayout;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.suggest_num_tv)
    TextView mSuggestNumTv;
    @BindView(R.id.time_tv)
    TextView mTimeTv;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.pic_recycler_view)
    RecyclerView mPicRecyclerView;
    private CommentDetailAdapter mCommentDetailAdapter;
    //发表评论的图片
    private PicAdapter mPicAdapter;
    private List<CommentDetailResponse.ReviewBean> commentDetailResponseList = new ArrayList<>();
    private List<String> picStringList = new ArrayList<>();
    private int page = 1;
    private String mCommentId;
    private String mContent;//评论内容
    private CommentDetailResponse mCommentDetailResponse;
    private CommentDetailResponse.ReviewBean mReviewBean;
    private int clickPos;//点击的位置
    private int supportPos;//1 点赞主评论  2 点赞列表（评论的回复）
    private int commentPos;//1 评论主评论  2 评论列表（评论的回复）

    public static void start(Context context, String commentId) {
        Intent intent = new Intent(context, CommentDetailsActivity.class);
        intent.putExtra("commentId", commentId);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_comment_details);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mCommentId = getIntent().getStringExtra("commentId");
            onRequestCommentDetail();
        }
        initAdapter();
    }

    private void initAdapter() {
        mCommentDetailAdapter = new CommentDetailAdapter(commentDetailResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentRecyclerView.setAdapter(mCommentDetailAdapter);
        mCommentDetailAdapter.setOnLoadMoreListener(this, mCommentRecyclerView);
        mCommentDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mReviewBean = (CommentDetailResponse.ReviewBean) adapter.getItem(position);
            clickPos = position;
            switch (view.getId()) {
                case R.id.suggest_num_tv:
                    supportPos = 2;
                    onCommentSuggestRequest(String.valueOf(mReviewBean.getReview_id()), "5");
                    break;
                case R.id.item_recommend_layout:
                    //回复某人
                    commentPos = 2;
                    showCommentPopupWindow("@" + mReviewBean.getUser_name());
                    break;
            }
        });
        mPicAdapter = new PicAdapter(picStringList, mImageLoaderHelper);
        mPicRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mPicRecyclerView.setAdapter(mPicAdapter);
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> LookPhotoActivity.start(this, (String) adapter.getItem(position)));
    }

    @Override
    public void initData() {
        mCommonTitleTv.setText(getResources().getString(R.string.CommentDetailsActivity_title));
    }


    @OnClick({R.id.common_left_iv, R.id.suggest_num_tv, R.id.comment_content_rl, R.id.publish_comment_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.suggest_num_tv://点赞
                supportPos = 1;
                onCommentSuggestRequest(String.valueOf(mCommentDetailResponse.getComment_id()), "3");//主评论点赞
                break;
            case R.id.comment_content_rl:
                commentPos = 1;
                showCommentPopupWindow("@" + mCommentDetailResponse.getName());
                break;
            case R.id.publish_comment_tv:
                commentPos = 1;
                showCommentPopupWindow("@" + mCommentDetailResponse.getName());
                break;
        }
    }

    /**
     * 评论详情
     */
    private void onRequestCommentDetail() {
        CommentDetailRequest commentDetailRequest = new CommentDetailRequest();
        commentDetailRequest.token = mBuProcessor.getToken();
        commentDetailRequest.page = String.valueOf(page);
        commentDetailRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        commentDetailRequest.comment_id = mCommentId;
        mPresenter.onRequestCommentDetail(commentDetailRequest);
    }

    boolean isReqState = false;//加载更多 正在请求状态

    @Override
    public void onLoadMoreRequested() {
        if (!isReqState) {
            if (!commentDetailResponseList.isEmpty()) {
                if (page == 1 && commentDetailResponseList.size() < Constant.PAGESIZE) {
                    mCommentDetailAdapter.loadMoreEnd(true);
                } else {
                    if (commentDetailResponseList.size() < Constant.PAGESIZE) {
                        mCommentDetailAdapter.loadMoreEnd();
                    } else {
                        //等于10条
                        page++;
                        mCommentDetailAdapter.loadMoreComplete();
                        onRequestCommentDetail();
                        isReqState = true;
                    }
                }
            } else {
                mCommentDetailAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void getCommentDetailSuccess(CommentDetailResponse commentDetailResponse) {
        isReqState = false;
        mCommentDetailResponse = commentDetailResponse;
        commentDetailResponseList = commentDetailResponse.getReview();
        //加载更多这样设置
        if (!commentDetailResponse.getReview().isEmpty()) {
            if (page == 1) {
                mCommentDetailAdapter.setNewData(commentDetailResponse.getReview());
            } else {
                mCommentDetailAdapter.addData(commentDetailResponse.getReview());
            }
        }
        mPicAdapter.setNewData(commentDetailResponse.getPics());
        mImageLoaderHelper.displayImage(this, commentDetailResponse.getHead_portrait(), mAvatarIv, Constant.LOADING_AVATOR);
        mNameTv.setText(commentDetailResponse.getName());
        if (commentDetailResponse.getIs_like() == 0) {//未点赞
            Drawable drawable = getResources().getDrawable(R.mipmap.title_praise_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mSuggestNumTv.setCompoundDrawables(drawable, null, null, null);
        } else {//点赞
            Drawable drawable = getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mSuggestNumTv.setCompoundDrawables(drawable, null, null, null);
        }
        mSuggestNumTv.setText(String.valueOf(commentDetailResponse.getLike()));
        mTimeTv.setText(DateUtil.getStrTime(commentDetailResponse.getComment_time(), DateUtil.TIME_MMDD_HHMM));
        mContentTv.setText(commentDetailResponse.getContent());
    }

    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow(String editHintContent) {
        CommentSoftKeyPopupWindow mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(this, this, null, editHintContent);
        mCommentSoftKeyPopupWindow.initPopWindow(mCommentDetailLayout);
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
        if (commentPos == 1) {//评论主评论
            PublishCommentUserRequest request = new PublishCommentUserRequest();
            request.token = mBuProcessor.getToken();
            request.comment_id = String.valueOf(mCommentDetailResponse.getComment_id());
            request.comment = mContent;
            request.be_user_id = String.valueOf(mCommentDetailResponse.getUser_id());
            request.reply_id = String.valueOf(mCommentDetailResponse.getComment_id());
            mPresenter.onPublishCommentUser(request);
        } else {
            //评论列表
            PublishCommentUserRequest request = new PublishCommentUserRequest();
            request.token = mBuProcessor.getToken();
            request.comment_id = String.valueOf(mReviewBean.getComment_id());
            request.comment = mContent;
            request.be_user_id = String.valueOf(mReviewBean.getUser_id());
            request.reply_id = String.valueOf(mReviewBean.getComment_id());
            mPresenter.onPublishCommentUser(request);
        }
    }

    @Override
    public void getCommentUserSuccess() {
        showToast("success");
        //刷新页面
        onRequestCommentDetail();
    }

    /**
     * 评论点赞
     */
    private void onCommentSuggestRequest(String relationId, String typeId) {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = relationId;
        commentSuggestRequest.type = typeId;
        mPresenter.onSupportRequest(commentSuggestRequest);
    }


    @Override
    public void getSupportSuccess() {
        if (supportPos == 1) {
            Drawable drawable = getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mSuggestNumTv.setCompoundDrawables(drawable, null, null, null);
            mSuggestNumTv.setText(String.valueOf(mCommentDetailResponse.getLike() + 1));
        } else {
            mCommentDetailAdapter.notifyItemChanged(clickPos, mReviewBean.getLike());//局部刷新
        }
    }

    @Override
    public void switchFunctionByCommentSoftKeyBtnListener() {

    }

    @Override
    public void photoBtnListener() {
    }

    @Override
    public void albumBtnListener(int maxPicNum) {
    }

    @Override
    public void CommentSendMessageBtnListener(List<TImage> tImageList, String content) {
    }


    private void initInjectData() {
        DaggerCommentDetailComponent.builder().appComponent(getAppComponent())
                .commentDetailModule(new CommentDetailModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
