package com.shushan.manhua.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHomeFragmentComponent;
import com.shushan.manhua.di.modules.HomeFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.response.BannerResponse;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.entity.user.User;
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
import butterknife.Unbinder;

/**
 * 书城
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;
    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private HomeAdapter mHomeAdapter;
    private List<HomeResponse> homeResponseList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeInjector();
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        mUser = mBuProcessor.getUser();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mHomeAdapter = new HomeAdapter(homeResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    public void initData() {
        initBanner();
        for (int i = 0; i < 3; i++) {
            HomeResponse homeResponse = new HomeResponse();
            List<HomeResponse.LabelBean> labelResponseList = new ArrayList<>();
            for (int j=0;j<3;j++){
                HomeResponse.LabelBean labelResponse = new HomeResponse.LabelBean();
                labelResponse.name = "标签111";
                labelResponseList.add(labelResponse);
            }
            homeResponse.labelBeanList = labelResponseList;
            homeResponseList.add(homeResponse);
        }
    }

    private void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        List<BannerResponse> bannerList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            BannerResponse bannerResponse = new BannerResponse();
            bannerList.add(bannerResponse);
        }
        mBanner.setPages(bannerList, (MZHolderCreator<BannerViewHolder>) () -> new BannerViewHolder(mImageLoaderHelper));
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
