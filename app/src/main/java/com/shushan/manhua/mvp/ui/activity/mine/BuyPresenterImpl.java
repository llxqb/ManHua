package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.VoucherCenterResponse;
import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.PayFinishAHDIRequest;
import com.shushan.manhua.entity.request.PayFinishByUniPinRequest;
import com.shushan.manhua.entity.request.PayFinishUploadRequest;
import com.shushan.manhua.entity.request.RequestOrderAHDIRequest;
import com.shushan.manhua.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.manhua.entity.request.VoucherCenterRequest;
import com.shushan.manhua.entity.response.CreateOrderAHDIResponse;
import com.shushan.manhua.entity.response.CreateOrderByUniPinResponse;
import com.shushan.manhua.entity.response.CreateOrderResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class BuyPresenterImpl implements BuyControl.PresenterBuy {

    private BuyControl.BuyView mBuyView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public BuyPresenterImpl(Context context, MineModel model, BuyControl.BuyView view) {
        mContext = context;
        mMineModel = model;
        mBuyView = view;
    }


    /**
     * 充值中心
     */
    @Override
    public void onRequestVoucherCenter(VoucherCenterRequest voucherCenterRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestVoucherCenter(voucherCenterRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestVoucherCenterSuccess, throwable -> mBuyView.showErrMessage(throwable),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    /**
     * 充值中心 成功
     */
    private void requestVoucherCenterSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(VoucherCenterResponse.class);
            if (responseData.parsedData != null) {
                VoucherCenterResponse response = (VoucherCenterResponse) responseData.parsedData;
                mBuyView.getVoucherCenterSuccess(response);
            }
        } else {
            mBuyView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 创建订单
     */
    @Override
    public void onRequestCreateOrder(CreateOrderRequest createOrderRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrder(createOrderRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::createOrderSuccess, throwable -> mBuyView.showErrMessage(throwable),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }


    private void createOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderResponse response = (CreateOrderResponse) responseData.parsedData;
                mBuyView.getCreateOrderGoogleSuccess(response);
            }
        } else {
            mBuyView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * Google支付成功上报--Google
     */
    @Override
    public void onPayFinishUpload(PayFinishUploadRequest payFinishUpload) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onPayFinishUpload(payFinishUpload).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUploadPaySuccess, throwable -> mBuyView.getPayFinishGoogleUploadThowable(),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    private void requestUploadPaySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mBuyView.getPayFinishGoogleUploadSuccess();
        } else {
            mBuyView.getPayFinishGoogleUploadFail(responseData.errorMsg);
        }
    }


    /**
     * AHDI支付创建订单
     */
    @Override
    public void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrderAHDI(requestOrderAHDIRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateAHDIOrderSuccess, throwable -> mBuyView.showErrMessage(throwable),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    private void requestCreateAHDIOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderAHDIResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderAHDIResponse response = (CreateOrderAHDIResponse) responseData.parsedData;
                mBuyView.createOrderAHDISuccess(response);
            }
        } else {
            mBuyView.getPayFinishAHDIUploadFail(responseData.errorMsg);
        }
    }

    /**
     * AHDI支付上报（查询是否已经支付完成）
     */
    @Override
    public void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onPayFinishAHDIUpload(payFinishAHDIRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishAHDISuccess, throwable -> mBuyView.getPayFinishAHDIUploadThowable(),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    private void requestPayFinishAHDISuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mBuyView.getPayFinishAHDIUploadSuccess();
        } else {
            mBuyView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付创建订单
     */
    @Override
    public void onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest requestOrderUniPinPayRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateOrderByUniPinSuccess, throwable -> mBuyView.showErrMessage(throwable),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    private void requestCreateOrderByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderByUniPinResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderByUniPinResponse response = (CreateOrderByUniPinResponse) responseData.parsedData;
                mBuyView.createOrderByUniPinSuccess(response);
            }
        } else {
            mBuyView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付上报
     */
    @Override
    public void onPayFinishUploadByUniPin(PayFinishByUniPinRequest payFinishByUniPinRequest) {
        Disposable disposable = mMineModel.onPayFinishUploadByUniPin(payFinishByUniPinRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishByUniPinSuccess, throwable -> mBuyView.getPayFinishUploadByUniPinThowable(),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    private void requestPayFinishByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mBuyView.getPayFinishUploadByUniPinSuccess();
        } else {
            mBuyView.getPayFinishUploadByUniPinFail(mContext.getResources().getString(R.string.payment_fail));
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
