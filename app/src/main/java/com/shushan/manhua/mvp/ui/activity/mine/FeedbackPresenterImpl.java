package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class FeedbackPresenterImpl implements FeedbackControl.PresenterFeedback {

    private FeedbackControl.FeedbackView mFeedbackView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public FeedbackPresenterImpl(Context context, MineModel model, FeedbackControl.FeedbackView view) {
        mContext = context;
        mMineModel = model;
        mFeedbackView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mFeedbackView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestLogin(loginRequest).compose(mFeedbackView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mFeedbackView.showErrMessage(throwable),
//                        () -> mFeedbackView.dismissLoading());
//        mFeedbackView.addSubscription(disposable);
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
//                mFeedbackView.getLoginSuccess(response);
//            }
//        } else {
//            mFeedbackView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
