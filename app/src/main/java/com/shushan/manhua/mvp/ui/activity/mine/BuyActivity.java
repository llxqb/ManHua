package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBuyComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BuyModule;
import com.shushan.manhua.entity.constants.VoucherCenterResponse;
import com.shushan.manhua.entity.request.VoucherCenterRequest;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.adapter.BuyBeansAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.BeansExpiredDialog;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值中心 ，购买
 */
public class BuyActivity extends BaseActivity implements BuyControl.BuyView {

    @Inject
    BuyControl.PresenterBuy mPresenter;
    @BindView(R.id.beans_num_tv)
    TextView mBeansNumTv;
    @BindView(R.id.beans_num_hint_tv)
    TextView mBeansNumHintTv;
    @BindView(R.id.buy_beans_recycler_view)
    RecyclerView mBuyBeansRecyclerView;
    @BindView(R.id.money_tv)
    TextView mMoneyTv;
    private BuyBeansAdapter mBuyBeansAdapter;
    private List<VoucherCenterResponse.BeaninfoBean> buyBeansResponseList = new ArrayList<>();
    private User mUser;
    private VoucherCenterResponse mVoucherCenterResponse;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_buy);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initView() {
//        if(mUser.bean){
//        }
        initAdapter();
    }

    private void initAdapter() {
        mBuyBeansAdapter = new BuyBeansAdapter(buyBeansResponseList);
        mBuyBeansRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mBuyBeansRecyclerView.setAdapter(mBuyBeansAdapter);
        mBuyBeansAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            VoucherCenterResponse.BeaninfoBean beaninfoBean = (VoucherCenterResponse.BeaninfoBean) adapter.getItem(position);
            for (VoucherCenterResponse.BeaninfoBean beaninfoBean1 : buyBeansResponseList) {
                if (beaninfoBean1.isCheck) {
                    beaninfoBean1.isCheck = false;
                }
            }
            if (beaninfoBean != null) {
                beaninfoBean.isCheck = true;
                String moneyValue = "$ " + beaninfoBean.getPrice();
                mMoneyTv.setText(moneyValue);
            }
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initData() {
        onRequestData();

    }


    @OnClick({R.id.common_back_iv, R.id.common_right_tv, R.id.invalid_beans_detail_tv, R.id.buy_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.common_right_tv://明细
                startActivitys(TransactionDetailsActivity.class);
                break;
            case R.id.invalid_beans_detail_tv://详情
                showBeansExpiredDialog();
                break;
            case R.id.buy_tv://立即购买

                break;
        }
    }

    /**
     * 充值中心
     */
    private void onRequestData() {
        VoucherCenterRequest voucherCenterRequest = new VoucherCenterRequest();
        voucherCenterRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestVoucherCenter(voucherCenterRequest);
    }

    @Override
    public void getVoucherCenterSuccess(VoucherCenterResponse voucherCenterResponse) {
        mVoucherCenterResponse = voucherCenterResponse;
        buyBeansResponseList = voucherCenterResponse.getBeaninfo();
        for (int i = 0; i < buyBeansResponseList.size(); i++) {
            if (i == 0) {
                VoucherCenterResponse.BeaninfoBean beaninfoBean = buyBeansResponseList.get(i);
                beaninfoBean.isCheck = true;
                String moneyValue = "$ " + beaninfoBean.getPrice();
                mMoneyTv.setText(moneyValue);
            }
        }
        mBuyBeansAdapter.setNewData(voucherCenterResponse.getBeaninfo());
        mBeansNumTv.setText(String.valueOf(voucherCenterResponse.getBean()));
        String expireBeanValue = voucherCenterResponse.getExpire_bean() + " koin, setelah " + DateUtil.getStrTime(voucherCenterResponse.getExpire_time(), "dd") + " hari gagal";
        mBeansNumHintTv.setText(expireBeanValue);

    }

    /**
     * 显示未使用的漫豆快要过期弹框
     */
    private void showBeansExpiredDialog() {
        BeansExpiredDialog beansExpiredDialog = BeansExpiredDialog.newInstance();
        if (mVoucherCenterResponse != null) {
            beansExpiredDialog.setData(mVoucherCenterResponse.getExpire_time(), String.valueOf(mVoucherCenterResponse.getExpire_bean()));
        }
        DialogFactory.showDialogFragment(getSupportFragmentManager(), beansExpiredDialog, BeansExpiredDialog.TAG);
    }

    private void initInjectData() {
        DaggerBuyComponent.builder().appComponent(getAppComponent())
                .buyModule(new BuyModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
