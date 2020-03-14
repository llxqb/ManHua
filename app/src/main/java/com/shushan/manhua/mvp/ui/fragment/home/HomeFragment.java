package com.shushan.manhua.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHomeFragmentComponent;
import com.shushan.manhua.di.modules.HomeFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.response.BannerResponse;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.mvp.ui.activity.book.BookClassificationActivity;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.activity.book.RankingActivity;
import com.shushan.manhua.mvp.ui.adapter.BannerViewHolder;
import com.shushan.manhua.mvp.ui.adapter.HomeComicAdapter;
import com.shushan.manhua.mvp.ui.adapter.HomeNovelAdapter;
import com.shushan.manhua.mvp.ui.adapter.HomeRecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 书城
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;
    @BindView(R.id.banner)
    MZBannerView mBanner;
    Unbinder unbinder;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.novel_recycler_view)
    RecyclerView mNovelRecyclerView;
    @BindView(R.id.komik_recycler_view)
    RecyclerView mKomikRecyclerView;
    private List<BannerBean> bannerList = new ArrayList<>();
    private List<HomeResponse.HomeCommonBean> homeCommonResponseList = new ArrayList<>();
    private HomeRecommendAdapter mHomeRecommendAdapter;
    private HomeNovelAdapter mHomeNovelAdapter;
    private HomeComicAdapter mHomeComicAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeInjector();
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        initAdapter();
    }

    private void initAdapter() {
        mRecommendRecyclerView.setFocusable(false);
        mHomeRecommendAdapter = new HomeRecommendAdapter(homeCommonResponseList, mImageLoaderHelper);//推荐
        mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecommendRecyclerView.setAdapter(mHomeRecommendAdapter);
        mHomeRecommendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomeResponse.HomeCommonBean booksBean = (HomeResponse.HomeCommonBean) adapter.getItem(position);
            if (booksBean != null) {
                BookDetailActivity.start(getActivity(), String.valueOf(booksBean.getBook_id()));
            }
        });

        mNovelRecyclerView.setFocusable(false);
        mHomeNovelAdapter = new HomeNovelAdapter(homeCommonResponseList, mImageLoaderHelper);//小说
        mNovelRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mNovelRecyclerView.setAdapter(mHomeNovelAdapter);
        mHomeNovelAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomeResponse.HomeCommonBean booksBean = (HomeResponse.HomeCommonBean) adapter.getItem(position);
            if (booksBean != null) {
                BookDetailActivity.start(getActivity(), String.valueOf(booksBean.getBook_id()));
            }
        });

        mKomikRecyclerView.setFocusable(false);
        mHomeComicAdapter = new HomeComicAdapter(homeCommonResponseList, mImageLoaderHelper);//漫画
        mKomikRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mKomikRecyclerView.setAdapter(mHomeComicAdapter);
        mHomeComicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomeResponse.HomeCommonBean booksBean = (HomeResponse.HomeCommonBean) adapter.getItem(position);
            if (booksBean != null) {
                BookDetailActivity.start(getActivity(), String.valueOf(booksBean.getBook_id()));
            }
        });
    }

    @Override
    public void initData() {
//        onRequestBanner();
        onRequestHome();
    }

    private void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        mBanner.setIndicatorPadding(0, 0, 0, 30);
        mBanner.setPages(bannerList, (MZHolderCreator<BannerViewHolder>) () -> new BannerViewHolder(mImageLoaderHelper));
        mBanner.start();//开始轮播
    }
    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }

    /**
     * 首页banner
     */
    private void onRequestBanner() {
        mPresenter.onRequestBanner();
    }

    @Override
    public void getBannerSuccess(BannerResponse bannerResponse) {
//        bannerList = bannerResponse.getBanner();
//        initBanner();
    }

    /**
     * 请求首页数据
     * genre 1: 漫画   2：小说
     */
    private void onRequestHome() {
        HomeInfoRequest homeInfoRequest = new HomeInfoRequest();
        homeInfoRequest.token = mBuProcessor.getToken();
        homeInfoRequest.channel = mBuProcessor.getChannel();
        homeInfoRequest.book_type = mBuProcessor.getbookType();
//        homeInfoRequest.genre = genre;
        mPresenter.onRequestHomeInfo(homeInfoRequest);
    }


    @Override
    public void getHomeInfoSuccess(HomeResponse homeResponse) {
        bannerList = homeResponse.getBanner();
        initBanner();
        mHomeRecommendAdapter.setNewData(homeResponse.getRecommend());
        mHomeNovelAdapter.setNewData(homeResponse.getNovels());
        mHomeComicAdapter.setNewData(homeResponse.getMoods());
    }

    @OnClick({R.id.peringkat_iv, R.id.jenis_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.peringkat_iv://排行
                startActivitys(RankingActivity.class);
                break;
            case R.id.jenis_iv://分类
                startActivitys(BookClassificationActivity.class);
                break;
        }
    }


    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
