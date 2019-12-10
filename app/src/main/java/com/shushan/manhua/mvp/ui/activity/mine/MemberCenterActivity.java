package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahdi.sdk.payment.AhdiPay;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMemberCenterComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MemberCenterModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.request.PayFinishAHDIRequest;
import com.shushan.manhua.entity.request.PayFinishByUniPinRequest;
import com.shushan.manhua.entity.request.PayFinishUploadRequest;
import com.shushan.manhua.entity.request.ReceiovedBeanByVipRequest;
import com.shushan.manhua.entity.request.RequestOrderAHDIRequest;
import com.shushan.manhua.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.manhua.entity.response.CreateOrderAHDIResponse;
import com.shushan.manhua.entity.response.CreateOrderByUniPinResponse;
import com.shushan.manhua.entity.response.CreateOrderResponse;
import com.shushan.manhua.entity.response.MemberCenterResponse;
import com.shushan.manhua.entity.response.ProfitResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.help.GooglePayHelper;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.adapter.BuyAdapter;
import com.shushan.manhua.mvp.ui.adapter.ProfitAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.PayReportErrorDialog;
import com.shushan.manhua.mvp.ui.dialog.PaySelectDialog;
import com.shushan.manhua.mvp.ui.dialog.TouristsModelLoginDialog;
import com.shushan.manhua.mvp.utils.DataUtils;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.manhua.mvp.utils.googlePayUtils.Purchase;
import com.shushan.manhua.mvp.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity implements MemberCenterControl.MemberCenterView, PaySelectDialog.payChoiceDialogListener, GooglePayHelper.BuyFinishListener,
        TouristsModelLoginDialog.TouristsModelLoginListener, PayReportErrorDialog.PayReportDialogListener {

    @Inject
    MemberCenterControl.PresenterMemberCenter mPresenter;
    @BindView(R.id.money_tv)
    TextView mMoneyTv;
    @BindView(R.id.no_vip_layout)
    LinearLayout mNoVipLayout;//非VIP
    @BindView(R.id.vip_layout)
    LinearLayout mVipLayout;//VIP
    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.get_beans_tv)
    TextView mGetBeansTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.vip_profit_recycler_view)
    RecyclerView mVipProfitRecyclerView;//VIP权益
    @BindView(R.id.profit_text_tv)
    TextView mProfitTextTv;
    @BindView(R.id.profit_img_iv)
    ImageView mProfitImgIv;
    private List<MemberCenterResponse.VipinfoBean> buyResponseList = new ArrayList<>();
    private List<ProfitResponse> profitResponseList = new ArrayList<>();
    private BuyAdapter mBuyAdapter;
    private User mUser;
    private int mLoginModel;//1 是游客模式 2 是登录模式
    private GooglePayHelper mGooglePayHelper;
    /**
     * google支付util类
     */
    private IabHelper iabHelper;
    private MemberCenterResponse.VipinfoBean mVipInfoBean;
    private String errorInfo1;
    private String errorInfo2;
    /**
     * 是否是打开了UniPin支付网页页面
     */
    private boolean openUniPinWeb = false;
    private String mBuyType = "1";//1购买会员2购买嗨豆
    /**
     * 支付类型 1：Google   2:AHDI  3:Unipin
     */
    private int mPayType = 0;
    private Purchase mPurchase;//google支付
    private CreateOrderAHDIResponse mCreateOrderAHDIResponse;//
    private CreateOrderByUniPinResponse mCreateOrderByUniPinResponse;
    private int paySwitch;//过审开关

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_member_center);
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
        if (mUser.vip == 0) {
            mVipLayout.setVisibility(View.GONE);
            mNoVipLayout.setVisibility(View.VISIBLE);
        } else {
            mVipLayout.setVisibility(View.VISIBLE);
            mNoVipLayout.setVisibility(View.GONE);
        }
        //初始化google支付
        mGooglePayHelper = new GooglePayHelper(this, this);
        iabHelper = mGooglePayHelper.initGooglePay();
        paySwitch = mSharePreferenceUtil.getIntData("paySwitch", 1);
        initRecyclerView();
        onRequestMemberCenter();
    }

    private void initRecyclerView() {
        mBuyAdapter = new BuyAdapter(buyResponseList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mBuyAdapter);
        mBuyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mVipInfoBean = (MemberCenterResponse.VipinfoBean) adapter.getItem(position);
            for (MemberCenterResponse.VipinfoBean vipinfoBean1 : buyResponseList) {
                if (vipinfoBean1.isCheck) {
                    vipinfoBean1.isCheck = false;
                }
            }
            if (mVipInfoBean != null) {
                mVipInfoBean.isCheck = true;
                String money = "$ " + mVipInfoBean.getPrice();
                mMoneyTv.setText(money);
            }
            adapter.notifyDataSetChanged();
        });
        ProfitAdapter mProfitAdapter = new ProfitAdapter(profitResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVipProfitRecyclerView.setLayoutManager(linearLayoutManager);
        mVipProfitRecyclerView.setAdapter(mProfitAdapter);
        mProfitAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_01));
                    mProfitImgIv.setImageResource(R.mipmap.koin_setiap_hari);
                    break;
                case 1:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_02));
                    mProfitImgIv.setImageResource(R.mipmap.batasan_gratis);
                    break;
                case 2:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_03));
                    mProfitImgIv.setImageResource(R.mipmap.potongan_untuk_anggota);
                    break;
                case 3:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_04));
                    mProfitImgIv.setImageResource(R.mipmap.komentar_langsung_anggota);
                    break;
                case 4:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_05));
                    mProfitImgIv.setImageResource(R.mipmap.my_identitas);
                    break;
            }
        });
    }


    @Override
    public void initData() {
        errorInfo1 = getResources().getString(R.string.PayReportErrorDialog_error_info1);
        errorInfo2 = getResources().getString(R.string.PayReportErrorDialog_error_info2);
        String[] profitName = {getResources().getString(R.string.MemberCenterActivity_beans_everyday), getResources().getString(R.string.MemberCenterActivity_restriction), getResources().getString(R.string.MemberCenterActivity_discount), getResources().getString(R.string.MemberCenterActivity_barrage), getResources().getString(R.string.MemberCenterActivity_Identity)};
        int[] profitIcon = {R.mipmap.beans2, R.mipmap.vip_free_works, R.mipmap.vip_discount, R.mipmap.vip_barrage, R.mipmap.vip_honorable_status};
        for (int i = 0; i < profitName.length; i++) {
            ProfitResponse profitResponse = new ProfitResponse();
            profitResponse.cover = profitIcon[i];
            profitResponse.name = profitName[i];
            profitResponseList.add(profitResponse);
        }
    }


    @OnClick({R.id.pay_tv, R.id.common_back_iv, R.id.get_beans_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_tv:
                if (mLoginModel != 2) {
                    showTouristsLoginDialog();
                } else {
                    if (paySwitch == 0) {
                        showPayChooseDialog();
                    } else {
                        mPayType = 1;
                        GooglePayChoose();
                    }
                }
                break;
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.get_beans_tv: //领取漫豆
                if (mUser.vip == 0) {
                    //提示开通会员领取
                    showToast("Gratis untuk anggota");
                } else {
                    //会员领取漫豆
                    onRequestReceivedBeanByVip();
                }
                break;
        }
    }


    /**
     * 游客未登录弹框
     */
    private void showTouristsLoginDialog() {
        String desc = "anggota yang membeli dengan mode pengunjung hanya berlaku saat digunakan saja, Setelah APP \n" +
                "diuninstall keuntungan yang didapat juga akan hilang. ";
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
//        showPayChooseDialog();
        if (paySwitch == 0) {
            showPayChooseDialog();
        } else {
            mPayType = 1;
            GooglePayChoose();
        }
    }

    /**
     * 请求会员中心数据
     */
    private void onRequestMemberCenter() {
        MemberCenterRequest memberCenterRequest = new MemberCenterRequest();
        memberCenterRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMemberCenter(memberCenterRequest);
    }

    @Override
    public void getMemberCenterResponse(MemberCenterResponse memberCenterResponse) {
        buyResponseList = memberCenterResponse.getVipinfo();
        MemberCenterResponse.UserinfoBean userinfoBean = memberCenterResponse.getUserinfo();
        setUserDate(userinfoBean);
        for (int i = 0; i < memberCenterResponse.getVipinfo().size(); i++) {
            MemberCenterResponse.VipinfoBean vipinfoBean = memberCenterResponse.getVipinfo().get(i);
            if (i == 0) {
                vipinfoBean.isCheck = true;
                mVipInfoBean = vipinfoBean;
            }
        }
        mBuyAdapter.setNewData(memberCenterResponse.getVipinfo());
        if (memberCenterResponse.getVipinfo().size() > 0) {
            String money = "$ " + memberCenterResponse.getVipinfo().get(0).getPrice();
            mMoneyTv.setText(money);
        }
        if (memberCenterResponse.getVipget_state() == 0) {//0未领取1已领取
            mGetBeansTv.setText(getString(R.string.MemberCenterActivity_get_beans));
            mGetBeansTv.setBackgroundResource(R.drawable.gradient_get_beans_bg);
            mGetBeansTv.setTextColor(getResources().getColor(R.color.white));
        } else {
            mGetBeansTv.setText(getString(R.string.MemberCenterActivity_get_beans_ed));
            mGetBeansTv.setBackgroundResource(R.drawable.bg_gray_round_solid_20);
            mGetBeansTv.setTextColor(getResources().getColor(R.color.color999));
        }

        mUser.vip = userinfoBean.getVip();
        mBuProcessor.setLoginUser(mUser);
        if (mPayType != 0) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.PAY_SUCCESS));
        }
    }


    private void setUserDate(MemberCenterResponse.UserinfoBean userinfoBean) {
        if (userinfoBean != null) {
            mImageLoaderHelper.displayImage(this, userinfoBean.getHead_portrait(), mAvatarIv, Constant.LOADING_AVATOR);
        }
    }

    /**
     * VIP每日领取漫豆
     */
    private void onRequestReceivedBeanByVip() {
        ReceiovedBeanByVipRequest receiovedBeanByVipRequest = new ReceiovedBeanByVipRequest();
        receiovedBeanByVipRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestReceivedBeanByVip(receiovedBeanByVipRequest);
    }


    @Override
    public void getReceivedBeanByVipSuccess() {
        mGetBeansTv.setText(getString(R.string.MemberCenterActivity_get_beans_ed));
        mGetBeansTv.setBackgroundResource(R.drawable.bg_gray_round_solid_20);
        mGetBeansTv.setTextColor(getResources().getColor(R.color.color999));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
    }


    /**
     * 选择三种支付方式弹框
     */
    private void showPayChooseDialog() {
        PaySelectDialog paySelectDialog = PaySelectDialog.newInstance();
        paySelectDialog.setListener(this);
        paySelectDialog.setMoney(mVipInfoBean.getPrice(), 100);//mBeaninfoBean.getYn_price()
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), paySelectDialog, PaySelectDialog.TAG);
    }

    @Override
    public void payType(int payType) {
        mPayType = payType;
        switch (payType) {
            case 1:
                GooglePayChoose();
                break;
            case 2:
                AHDIPayChoose();
                break;
            case 3:
                UNiPinPayChoose();
                break;
        }
    }

    //1.创建订单
    private void GooglePayChoose() {
        createOrderGoogle(String.valueOf(mVipInfoBean.getVipinfo_id()), mVipInfoBean.getPrice());
    }

    /**
     * 创建订单 google支付
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrderGoogle(String relation_id, String price) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.token = mBuProcessor.getToken();
        createOrderRequest.type = mBuyType;
        createOrderRequest.relation_id = relation_id;
        createOrderRequest.money = price;
        createOrderRequest.from = Constant.FROM;
        mPresenter.onRequestCreateOrder(createOrderRequest);
    }

    //2.创建订单成功
    @Override
    public void getCreateOrderGoogleSuccess(CreateOrderResponse createOrderResponse) {
        //3 . 购买漫豆
        if (createOrderResponse.getProduct_id().equals("pulaukomik_appstore_subscription")) {//订阅
            mGooglePayHelper.queryGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no(), true);
        } else {
            mGooglePayHelper.queryGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no(), false);
        }
    }

    //4.购买漫豆成功
    @Override
    public void buyFinishSuccess(Purchase purchase) {
        mPurchase = purchase;
        payFinishGoogleUpload(purchase);
    }

    //4.购买漫豆失败
    @Override
    public void buyFinishFail() {

    }


    /**
     * 支付成功上报--GOOGLE
     */
    private void payFinishGoogleUpload(Purchase purchase) {
        //上传数据到服务器
        PayFinishUploadRequest payFinishUploadRequest = new PayFinishUploadRequest();
        payFinishUploadRequest.ord_no = purchase.getDeveloperPayload();
        payFinishUploadRequest.INAPP_DATA_SIGNATURE = purchase.getSignature();
        payFinishUploadRequest.INAPP_PURCHASE_DATA = purchase.getOriginalJson();
        mPresenter.onPayFinishUpload(payFinishUploadRequest);
    }


    @Override
    public void getPayFinishGoogleUploadSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
