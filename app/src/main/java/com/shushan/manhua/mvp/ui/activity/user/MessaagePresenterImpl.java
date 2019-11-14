package com.shushan.manhua.mvp.ui.activity.user;

import android.content.Context;

import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class MessaagePresenterImpl implements MessageControl.PresenterMessage {

    private MessageControl.MessageView mMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public MessaagePresenterImpl(Context context, UserModel model, MessageControl.MessageView view) {
        mContext = context;
        mUserModel = model;
        mMessageView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mMessageView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mUserModel.onRequestLogin(loginRequest).compose(mMessageView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mMessageView.showErrMessage(throwable),
//                        () -> mMessageView.dismissLoading());
//        mMessageView.addSubscription(disposable);
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
//                mMessageView.getLoginSuccess(response);
//            }
//        } else {
//            mMessageView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
