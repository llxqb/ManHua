package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.ExpensesRecordResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


    /**
     * 消费记录
     */
    @Override
    public void onRequestRechargeRecord(RechargeRecordRequest rechargeRecordRequest) {
        mExpensesRecordView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestRechargeRecord(rechargeRecordRequest).compose(mExpensesRecordView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRechargeRecordSuccess, throwable -> mExpensesRecordView.showErrMessage(throwable),
                        () -> mExpensesRecordView.dismissLoading());
        mExpensesRecordView.addSubscription(disposable);
    }

    /**
     * 消费记录 成功
     */
    private void requestRechargeRecordSuccess(ResponseData responseData) {
        mExpensesRecordView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            ExpensesRecordResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), ExpensesRecordResponse.class);
            mExpensesRecordView.getExpensesRecordSuccess(response);
//            responseData.parseData(RechargeRecordResponse.class);
//            if (responseData.parsedData != null) {
//                RechargeRecordResponse response = (RechargeRecordResponse) responseData.parsedData;
//                mExpensesRecordView.getRechargeRecordSuccess(response);
//            }
        } else {
            mExpensesRecordView.showToast(responseData.errorMsg);
        }
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mExpensesRecordView = null;
    }
}
