package com.shushan.manhua.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHomeFragmentComponent;
import com.shushan.manhua.di.modules.HomeFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.ui.dialog.SelectChannelDialog;
import com.shushan.manhua.mvp.ui.dialog.SelectManHuaTypeDialog;

import java.util.Objects;

import javax.inject.Inject;

/**
 * 书城
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    private User mUser;
    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeInjector();
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
    }


    /**
     * 第一次选择频道
     */
    private void showSelectChannelDialog() {
        SelectChannelDialog selectChannelDialog = SelectChannelDialog.newInstance();
//        editLabelDialog.setListener(this);
//        editLabelDialog.setTitle(title, hintText);
//        editLabelDialog.setName(label);
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), selectChannelDialog, SelectChannelDialog.TAG);
    }

    /**
     * 第一次选择漫画类型
     */
    private void showSelectManHuaTypeDialog() {
        SelectManHuaTypeDialog selectManHuaTypeDialog = SelectManHuaTypeDialog.newInstance();
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), selectManHuaTypeDialog, SelectManHuaTypeDialog.TAG);
    }

    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }

}
