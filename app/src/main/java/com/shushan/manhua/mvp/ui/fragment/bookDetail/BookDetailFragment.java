package com.shushan.manhua.mvp.ui.fragment.bookDetail;

import android.annotation.SuppressLint;
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
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
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
    ReadingCommentAdapter mReadingCommentAdapter;
    private List<CommentBean> readingCommendResponseList = new ArrayList<>();
    private View mEmptyView;
    private User mUser;
    @SuppressLint("StaticFieldLeak")
    static BookDetailFragment mBookDetailFragment;

    public static BookDetailFragment getInstance(String bookId) {
        if (mBookDetailFragment == null) {
            mBookDetailFragment = new BookDetailFragment();
        }
        Bundle bd = new Bundle();
        bd.putString("bookId", bookId);
        mBookDetailFragment.setArguments(bd);
        return mBookDetailFragment;
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
        initEmptyView();
        mUser = mBuProcessor.getUser();
        if (getArguments() != null) {
            String mBookId = getArguments().getString("bookId");
            onRequestDetailInfo(mBookId);
        }
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CommentBean commentBean = (CommentBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.comment_ll:
                    MoreCommentActivity.start(getActivity(),String.valueOf(commentBean.getBook_id()));
                    break;
            }
        });
    }

    @Override
    public void initData() {

        //
    }

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mCommentRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_comment);
        emptyTv.setText(getResources().getString(R.string.BookDetailFragment_empty_tv));
    }


    @OnClick({R.id.publish_comment_tv, R.id.more_comment_tv, R.id.start_reading_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_comment_tv:
                startActivitys(MoreCommentActivity.class);
                break;
            case R.id.more_comment_tv:
                startActivitys(MoreCommentActivity.class);
                break;
            case R.id.start_reading_tv:
                showToast("开始阅读");
                break;
        }
    }

    private void onRequestDetailInfo(String bookId) {
        BookDetailRequest bookDetailRequest = new BookDetailRequest();
        bookDetailRequest.book_id = bookId;
        mPresenter.onRequestBookDetailInfo(bookDetailRequest);
    }

    @Override
    public void getBookDetailInfoSuccess(BookDetailInfoResponse bookDetailInfoResponse) {
        BookDetailInfoResponse.DetailBean detailBean = bookDetailInfoResponse.getDetail();
        if (detailBean != null) {
            mDescTv.setText(detailBean.getDes());
            mAuthorTv.setText(getString(R.string.BookDetailFragment_author) + detailBean.getAuthor());
            mCommentNumTv.setText(String.valueOf(detailBean.getComment_count()));
            mCollectionNumTv.setText(detailBean.getCollect() + getString(R.string.BookDetailFragment_connection_num));
        }
        if (bookDetailInfoResponse.getComment().isEmpty()) {
            mReadingCommentAdapter.setNewData(null);
            mReadingCommentAdapter.setEmptyView(mEmptyView);
        } else {
            mReadingCommentAdapter.setNewData(bookDetailInfoResponse.getComment());
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
