package com.shushan.manhua.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHomeFragmentComponent;
import com.shushan.manhua.di.modules.HomeFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.response.BannerResponse;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.mvp.ui.adapter.BannerViewHolder;
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
import butterknife.Unbinder;

/**
 * 书城
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;
    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    Unbinder unbinder;
    String[] titles;
    private List<BannerBean> bannerList = new ArrayList<>();

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
        titles = new String[]{getResources().getString(R.string.HomeFragment_title_comic), getResources().getString(R.string.HomeFragment_title_novel)};
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));//子fragment用getChildFragmentManager()
        mXTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {
        onRequestBanner();
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

    @Override
    public void getHomeInfoSuccess(HomeResponse homeResponse) {
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            HomeComicFragment homeComicFragment = new HomeComicFragment();//漫画
            HomeNovelFragment homeNovelFragment = new HomeNovelFragment();//小说
            fragments.add(homeComicFragment);
            fragments.add(homeNovelFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
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
