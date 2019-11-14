package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class SentMessageFragmentPresenterImpl implements SentMessageFragmentControl.SentMessageFragmentPresenter {

    private SentMessageFragmentControl.SentMessageView mSentMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public SentMessageFragmentPresenterImpl(Context context, UserModel model, SentMessageFragmentControl.SentMessageView view) {
        mContext = context;
        mUserModel = model;
        mSentMessageView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mSentMessageView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mUserModel.onRequestMineInfo(tokenRequest).compose(mSentMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mSentMessageView.showErrMessage(throwable),
//                        () -> mSentMessageView.dismissLoading());
//        mSentMessageView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mSentMessageView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mSentMessageView.getMineInfoSuccess(response);
//            }
//        } else {
//            mSentMessageView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mSentMessageView = null;
    }
}
