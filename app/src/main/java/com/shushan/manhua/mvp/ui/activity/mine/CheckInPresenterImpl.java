package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class CheckInPresenterImpl implements CheckInControl.PresenterCheckIn {

    private CheckInControl.CheckInView mCheckInView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public CheckInPresenterImpl(Context context, MineModel model, CheckInControl.CheckInView view) {
        mContext = context;
        mMineModel = model;
        mCheckInView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mCheckInView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestLogin(loginRequest).compose(mCheckInView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mCheckInView.showErrMessage(throwable),
//                        () -> mCheckInView.dismissLoading());
//        mCheckInView.addSubscription(disposable);
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
//                mCheckInView.getLoginSuccess(response);
//            }
//        } else {
//            mCheckInView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
