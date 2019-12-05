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
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerRechargeRecordFragmentComponent;
import com.shushan.manhua.di.modules.RechargeRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.RechargeRecordResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.RechargeRecordAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 充值记录
 */

public class RechargeRecordFragment extends BaseFragment implements RechargeRecordFragmentControl.RechargeRecordView , BaseQuickAdapter.RequestLoadMoreListener{

    @Inject
    RechargeRecordFragmentControl.RechargeRecordFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private RechargeRecordAdapter mRechargeRecordAdapter;
    private List<RechargeRecordResponse> rechargeRecordResponseList = new ArrayList<>();
    private int page = 1;
    private View mEmptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge_record, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        initEmptyView();
        mUser = mBuProcessor.getUser();
        mRechargeRecordAdapter = new RechargeRecordAdapter(rechargeRecordResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mRechargeRecordAdapter);
        mRechargeRecordAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    @Override
    public void initData() {
        onRequestRechargeRecord();
    }

    /**
     * 充值记录
     */
    private void onRequestRechargeRecord() {
        RechargeRecordRequest rechargeRecordRequest = new RechargeRecordRequest();
        rechargeRecordRequest.token = mBuProcessor.getToken();
        rechargeRecordRequest.label = "1"; //1充值记录2消费记录
        rechargeRecordRequest.page = String.valueOf(page);
        rechargeRecordRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestRechargeRecord(rechargeRecordRequest);
    }

    @Override
    public void onLoadMoreRequested() {

    }

    /**
     * 充值记录 成功
     */
    @Override
    public void getRechargeRecordSuccess(RechargeRecordResponse rechargeRecordResponse) {

    }


    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_history);
        emptyTv.setText(getResources().getString(R.string.ReadingHistoryActivity_empty_tv));
    }

    private void initializeInjector() {
        DaggerRechargeRecordFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .transactionDetailsModule(new TransactionDetailsModule((AppCompatActivity) getActivity()))
                .rechargeRecordFragmentModule(new RechargeRecordFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
