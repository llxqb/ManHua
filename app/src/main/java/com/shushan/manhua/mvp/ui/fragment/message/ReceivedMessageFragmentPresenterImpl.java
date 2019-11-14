package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class ReceivedMessageFragmentPresenterImpl implements ReceivedMessageFragmentControl.ReceivedMessageFragmentPresenter {

    private ReceivedMessageFragmentControl.ReceivedMessageView mReceivedMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public ReceivedMessageFragmentPresenterImpl(Context context, UserModel model, ReceivedMessageFragmentControl.ReceivedMessageView view) {
        mContext = context;
        mUserModel = model;
        mReceivedMessageView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mReceivedMessageView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mUserModel.onRequestMineInfo(tokenRequest).compose(mReceivedMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mReceivedMessageView.showErrMessage(throwable),
//                        () -> mReceivedMessageView.dismissLoading());
//        mReceivedMessageView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mReceivedMessageView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mReceivedMessageView.getMineInfoSuccess(response);
//            }
//        } else {
//            mReceivedMessageView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mReceivedMessageView = null;
    }
}
