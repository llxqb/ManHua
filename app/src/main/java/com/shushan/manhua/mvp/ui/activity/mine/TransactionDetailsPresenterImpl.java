package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class TransactionDetailsPresenterImpl implements TransactionDetailsControl.PresenterTransactionDetails {

    private TransactionDetailsControl.TransactionDetailsView mTransactionDetailsView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public TransactionDetailsPresenterImpl(Context context, MineModel model, TransactionDetailsControl.TransactionDetailsView view) {
        mContext = context;
        mMineModel = model;
        mTransactionDetailsView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mTransactionDetailsView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestLogin(loginRequest).compose(mTransactionDetailsView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mTransactionDetailsView.showErrMessage(throwable),
//                        () -> mTransactionDetailsView.dismissLoading());
//        mTransactionDetailsView.addSubscription(disposable);
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
//                mTransactionDetailsView.getLoginSuccess(response);
//            }
//        } else {
//            mTransactionDetailsView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
