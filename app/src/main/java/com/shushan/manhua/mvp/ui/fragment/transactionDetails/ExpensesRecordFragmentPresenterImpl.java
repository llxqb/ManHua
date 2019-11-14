package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

import android.content.Context;

import com.shushan.manhua.mvp.model.MineModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class ExpensesRecordFragmentPresenterImpl implements ExpensesRecordFragmentControl.ExpensesRecordFragmentPresenter {

    private ExpensesRecordFragmentControl.ExpensesRecordView mExpensesRecordView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public ExpensesRecordFragmentPresenterImpl(Context context, MineModel model, ExpensesRecordFragmentControl.ExpensesRecordView view) {
        mContext = context;
        mMineModel = model;
        mExpensesRecordView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mExpensesRecordView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMineModel.onRequestMineInfo(tokenRequest).compose(mExpensesRecordView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mExpensesRecordView.showErrMessage(throwable),
//                        () -> mExpensesRecordView.dismissLoading());
//        mExpensesRecordView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mExpensesRecordView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mExpensesRecordView.getMineInfoSuccess(response);
//            }
//        } else {
//            mExpensesRecordView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mExpensesRecordView = null;
    }
}
