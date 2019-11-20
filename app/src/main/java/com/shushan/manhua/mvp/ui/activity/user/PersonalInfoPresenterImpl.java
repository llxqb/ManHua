package com.shushan.manhua.mvp.ui.activity.user;

import android.content.Context;

import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class PersonalInfoPresenterImpl implements PersonalInfoControl.PresenterPersonalInfo {

    private PersonalInfoControl.PersonalInfoView mPersonalInfoView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public PersonalInfoPresenterImpl(Context context, UserModel model, PersonalInfoControl.PersonalInfoView view) {
        mContext = context;
        mUserModel = model;
        mPersonalInfoView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mUserModel.onRequestLogin(loginRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
//                        () -> mPersonalInfoView.dismissLoading());
//        mPersonalInfoView.addSubscription(disposable);
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
//                mPersonalInfoView.getLoginSuccess(response);
//            }
//        } else {
//            mPersonalInfoView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
