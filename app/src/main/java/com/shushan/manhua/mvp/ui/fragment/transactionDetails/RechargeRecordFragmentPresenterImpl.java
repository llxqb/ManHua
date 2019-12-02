package com.shushan.manhua.mvp.ui.fragment.transactionDetails;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.RechargeRecordResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


    /**
     * 充值记录
     */
    @Override
    public void onRequestRechargeRecord(RechargeRecordRequest rechargeRecordRequest) {
        mRechargeRecordView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestRechargeRecord(rechargeRecordRequest).compose(mRechargeRecordView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRechargeRecordSuccess, throwable -> mRechargeRecordView.showErrMessage(throwable),
                        () -> mRechargeRecordView.dismissLoading());
        mRechargeRecordView.addSubscription(disposable);
    }

    /**
     * 充值记录 成功
     */
    private void requestRechargeRecordSuccess(ResponseData responseData) {
        mRechargeRecordView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            RechargeRecordResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), RechargeRecordResponse.class);
            mRechargeRecordView.getRechargeRecordSuccess(response);
//            responseData.parseData(RechargeRecordResponse.class);
//            if (responseData.parsedData != null) {
//                RechargeRecordResponse response = (RechargeRecordResponse) responseData.parsedData;
//                mRechargeRecordView.getRechargeRecordSuccess(response);
//            }
        } else {
            mRechargeRecordView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mRechargeRecordView = null;
    }
}
