package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCommentDetailComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CommentDetailModule;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.mvp.ui.adapter.CommentDetailAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评论详情
 */
public class CommentDetailsActivity extends BaseActivity implements CommentDetailControl.CommentDetailView {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.comment_et)
    EditText mCommentEt;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    private CommentDetailAdapter mCommentDetailAdapter;
    private List<CommentDetailResponse> commentDetailResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_comment_details);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        mCommentDetailAdapter = new CommentDetailAdapter(commentDetailResponseList);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentRecyclerView.setAdapter(mCommentDetailAdapter);

    }

    @Override
    public void initData() {
        mCommonTitleTv.setText(getResources().getString(R.string.CommentDetailsActivity_title));
        for (int i=0;i<10;i++){
            CommentDetailResponse commentDetailResponse = new CommentDetailResponse();
            commentDetailResponseList.add(commentDetailResponse);
        }
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

    private void initInjectData() {
        DaggerCommentDetailComponent.builder().appComponent(getAppComponent())
                .commentDetailModule(new CommentDetailModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
