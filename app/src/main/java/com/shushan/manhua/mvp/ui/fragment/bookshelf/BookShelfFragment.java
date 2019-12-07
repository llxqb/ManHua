package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookShelfFragmentComponent;
import com.shushan.manhua.di.modules.BookShelfFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.activity.book.LongDeleteActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadingHistoryActivity;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.activity.mine.MemberCenterActivity;
import com.shushan.manhua.mvp.ui.adapter.BookShelfAdapter;
import com.shushan.manhua.mvp.ui.adapter.RecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.LogUtils;
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

public class BookShelfFragment extends BaseFragment implements BookShelfFragmentControl.BookShelfView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    BookShelfFragmentControl.BookShelfFragmentPresenter mPresenter;
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLy;
    @BindView(R.id.last_read_layout)
    LinearLayout mLastReadLayout;
    @BindView(R.id.recent_read_book_iv)
    ImageView mRecentReadBookIv;
    @BindView(R.id.recent_read_book_name_tv)
    TextView mRecentReadBookNameTv;
    @BindView(R.id.recent_read_book_to_chapter_tv)
    TextView mRecentReadBookToChapterTv;
    @BindView(R.id.bookshelf_recycler_view)
    RecyclerView mBookshelfRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.shelf_layout)
    LinearLayout mShelfLayout;
    @BindView(R.id.shelf_empty_layout)
    LinearLayout mShelfEmptyLayout;
    Unbinder unbinder;
    private BookShelfAdapter mBookShelfAdapter;
    private RecommendAdapter mRecommendAdapter;
    private List<BookShelfResponse.BookrackBean> bookShelfResponseList = new ArrayList<>();
    private List<RecommendBean> recommendResponseList = new ArrayList<>();
    private BookShelfResponse mBookShelfResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.UPDATE_BOOKSHELF)) {
                onRequestBookShelfInfo();
            } else if (intent.getAction().equals(ActivityConstant.UPDATE_RECOMMEND_BOOK)) {
                //更新推荐数据
                onRecommendInfo();
            } else if (intent.getAction().equals(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA)) {
                mShelfLayout.setVisibility(View.VISIBLE);
                mShelfEmptyLayout.setVisibility(View.GONE);
                onRequestBookShelfInfo();
                onRecommendInfo();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_BOOKSHELF);
        mFilter.addAction(ActivityConstant.UPDATE_RECOMMEND_BOOK);
        mFilter.addAction(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA);
    }

    @Override
    public void initView() {
        mSwipeLy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeLy.setOnRefreshListener(this);
        initAdapter();
        //1 是游客模式 2 是登录模式
        int mLoginModel = mBuProcessor.getLoginModel();
        if (mLoginModel != 2) {
            mShelfLayout.setVisibility(View.GONE);
            mShelfEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mShelfLayout.setVisibility(View.VISIBLE);
            mShelfEmptyLayout.setVisibility(View.GONE);
            onRequestBookShelfInfo();
            onRecommendInfo();
        }
    }

    private void initAdapter() {
        mBookShelfAdapter = new BookShelfAdapter(bookShelfResponseList, mImageLoaderHelper);
        mBookshelfRecyclerView.setAdapter(mBookShelfAdapter);
        mBookshelfRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecommendAdapter = new RecommendAdapter(recommendResponseList, mImageLoaderHelper);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //长按删除
        mBookShelfAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            LongDeleteActivity.start(getActivity(), (ArrayList<BookShelfResponse.BookrackBean>) mBookShelfResponse.getBookrack());
            return false;
        });

        mBookShelfAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (position == adapter.getItemCount() - 1) {
                LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(new Intent(ActivityConstant.SWITCH_TO_HOME_PAGE));
            } else {
                BookShelfResponse.BookrackBean bookrackBean = (BookShelfResponse.BookrackBean) adapter.getItem(position);
                if (bookrackBean != null) {
                    ReadActivity.start(getActivity(), String.valueOf(bookrackBean.getBook_id()), bookrackBean.getCatalogue_id(), bookrackBean.getDetail_cover());//阅读页面
                }
            }
        });

        mRecommendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RecommendBean dataBean = (RecommendBean) adapter.getItem(position);
            BookDetailActivity.start(getActivity(), String.valueOf(dataBean.getBook_id()), dataBean.getSquare_cover());
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.search_rl, R.id.vip_center_tv, R.id.continue_read_rl, R.id.read_record_ll, R.id.change_tv, R.id.login_in_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                showToast("搜索");
                break;
            case R.id.vip_center_tv:
                startActivitys(MemberCenterActivity.class);
                break;
            case R.id.continue_read_rl://继续阅读
                ReadActivity.start(getActivity(), String.valueOf(mBookShelfResponse.getLast_read().getBook_id()), mBookShelfResponse.getLast_read().getCatalogue_id(), mBookShelfResponse.getLast_read().getDetail_cover());//阅读页面
                break;
            case R.id.read_record_ll:
                startActivitys(ReadingHistoryActivity.class);
                break;
            case R.id.change_tv:
//                showToast("换一批");
                break;
            case R.id.login_in_tv://登录
                startActivitys(LoginActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        onRequestBookShelfInfo();
    }

    /**
     * 请求书架数据
     */
    private void onRequestBookShelfInfo() {
        BookShelfInfoRequest bookShelfInfoRequest = new BookShelfInfoRequest();
        bookShelfInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBookShelfInfo(bookShelfInfoRequest);
    }

    @Override
    public void getBookShelfInfoSuccess(BookShelfResponse bookShelfResponse) {
        if (mSwipeLy.isRefreshing()) {
            mSwipeLy.setRefreshing(false);
        }
        mBookShelfResponse = bookShelfResponse;
        BookShelfResponse.LastReadBean lastReadBean = bookShelfResponse.getLast_read();
        LogUtils.e("lastReadBean:" + new Gson().toJson(lastReadBean));
        if (lastReadBean == null || new Gson().toJson(lastReadBean).equals("{}") || lastReadBean.getBook_id() == 0) {
            mLastReadLayout.setVisibility(View.GONE);
        } else {
            mLastReadLayout.setVisibility(View.VISIBLE);
            mImageLoaderHelper.displayImage(getActivity(), lastReadBean.getDetail_cover(), mRecentReadBookIv, Constant.LOADING_DEFAULT_2);
            mRecentReadBookNameTv.setText(lastReadBean.getBook_name());
            mRecentReadBookToChapterTv.setText("Bacaan terakhir ke Bab " + lastReadBean.getCatalogue_id());
        }
        bookShelfResponseList = bookShelfResponse.getBookrack();
        BookShelfResponse.BookrackBean bookrackBean = new BookShelfResponse.BookrackBean();
        bookrackBean.isMore = true;
        bookShelfResponseList.add(bookrackBean);
        mBookShelfAdapter.setNewData(bookShelfResponseList);

    }


    /**
     * 请求推荐数据
     */
    private void onRecommendInfo() {
        RecommendRequest recommendRequest = new RecommendRequest();
        recommendRequest.channel = mBuProcessor.getChannel();
        recommendRequest.book_type = mBuProcessor.getbookType();
        mPresenter.onRecommendInfo(recommendRequest);
    }


    @Override
    public void getRecommendInfoSuccess(RecommendResponse recommendResponse) {
//        mRecommendAdapter.setEmptyView(recommendResponse.getData());
        mRecommendAdapter.setNewData(recommendResponse.getData());
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
