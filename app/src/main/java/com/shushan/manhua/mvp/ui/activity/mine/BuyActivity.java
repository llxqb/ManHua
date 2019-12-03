package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBuyComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BuyModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.constants.VoucherCenterResponse;
import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.VoucherCenterRequest;
import com.shushan.manhua.entity.response.CreateOrderResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.help.GooglePayHelper;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.adapter.BuyBeansAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.BeansExpiredDialog;
import com.shushan.manhua.mvp.ui.dialog.PaySelectDialog;
import com.shushan.manhua.mvp.ui.dialog.TouristsModelLoginDialog;
import com.shushan.manhua.mvp.utils.DataUtils;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.manhua.mvp.utils.googlePayUtils.Purchase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值中心 ，购买
 */
public class BuyActivity extends BaseActivity implements BuyControl.BuyView, PaySelectDialog.payChoiceDialogListener, GooglePayHelper.BuyFinishListener,TouristsModelLoginDialog.TouristsModelLoginListener {

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
    private VoucherCenterResponse.BeaninfoBean mBeaninfoBean;//adapter item 点击
    private int mLoginModel;//1 是游客模式 2 是登录模式
    private GooglePayHelper mGooglePayHelper;
    /**
     * google支付util类
     */
    private IabHelper iabHelper;


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_buy);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
        mUser = mBuProcessor.getUser();
        mLoginModel = mBuProcessor.getLoginModel();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA)) {
                mLoginModel = mBuProcessor.getLoginModel();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA);
    }

    @Override
    public void initView() {
        //初始化google支付
        mGooglePayHelper = new GooglePayHelper(this, this);
        iabHelper = mGooglePayHelper.initGooglePay();
        initAdapter();
    }

    private void initAdapter() {
        mBuyBeansAdapter = new BuyBeansAdapter(buyBeansResponseList);
        mBuyBeansRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mBuyBeansRecyclerView.setAdapter(mBuyBeansAdapter);
        mBuyBeansAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mBeaninfoBean = (VoucherCenterResponse.BeaninfoBean) adapter.getItem(position);
            for (VoucherCenterResponse.BeaninfoBean beaninfoBean1 : buyBeansResponseList) {
                if (beaninfoBean1.isCheck) {
                    beaninfoBean1.isCheck = false;
                }
            }
            if (mBeaninfoBean != null) {
                mBeaninfoBean.isCheck = true;
                String moneyValue = "$ " + mBeaninfoBean.getPrice();
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
                if (mLoginModel != 2) {
                    showTouristsLoginDialog();
                } else {
                    showPayChooseDialog();
                }
                break;
        }
    }
    /**
     * 游客未登录弹框
     */
    private void showTouristsLoginDialog() {
        String desc = "koin yang diisi ulang dengan mode pengunjung hanya berlaku saat digunakan saja, setelah APP diunistall koin juga akan hilang. ";
        TouristsModelLoginDialog touristsModelLoginDialog = TouristsModelLoginDialog.newInstance();
        touristsModelLoginDialog.setListener(this);
        touristsModelLoginDialog.setDesc(desc);
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), touristsModelLoginDialog, TouristsModelLoginDialog.TAG);
    }

    @Override
    public void touristsModelLoginInBtnOkListener() {
        startActivitys(LoginActivity.class);
    }

    @Override
    public void touristsModelPurchaseBtnOkListener() {
        showPayChooseDialog();
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
                mBeaninfoBean = beaninfoBean;
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


    /**
     * 选择三种支付方式弹框
     */
    private void showPayChooseDialog() {
        PaySelectDialog paySelectDialog = PaySelectDialog.newInstance();
        paySelectDialog.setListener(this);
        paySelectDialog.setMoney(mBeaninfoBean.getPrice(), 100);//mBeaninfoBean.getYn_price()
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), paySelectDialog, PaySelectDialog.TAG);
    }

    @Override
    public void payType(int payType) {
        LogUtils.e("payType:" + payType);
        switch (payType) {
            case 1:
                GooglePayChoose();
                break;
            case 2:
//                AHDIPayChoose();
                break;
            case 3:
//                UNiPinPayChoose();
                break;
        }
    }

    //1.创建订单
    private void GooglePayChoose() {
        createOrderGoogle(String.valueOf(mBeaninfoBean.getBeans_id()), mBeaninfoBean.getPrice());
    }

    /**
     * 创建订单 google支付
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrderGoogle(String relation_id, String price) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.token = mBuProcessor.getToken();
        createOrderRequest.type = "2";
        createOrderRequest.relation_id = relation_id;
        createOrderRequest.money = price;
        createOrderRequest.from = Constant.FROM;
        mPresenter.onRequestCreateOrder(createOrderRequest);
    }

    //2.创建订单成功
    @Override
    public void getCreateOrderGoogleSuccess(CreateOrderResponse createOrderResponse) {
        //3 . 购买漫豆
        mGooglePayHelper.queryGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no());
    }

    //4.购买漫豆成功
    @Override
    public void buyFinishSuccess(Purchase purchase) {

    }

    //4.购买漫豆失败
    @Override
    public void buyFinishFail() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //一定记得调用这个方法，才能调起Google回调
        if (iabHelper != null && requestCode == Constant.GOOGLE_PAY_REQ) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }


    private void initInjectData() {
        DaggerBuyComponent.builder().appComponent(getAppComponent())
                .buyModule(new BuyModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGooglePayHelper != null) mGooglePayHelper.dispose();
    }



}
