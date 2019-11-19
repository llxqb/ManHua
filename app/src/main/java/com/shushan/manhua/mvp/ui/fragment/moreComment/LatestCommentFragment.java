package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLatestCommentFragmentComponent;
import com.shushan.manhua.di.modules.LatestCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.entity.response.ReadingCommendResponse;
import com.shushan.manhua.mvp.ui.activity.book.CommentDetailsActivity;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

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

public class LatestCommentFragment extends BaseFragment implements LatestCommentFragmentControl.LatestCommentView {

    @Inject
    LatestCommentFragmentControl.LatestCommentFragmentPresenter mPresenter;
    @BindView(R.id.comment_et)
    EditText mCommentEt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private ReadingCommentAdapter mReadingCommentAdapter;
    List<ReadingCommendResponse> readingCommendResponseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_comment, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList);
        mRecyclerView.setAdapter(mReadingCommentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReadingCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.look_all_comment_tv:
                        startActivitys(CommentDetailsActivity.class);
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            ReadingCommendResponse readingCommendResponse = new ReadingCommendResponse();
            readingCommendResponseList.add(readingCommendResponse);
        }
    }


    @OnClick(R.id.publish_comment_tv)
    public void onViewClicked() {

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
