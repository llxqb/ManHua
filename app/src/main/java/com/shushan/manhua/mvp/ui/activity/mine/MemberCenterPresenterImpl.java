package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;


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


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestLogin(loginRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
//                        () -> mMemberCenterView.dismissLoading());
//        mMemberCenterView.addSubscription(disposable);
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
//                mMemberCenterView.getLoginSuccess(response);
//            }
//        } else {
//            mMemberCenterView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
