package com.shushan.manhua.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.HomeAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页漫画
 */

public class HomeComicFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private HomeAdapter mHomeAdapter;
    private List<HomeResponse.BooksBean> homeResponseList = new ArrayList<>();
    List<BannerBean> bannerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        mUser = mBuProcessor.getUser();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        onRequestHome();
    }


    /**
     * 请求首页数据
     */
    private void onRequestHome() {
        HomeInfoRequest homeInfoRequest = new HomeInfoRequest();
        if (mUser != null) {
            homeInfoRequest.token = mUser.token;
        }
        homeInfoRequest.channel = mBuProcessor.getChannel();
        homeInfoRequest.book_type = mBuProcessor.getbookType();
        mPresenter.onRequestHomeInfo(homeInfoRequest);
    }

    @Override
    public void getHomeInfoSuccess(HomeResponse homeResponse) {
        bannerList = homeResponse.getBanner();
//        mHomeAdapter.setNewData(homeResponse.getBooks());
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
