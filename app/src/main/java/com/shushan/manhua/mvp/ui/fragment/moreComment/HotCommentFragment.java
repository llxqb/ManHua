package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerHotCommentFragmentComponent;
import com.shushan.manhua.di.modules.HotCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.Objects;

import javax.inject.Inject;

/**
 * 最热评论
 */

public class HotCommentFragment extends BaseFragment implements HotCommentFragmentControl.HotCommentView {

    @Inject
    HotCommentFragmentControl.HotCommentFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_comment, container, false);
        initializeInjector();
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



    private void initializeInjector() {
        DaggerHotCommentFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .moreCommentModule(new MoreCommentModule((AppCompatActivity) getActivity()))
                .hotCommentFragmentModule(new HotCommentFragmentModule(this))
                .build().inject(this);
    }


}