//        requestHomeUserInfo();
//        logAddPaymentInfoEvent(true);
        onRequestMemberCenter();
    }

    /**
     * 支付成功但是上传失败--》重新上报
     */
    @Override
    public void getPayFinishGoogleUploadFail(String error) {
        reportPayDialog(errorInfo1);
    }

    @Override
    public void getPayFinishGoogleUploadThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }

    //AHDI 创建订单
    private void AHDIPayChoose() {
        createOrderAHDI(String.valueOf(mVipInfoBean.getVipinfo_id()), String.valueOf(mVipInfoBean.getYn_price()));
    }

    /**
     * 创建订单 AHDI订单
     */
    private void createOrderAHDI(String relation_id, String price) {
        RequestOrderAHDIRequest requestOrderAHDIRequest = new RequestOrderAHDIRequest();
        requestOrderAHDIRequest.token = mBuProcessor.getToken();
        requestOrderAHDIRequest.type = mBuyType;
        requestOrderAHDIRequest.relation_id = relation_id;
        requestOrderAHDIRequest.money = price;
        mPresenter.onRequestCreateOrderAHDI(requestOrderAHDIRequest);
    }

    /**
     * 创建订单成功--AHDI pay
     */
    @Override
    public void createOrderAHDISuccess(CreateOrderAHDIResponse createOrderAHDIResponse) {
        //调用 SDK 的 startPay 方法发起支付
        AhdiPay.startPay(this, createOrderAHDIResponse.getAppid(), createOrderAHDIResponse.getApp_userid(), createOrderAHDIResponse.getToken(), (resultCode, signValue, resultInfo) -> {
            if (resultCode == AhdiPay.PAY_SUCCESS) {
                //支付成功，上传数据到服务器
                mCreateOrderAHDIResponse = createOrderAHDIResponse;
                payFinishAHDIUpload(createOrderAHDIResponse);
            } else {
                showToast(getResources().getString(R.string.payment_fail));
            }
        });
    }

    /**
     * 支付成功上报--AHDI
     */
    private void payFinishAHDIUpload(CreateOrderAHDIResponse createOrderAHDIResponse) {
        PayFinishAHDIRequest payFinishAHDIRequest = new PayFinishAHDIRequest();
        payFinishAHDIRequest.token = mBuProcessor.getToken();
        payFinishAHDIRequest.order_no = createOrderAHDIResponse.getOrder_no();
        mPresenter.onPayFinishAHDIUpload(payFinishAHDIRequest);
    }

    /**
     * AHDI上报 成功
     */
    @Override
    public void getPayFinishAHDIUploadSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
