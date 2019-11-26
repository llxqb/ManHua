package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookShelfFragmentComponent;
import com.shushan.manhua.di.modules.BookShelfFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.activity.book.LongDeleteActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadingHistoryActivity;
import com.shushan.manhua.mvp.ui.adapter.BookShelfAdapter;
import com.shushan.manhua.mvp.ui.adapter.RecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 书架
 */

public class BookShelfFragment extends BaseFragment implements BookShelfFragmentControl.BookShelfView {

    @Inject
    BookShelfFragmentControl.BookShelfFragmentPresenter mPresenter;
    @BindView(R.id.last_read_layout)
    LinearLayout mLastReadLayout;
    @BindView(R.id.recent_read_book_name_tv)
    TextView mRecentReadBookNameTv;
    @BindView(R.id.recent_read_book_to_chapter_tv)
    TextView mRecentReadBookToChapterTv;
    @BindView(R.id.bookshelf_recycler_view)
    RecyclerView mBookshelfRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    Unbinder unbinder;
    private BookShelfAdapter mBookShelfAdapter;
    private RecommendAdapter mRecommendAdapter;
    private List<BookShelfResponse.BookrackBean> bookShelfResponseList = new ArrayList<>();
    private List<RecommendResponse> recommendResponseList = new ArrayList<>();
    private User mUser;
    private BookShelfResponse mBookShelfResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        mUser = mBuProcessor.getUser();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mBookShelfAdapter = new BookShelfAdapter(bookShelfResponseList, mImageLoaderHelper);
        mBookshelfRecyclerView.setAdapter(mBookShelfAdapter);
        mBookshelfRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecommendAdapter = new RecommendAdapter(recommendResponseList);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mBookShelfAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            startActivitys(ReadActivity.class);//阅读页面
        });
        //长按删除
        mBookShelfAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            BookShelfResponse.BookrackBean bookRackBean = (BookShelfResponse.BookrackBean) adapter.getItem(position);
            LongDeleteActivity.start(getActivity(), mBookShelfResponse);
            return false;
        });

        mRecommendAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivitys(BookDetailActivity.class);
            }
        });
    }

    @Override
    public void initData() {
//        BookShelfResponse bookShelfResponse = new BookShelfResponse();
//        bookShelfResponse.cover = R.mipmap.bookshelf_find_more;
//        bookShelfResponse.isMore = true;
//        bookShelfResponseList.add(bookShelfResponse);
        onRequestBookShelfInfo();
    }

    /**
     * 请求书架数据
     */
    private void onRequestBookShelfInfo() {
        BookShelfInfoRequest bookShelfInfoRequest = new BookShelfInfoRequest();
        bookShelfInfoRequest.token =  mBuProcessor.getToken();
        mPresenter.onRequestBookShelfInfo(bookShelfInfoRequest);
    }

    @Override
    public void getBookShelfInfoSuccess(BookShelfResponse bookShelfResponse) {
        mBookShelfResponse = bookShelfResponse;
        if (bookShelfResponse.getLast_read().isEmpty()) {
            mLastReadLayout.setVisibility(View.GONE);
        } else {
            mLastReadLayout.setVisibility(View.VISIBLE);
        }
        mBookShelfAdapter.setNewData(bookShelfResponse.getBookrack());
    }


    @OnClick({R.id.search_rl, R.id.vip_center_tv, R.id.continue_read_rl, R.id.read_record_ll, R.id.change_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                showToast("搜索");
                break;
            case R.id.vip_center_tv:
                showToast("会员中心");
                break;
            case R.id.continue_read_rl:
                showToast("继续阅读");
                break;
            case R.id.read_record_ll:
                startActivitys(ReadingHistoryActivity.class);
                break;
            case R.id.change_tv:
                showToast("换一批");
                break;
        }
    }

    private void initializeInjector() {
        DaggerBookShelfFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .bookShelfFragmentModule(new BookShelfFragmentModule(this))
                .build().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
