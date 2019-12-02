package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

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
import com.shushan.manhua.di.components.DaggerExpensesRecordFragmentComponent;
import com.shushan.manhua.di.modules.ExpensesRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.ExpensesRecordResponse;
import com.shushan.manhua.entity.response.RechargeRecordResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.ExpensesRecordAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消费记录
 */

public class ExpensesRecordFragment extends BaseFragment implements ExpensesRecordFragmentControl.ExpensesRecordView {

    @Inject
    ExpensesRecordFragmentControl.ExpensesRecordFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private ExpensesRecordAdapter mExpensesRecordAdapter;
    private List<ExpensesRecordResponse> expensesRecordResponseList = new ArrayList<>();
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_record, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mUser = mBuProcessor.getUser();
        mExpensesRecordAdapter = new ExpensesRecordAdapter(expensesRecordResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mExpensesRecordAdapter);
    }


    @Override
    public void initData() {
        onRequestRechargeRecord();
    }

    /**
     * 消费记录
     */
    private void onRequestRechargeRecord() {
        RechargeRecordRequest rechargeRecordRequest = new RechargeRecordRequest();
        rechargeRecordRequest.token = mBuProcessor.getToken();
        rechargeRecordRequest.label = "2"; //1充值记录2消费记录
        rechargeRecordRequest.page = String.valueOf(page);
        rechargeRecordRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestRechargeRecord(rechargeRecordRequest);
    }

    /**
     * 消费记录 成功
     */
    @Override
    public void getRechargeRecordSuccess(RechargeRecordResponse rechargeRecordResponse) {

    }

    private void initializeInjector() {
        DaggerExpensesRecordFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .transactionDetailsModule(new TransactionDetailsModule((AppCompatActivity) getActivity()))
                .expensesRecordFragmentModule(new ExpensesRecordFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
