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
import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.response.BannerResponse;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.adapter.HomeAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页小说
 */

public class HomeNovelFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private HomeAdapter mHomeAdapter;
    private List<HomeResponse.BooksBean> homeResponseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        mUser = mBuProcessor.getUser();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mRecyclerView.setNestedScrollingEnabled(false);//解决ScrollView+RecyclerView的滑动冲突问题
        mHomeAdapter = new HomeAdapter(homeResponseList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomeResponse.BooksBean booksBean = (HomeResponse.BooksBean) adapter.getItem(position);
            if (booksBean != null) {
                BookDetailActivity.start(getActivity(), String.valueOf(booksBean.getBook_id()));
            }
        });
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
        homeInfoRequest.token = mBuProcessor.getToken();
        homeInfoRequest.channel = mBuProcessor.getChannel();
        homeInfoRequest.book_type = mBuProcessor.getbookType();
        homeInfoRequest.genre = "2";
        mPresenter.onRequestHomeInfo(homeInfoRequest);
    }

    @Override
    public void getHomeInfoSuccess(HomeResponse homeResponse) {
        mHomeAdapter.setNewData(homeResponse.getBooks());
    }

    @Override
    public void getBannerSuccess(BannerResponse bannerResponse) {

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
