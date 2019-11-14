package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class PurchasedPresenterImpl implements PurchasedControl.PresenterPurchased {

    private PurchasedControl.PurchasedView mPurchasedView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public PurchasedPresenterImpl(Context context, MineModel model, PurchasedControl.PurchasedView view) {
        mContext = context;
        mMineModel = model;
        mPurchasedView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mPurchasedView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestLogin(loginRequest).compose(mPurchasedView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mPurchasedView.showErrMessage(throwable),
//                        () -> mPurchasedView.dismissLoading());
//        mPurchasedView.addSubscription(disposable);
//    }
//
//    /**
//     * 登录成功
//     */
//    private void requestLoginSuccess(ResponseData responseData) {
//        if (responseData.resultCode == 0) {
//            responseData.parseData(LoginResponse.class);
//            if (responseData.parsedData != null) {
//                LoginResponse response = (LoginResponse) responseData.parsedData;
//                mPurchasedView.getLoginSuccess(response);
//            }
//        } else {
//            mPurchasedView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
