package com.shushan.manhua.mvp.ui.fragment.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHomeFragmentComponent;
import com.shushan.manhua.di.modules.HomeFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.response.BannerResponse;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.adapter.BannerViewHolder;
import com.shushan.manhua.mvp.ui.adapter.HomeAdapter;
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
    @BindView(R.id.comic_title_tv)
    TextView mComicTitleTv;
    @BindView(R.id.novel_title_tv)
    TextView mNovelTitleTv;
    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    Unbinder unbinder;
    private List<BannerBean> bannerList = new ArrayList<>();
    private HomeAdapter mHomeAdapter;
    private List<HomeResponse.BooksBean> homeResponseList = new ArrayList<>();

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
        homeRecyclerView.setFocusable(false);
        mHomeAdapter = new HomeAdapter(homeResponseList, mImageLoaderHelper);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomeResponse.BooksBean booksBean = (HomeResponse.BooksBean) adapter.getItem(position);
            if(booksBean!=null){
                BookDetailActivity.start(getActivity(), String.valueOf(booksBean.getBook_id()));
            }
        });
        initTitle();
    }

    private void initTitle() {
        mComicTitleTv.setTextColor(getResources().getColor(R.color.black));
        mNovelTitleTv.setTextColor(getResources().getColor(R.color.color_b4));
        Drawable drawable = getResources().getDrawable(R.drawable.bg_rectangle_blue_bottom);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mComicTitleTv.setCompoundDrawables(null, null, null, drawable);
    }

    @Override
    public void initData() {
        onRequestBanner();
        onRequestHome("1");
    }

    private void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        mBanner.setIndicatorPadding(0, 0, 0, 30);
        mBanner.setPages(bannerList, (MZHolderCreator<BannerViewHolder>) () -> new BannerViewHolder(mImageLoaderHelper));
    }

    /**
     * 首页banner
     */
    private void onRequestBanner() {
        mPresenter.onRequestBanner();
    }

    @Override
    public void getBannerSuccess(BannerResponse bannerResponse) {
        bannerList = bannerResponse.getBanner();
        initBanner();
    }

    /**
     * 请求首页数据
     * genre 1: 漫画   2：小说
     *
     */
    private void onRequestHome(String genre) {
        HomeInfoRequest homeInfoRequest = new HomeInfoRequest();
        homeInfoRequest.token = mBuProcessor.getToken();
        homeInfoRequest.channel = mBuProcessor.getChannel();
        homeInfoRequest.book_type = mBuProcessor.getbookType();
        homeInfoRequest.genre = genre;
        mPresenter.onRequestHomeInfo(homeInfoRequest);
    }

    @Override
    public void getHomeInfoSuccess(HomeResponse homeResponse) {
        mHomeAdapter.setNewData(homeResponse.getBooks());
    }

    @OnClick({R.id.comic_title_tv, R.id.novel_title_tv})
    public void onViewClicked(View view) {
        Drawable drawable;
        switch (view.getId()) {
            case R.id.comic_title_tv://漫画
                mComicTitleTv.setTextColor(getResources().getColor(R.color.black));
                mNovelTitleTv.setTextColor(getResources().getColor(R.color.color_b4));
                drawable = getResources().getDrawable(R.drawable.bg_rectangle_blue_bottom);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mNovelTitleTv.setCompoundDrawables(null, null, null, null);
                mComicTitleTv.setCompoundDrawables(null, null, null, drawable);
                onRequestHome("1");
                break;
            case R.id.novel_title_tv://小说
                mNovelTitleTv.setTextColor(getResources().getColor(R.color.black));
                mComicTitleTv.setTextColor(getResources().getColor(R.color.color_b4));
                drawable = getResources().getDrawable(R.drawable.bg_rectangle_blue_bottom);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mComicTitleTv.setCompoundDrawables(null, null, null, null);
                mNovelTitleTv.setCompoundDrawables(null, null, null, drawable);
                onRequestHome("2");
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
