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
import com.shushan.manhua.di.components.DaggerRechargeRecordFragmentComponent;
import com.shushan.manhua.di.modules.RechargeRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.Objects;

/**
 * 充值记录
 */

public class RechargeRecordFragment extends BaseFragment implements RechargeRecordFragmentControl.RechargeRecordView {

    private User mUser;
//    @Inject
//    RechargeRecordFragmentControl.RechargeRecordFragmentPresenter mPresenter;

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
        DaggerRechargeRecordFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .transactionDetailsModule(new TransactionDetailsModule((AppCompatActivity) getActivity()))
                .rechargeRecordFragmentModule(new RechargeRecordFragmentModule(this))
                .build().inject(this);
    }



}
