package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class RechargeRecordFragmentPresenterImpl implements RechargeRecordFragmentControl.RechargeRecordFragmentPresenter {

    private RechargeRecordFragmentControl.RechargeRecordView mRechargeRecordView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public RechargeRecordFragmentPresenterImpl(Context context, MineModel model, RechargeRecordFragmentControl.RechargeRecordView view) {
        mContext = context;
        mMineModel = model;
        mRechargeRecordView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mRechargeRecordView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestMineInfo(tokenRequest).compose(mRechargeRecordView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mRechargeRecordView.showErrMessage(throwable),
//                        () -> mRechargeRecordView.dismissLoading());
//        mRechargeRecordView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mRechargeRecordView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mRechargeRecordView.getMineInfoSuccess(response);
//            }
//        } else {
//            mRechargeRecordView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mRechargeRecordView = null;
    }
}