//        requestHomeUserInfo();
//        logAddPaymentInfoEvent(true);
        onRequestMemberCenter();
    }

    @Override
    public void getPayFinishAHDIUploadFail(String error) {
        reportPayDialog(errorInfo1);
    }

    @Override
    public void getPayFinishAHDIUploadThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }


    private void UNiPinPayChoose() {
        //2.创建订单 - UniPin支付
        createOrderByUniPin(String.valueOf(mVipInfoBean.getVipinfo_id()), String.valueOf(mVipInfoBean.getYn_price()));
    }

    /**
     * 创建订单 UniPin订单
     */
    private void createOrderByUniPin(String relation_id, String price) {
        RequestOrderUniPinPayRequest requestOrderUniPinPayRequest = new RequestOrderUniPinPayRequest();
        requestOrderUniPinPayRequest.token = mBuProcessor.getToken();
        requestOrderUniPinPayRequest.type = mBuyType;
        requestOrderUniPinPayRequest.relation_id = relation_id;
        requestOrderUniPinPayRequest.money = price;
        mPresenter.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest);
    }


    /**
     * 创建订单成功--UniPin
     */
    @Override
    public void createOrderByUniPinSuccess(CreateOrderByUniPinResponse createOrderByUniPinResponse) {
//        Log.e("ddd", "createOrderByUniPinResponse:" + new Gson().toJson(createOrderByUniPinResponse));
        mCreateOrderByUniPinResponse = createOrderByUniPinResponse;
        openUniPinWeb = true;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(createOrderByUniPinResponse.getUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (openUniPinWeb) {
            payFinishUnipinUpload(true);
        }
    }


    /**
     * 支付成功上报--UniPin
     */
    private void payFinishUnipinUpload(boolean showLoading) {
        openUniPinWeb = false;
        if (showLoading) {
            showLoading(getResources().getString(R.string.loading));
        }
        //UniPin支付上报
        String orderId = mCreateOrderByUniPinResponse.getOrder_no();
        PayFinishByUniPinRequest payFinishByUniPinRequest = new PayFinishByUniPinRequest();
        payFinishByUniPinRequest.order_no = orderId;
        mPresenter.onPayFinishUploadByUniPin(payFinishByUniPinRequest);
    }

    /**
     * UniPin上报成功
     */
    @Override
    public void getPayFinishUploadByUniPinSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
//        requestHomeUserInfo();
//        logAddPaymentInfoEvent(true);
        onRequestMemberCenter();
    }

    private int UnipinPayNum = 1;

    @Override
    public void getPayFinishUploadByUniPinFail(String error) {
        showToast(error);
        //不知道是取消还是上报异常操作 没有验证
        if (UnipinPayNum < 2) {
            UnipinPayNum++;
            delayTimeUnloadUnipin();
        }
    }

    @Override
    public void getPayFinishUploadByUniPinThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }

    private void delayTimeUnloadUnipin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                payFinishUnipinUpload(false);
            }
        }, 1000);//3秒后执行Runnable中的run方法
    }

    /**
     * 重新上报dialog
     */
    private void reportPayDialog(String title) {
        PayReportErrorDialog payReportErrorDialog = PayReportErrorDialog.newInstance();
        payReportErrorDialog.setListener(this);
        payReportErrorDialog.setTitle(title);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payReportErrorDialog, PayReportErrorDialog.TAG);
    }

    @Override
    public void payReportBtnOkListener() {
        switch (mPayType) {
            case 1:
                if (mPurchase != null) {
                    payFinishGoogleUpload(mPurchase);
                }
                break;
            case 2:
                if (mCreateOrderAHDIResponse != null) {
                    payFinishAHDIUpload(mCreateOrderAHDIResponse);
                }
                break;
            case 3:
                payFinishUnipinUpload(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //一定记得调用这个方法，才能调起Google回调
        if (iabHelper != null) {
            if (requestCode == Constant.GOOGLE_PAY_REQ) {
                iabHelper.handleActivityResult(requestCode, resultCode, data);
            } else if (requestCode == Constant.GOOGLE_PAY_REQ_SUBSCRIPTION) {//谷歌订阅
                iabHelper.handleActivityResult(requestCode, resultCode, data);
            }
        }
    }


    private void initInjectData() {
        DaggerMemberCenterComponent.builder().appComponent(getAppComponent())
                .memberCenterModule(new MemberCenterModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGooglePayHelper != null) mGooglePayHelper.dispose();
    }

}
