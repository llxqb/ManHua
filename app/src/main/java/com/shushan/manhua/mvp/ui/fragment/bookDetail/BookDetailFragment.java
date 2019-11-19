package com.shushan.manhua.mvp.ui.fragment.bookDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookDetailFragmentComponent;
import com.shushan.manhua.di.modules.BookDetailFragmentModule;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.entity.response.ReadingCommendResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.activity.book.MoreCommentActivity;
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
 * 漫画详情
 */

public class BookDetailFragment extends BaseFragment implements BookDetailFragmentControl.BookDetailView {

    @BindView(R.id.desc_tv)
    TextView mDescTv;
    @BindView(R.id.author_tv)
    TextView mAuthorTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.collection_num_tv)
    TextView mCollectionNumTv;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    Unbinder unbinder;
    private User mUser;
    ReadingCommentAdapter mReadingCommentAdapter;
    private List<ReadingCommendResponse> readingCommendResponseList = new ArrayList<>();
    @Inject
    BookDetailFragmentControl.BookDetailFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mUser = mBuProcessor.getUser();
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void initData() {
        for (int i = 0; i < 2; i++) {
            ReadingCommendResponse readingCommendResponse = new ReadingCommendResponse();
            readingCommendResponseList.add(readingCommendResponse);
        }
    }


    @OnClick({R.id.publish_comment_tv, R.id.more_comment_tv, R.id.start_reading_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_comment_tv:
                showToast("发表评论");
                break;
            case R.id.more_comment_tv:
                showToast("更多评论");
                startActivitys(MoreCommentActivity.class);
                break;
            case R.id.start_reading_tv:
                showToast("开始阅读");
                break;
        }
    }

    private void initializeInjector() {
        DaggerBookDetailFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .bookDetailModule(new BookDetailModule((AppCompatActivity) getActivity()))
                .bookDetailFragmentModule(new BookDetailFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
