package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.R;
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
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class MemberCenterPresenterImpl implements MemberCenterControl.PresenterMemberCenter {

    private MemberCenterControl.MemberCenterView mMemberCenterView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public MemberCenterPresenterImpl(Context context, MineModel model, MemberCenterControl.MemberCenterView view) {
        mContext = context;
        mMineModel = model;
        mMemberCenterView = view;
    }


    /**
     * 请求会员中心
     */
    @Override
    public void onRequestMemberCenter(MemberCenterRequest memberCenterRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestMemberCenter(memberCenterRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::memberCenterRequestSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    /**
     * 请求会员中心数据 成功
     */
    private void memberCenterRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(MemberCenterResponse.class);
            if (responseData.parsedData != null) {
                MemberCenterResponse response = (MemberCenterResponse) responseData.parsedData;
                mMemberCenterView.getMemberCenterResponse(response);
            }
        } else {
            mMemberCenterView.showToast(responseData.errorMsg);
        }
    }
    /**
     * VIP每日领取漫豆
     */
    @Override
    public void onRequestReceivedBeanByVip(ReceiovedBeanByVipRequest receiovedBeanByVipRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestReceivedBeanByVip(receiovedBeanByVipRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::receivedBeanByVipRequestSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    /**
     * VIP每日领取漫豆 成功
     */
    private void receivedBeanByVipRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mMemberCenterView.getReceivedBeanByVipSuccess();
        } else {
            mMemberCenterView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 创建订单
     */
    @Override
    public void onRequestCreateOrder(CreateOrderRequest createOrderRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrder(createOrderRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::createOrderSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }


    private void createOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderResponse response = (CreateOrderResponse) responseData.parsedData;
                mMemberCenterView.getCreateOrderGoogleSuccess(response);
            }
        } else {
            mMemberCenterView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * Google支付成功上报--Google
     */
    @Override
    public void onPayFinishUpload(PayFinishUploadRequest payFinishUpload) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onPayFinishUpload(payFinishUpload).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUploadPaySuccess, throwable -> mMemberCenterView.getPayFinishGoogleUploadThowable(),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    private void requestUploadPaySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mMemberCenterView.getPayFinishGoogleUploadSuccess();
        } else {
            mMemberCenterView.getPayFinishGoogleUploadFail(responseData.errorMsg);
        }
    }


    /**
     * AHDI支付创建订单
     */
    @Override
    public void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrderAHDI(requestOrderAHDIRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateAHDIOrderSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    private void requestCreateAHDIOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderAHDIResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderAHDIResponse response = (CreateOrderAHDIResponse) responseData.parsedData;
                mMemberCenterView.createOrderAHDISuccess(response);
            }
        } else {
            mMemberCenterView.getPayFinishAHDIUploadFail(responseData.errorMsg);
        }
    }

    /**
     * AHDI支付上报（查询是否已经支付完成）
     */
    @Override
    public void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onPayFinishAHDIUpload(payFinishAHDIRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishAHDISuccess, throwable -> mMemberCenterView.getPayFinishAHDIUploadThowable(),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    private void requestPayFinishAHDISuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mMemberCenterView.getPayFinishAHDIUploadSuccess();
        } else {
            mMemberCenterView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付创建订单
     */
    @Override
    public void onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest requestOrderUniPinPayRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateOrderByUniPinSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    private void requestCreateOrderByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderByUniPinResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderByUniPinResponse response = (CreateOrderByUniPinResponse) responseData.parsedData;
                mMemberCenterView.createOrderByUniPinSuccess(response);
            }
        } else {
            mMemberCenterView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付上报
     */
    @Override
    public void onPayFinishUploadByUniPin(PayFinishByUniPinRequest payFinishByUniPinRequest) {
        Disposable disposable = mMineModel.onPayFinishUploadByUniPin(payFinishByUniPinRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishByUniPinSuccess, throwable -> mMemberCenterView.getPayFinishUploadByUniPinThowable(),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    private void requestPayFinishByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mMemberCenterView.getPayFinishUploadByUniPinSuccess();
        } else {
            mMemberCenterView.getPayFinishUploadByUniPinFail(mContext.getResources().getString(R.string.payment_fail));
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
