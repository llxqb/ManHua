package com.shushan.manhua.mvp.ui.fragment.selection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerSelectionFragmentComponent;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.di.modules.SelectionFragmentModule;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.Objects;

import javax.inject.Inject;

/**
 * 漫画选集
 */

public class SelectionDetailFragment extends BaseFragment implements SelectionFragmentControl.SelectionView {

    private User mUser;
    @Inject
    SelectionFragmentControl.SelectionFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_selection, container, false);
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
        DaggerSelectionFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .bookDetailModule(new BookDetailModule((AppCompatActivity) getActivity()))
                .selectionFragmentModule(new SelectionFragmentModule(this))
                .build().inject(this);
    }



}
