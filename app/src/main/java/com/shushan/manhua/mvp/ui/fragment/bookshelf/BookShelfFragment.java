package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookShelfFragmentComponent;
import com.shushan.manhua.di.components.DaggerMineFragmentComponent;
import com.shushan.manhua.di.modules.BookShelfFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.di.modules.MineFragmentModule;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.ui.fragment.home.HomeFragmentControl;

import java.util.Objects;

import javax.inject.Inject;

/**
 * 书架
 */

public class BookShelfFragment extends BaseFragment implements BookShelfFragmentControl.BookShelfView {

    private User mUser;
    @Inject
    BookShelfFragmentControl.BookShelfFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initData() {
    }


    private void initializeInjector() {
        DaggerBookShelfFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .bookShelfFragmentModule(new BookShelfFragmentModule(this))
                .build().inject(this);
    }


}
