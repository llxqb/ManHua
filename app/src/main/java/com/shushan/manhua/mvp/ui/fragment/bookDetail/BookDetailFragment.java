package com.shushan.manhua.mvp.ui.fragment.bookDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookDetailFragmentComponent;
import com.shushan.manhua.di.modules.BookDetailFragmentModule;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
import com.shushan.manhua.mvp.ui.activity.book.MoreCommentActivity;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.adapter.BookDetailAdapter;
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

    @Inject
    BookDetailFragmentControl.BookDetailFragmentPresenter mPresenter;
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
    BookDetailAdapter mReadingCommentAdapter;
    private List<CommentBean> readingCommendResponseList = new ArrayList<>();
    private View mEmptyView;
    private String mBookId;
    private CommentBean commentBean;
    private int clickPos;
    private int mLoginModel;//1 是游客模式 2 是登录模式

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA)) {
                mLoginModel = mBuProcessor.getLoginModel();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA);
    }

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
        mLoginModel = mBuProcessor.getLoginModel();
        initEmptyView();
        if (getArguments() != null) {// Bundle bundle =this.getArguments();
            mBookId = getArguments().getString("bookId");
            onRequestDetailInfo();
        }
        mReadingCommentAdapter = new BookDetailAdapter(readingCommendResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            commentBean = (CommentBean) adapter.getItem(position);
            clickPos = position;
            switch (view.getId()) {
                case R.id.suggest_num_tv:
                    if (mLoginModel != 2) {
                        toLogin();
                    } else {
                        onCommentSuggestRequest();
                    }
                    break;
                case R.id.item_comment_layout:
                    if (mLoginModel != 2) {
                        toLogin();
                    } else {
                        MoreCommentActivity.start(getActivity(), mBookId);
                    }
                    break;
            }
        });
    }

    @Override
    public void initData() {
    }

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mCommentRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_comment);
        emptyTv.setText(getResources().getString(R.string.BookDetailFragment_empty_tv));
    }


    @OnClick({R.id.publish_comment_tv, R.id.more_comment_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_comment_tv:
            case R.id.more_comment_tv:
                if (mLoginModel != 2) {
                    toLogin();
                } else {
                    MoreCommentActivity.start(getActivity(), mBookId);
                }
                break;
        }
    }

    /**
     * 游客提示登录
     */
    private void toLogin() {
        showToast(getString(R.string.please_login_hint));
        startActivitys(LoginActivity.class);
    }


    /**
     * 请求漫画详情
     */
    private void onRequestDetailInfo() {
        BookDetailRequest bookDetailRequest = new BookDetailRequest();
        bookDetailRequest.token = mBuProcessor.getToken();
        bookDetailRequest.book_id = mBookId;
        mPresenter.onRequestBookDetailInfo(bookDetailRequest);
    }

    @Override
    public void getBookDetailInfoSuccess(BookDetailInfoResponse bookDetailInfoResponse) {
        BookDetailInfoResponse.DetailBean detailBean = bookDetailInfoResponse.getDetail();
        if (detailBean != null) {
            mDescTv.setText(detailBean.getDes());
            mCommentNumTv.setText(String.valueOf(detailBean.getComment_count()));
            String authorValue = getString(R.string.BookDetailFragment_author) + detailBean.getAuthor();
            String collectionValue = detailBean.getCollect() + getString(R.string.BookDetailFragment_connection_num);
            mAuthorTv.setText(authorValue);
            mCollectionNumTv.setText(collectionValue);
        }
        if (bookDetailInfoResponse.getComment().isEmpty()) {
            mReadingCommentAdapter.setNewData(null);
            mReadingCommentAdapter.setEmptyView(mEmptyView);
        } else {
            mReadingCommentAdapter.setNewData(bookDetailInfoResponse.getComment());
        }
    }


    /**
     * 评论点赞
     */
    private void onCommentSuggestRequest() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = String.valueOf(commentBean.getComment_id());
        commentSuggestRequest.type = "3";
        mPresenter.onCommentSuggestRequest(commentSuggestRequest);
    }

    @Override
    public void getSuggestSuccess() {
        mReadingCommentAdapter.notifyItemChanged(clickPos, commentBean.getLike());//局部刷新
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
