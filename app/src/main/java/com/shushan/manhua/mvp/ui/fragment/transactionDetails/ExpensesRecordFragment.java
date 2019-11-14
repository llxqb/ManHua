package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerExpensesRecordFragmentComponent;
import com.shushan.manhua.di.modules.ExpensesRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.Objects;

/**
 * 消费记录
 */

public class ExpensesRecordFragment extends BaseFragment implements ExpensesRecordFragmentControl.ExpensesRecordView {

    private User mUser;
//    @Inject
//    ExpensesRecordFragmentControl.ExpensesRecordFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
//        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initData() {
    }


    private void initializeInjector() {
        DaggerExpensesRecordFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .transactionDetailsModule(new TransactionDetailsModule((AppCompatActivity) getActivity()))
                .expensesRecordFragmentModule(new ExpensesRecordFragmentModule(this))
                .build().inject(this);
    }


}
