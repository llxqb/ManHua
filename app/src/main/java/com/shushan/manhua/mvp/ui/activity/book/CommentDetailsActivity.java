package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCommentDetailComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CommentDetailModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.mvp.ui.adapter.CommentDetailAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评论详情
 */
public class CommentDetailsActivity extends BaseActivity implements CommentDetailControl.CommentDetailView {

    @Inject
    CommentDetailControl.PresenterCommentDetail mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    private CommentDetailAdapter mCommentDetailAdapter;
    private List<CommentDetailResponse.ReviewBean> commentDetailResponseList = new ArrayList<>();
    private int page = 1;
    private String mCommentId;

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
        mCommentDetailAdapter = new CommentDetailAdapter(commentDetailResponseList);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentRecyclerView.setAdapter(mCommentDetailAdapter);
        mCommentDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommentDetailResponse commentDetailResponse = (CommentDetailResponse) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.comment_iv:
                        //点击图片 放大查看
                        break;
                    case R.id.item_recommend_layout:
                        //回复某人
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        mCommonTitleTv.setText(getResources().getString(R.string.CommentDetailsActivity_title));
    }


    @OnClick({R.id.common_left_iv, R.id.publish_comment_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.publish_comment_tv:
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

    @Override
    public void getCommentDetailSuccess(CommentDetailResponse commentDetailResponse) {
        mCommentDetailAdapter.setNewData(commentDetailResponse.getReview());
    }

    private void initInjectData() {
        DaggerCommentDetailComponent.builder().appComponent(getAppComponent())
                .commentDetailModule(new CommentDetailModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
