package com.shushan.manhua.mvp.ui.activity.login;

import android.content.Context;

import com.shushan.manhua.mvp.model.LoginModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class LoginPresenterImpl implements LoginControl.PresenterLogin {

    private LoginControl.LoginView mLoginView;
    private final LoginModel mLoginModel;
    private final Context mContext;

    @Inject
    public LoginPresenterImpl(Context context, LoginModel model, LoginControl.LoginView LoginView) {
        mContext = context;
        mLoginModel = model;
        mLoginView = LoginView;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mLoginView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mLoginModel.onRequestLogin(loginRequest).compose(mLoginView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mLoginView.showErrMessage(throwable),
//                        () -> mLoginView.dismissLoading());
//        mLoginView.addSubscription(disposable);
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
//                mLoginView.getLoginSuccess(response);
//            }
//        } else {
//            mLoginView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
