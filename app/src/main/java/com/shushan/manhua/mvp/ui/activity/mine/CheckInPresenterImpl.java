package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.ReceiveTaskRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.entity.request.SignDataRequest;
import com.shushan.manhua.entity.request.SignRequest;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.entity.response.SignDataResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class CheckInPresenterImpl implements CheckInControl.PresenterCheckIn {

    private CheckInControl.CheckInView mCheckInView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public CheckInPresenterImpl(Context context, MineModel model, CheckInControl.CheckInView view) {
        mContext = context;
        mMineModel = model;
        mCheckInView = view;
    }


    /**
     * 请求签到数据
     */
    @Override
    public void onRequestSignData(SignDataRequest signDataRequest) {
        mCheckInView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestSignData(signDataRequest).compose(mCheckInView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSignDataSuccess, throwable -> mCheckInView.showErrMessage(throwable),
                        () -> mCheckInView.dismissLoading());
        mCheckInView.addSubscription(disposable);
    }

    /**
     * 请求签到数据 成功
     */
    private void requestSignDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(SignDataResponse.class);
            if (responseData.parsedData != null) {
                SignDataResponse response = (SignDataResponse) responseData.parsedData;
                mCheckInView.getSignDataSuccess(response);
            }
        } else {
            mCheckInView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 请求推荐数据
     */
    @Override
    public void onRecommendInfo(RecommendRequest recommendRequest) {
        mCheckInView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRecommendInfo(recommendRequest).compose(mCheckInView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::recommendInfoSuccess, throwable -> mCheckInView.showErrMessage(throwable),
                        () -> mCheckInView.dismissLoading());
        mCheckInView.addSubscription(disposable);
    }

    /**
     * 请求推荐数据成功
     */
    private void recommendInfoSuccess(ResponseData responseData) {
        mCheckInView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            RecommendResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), RecommendResponse.class);
            mCheckInView.getRecommendInfoSuccess(response);
        } else {
            mCheckInView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 签到
     */
    @Override
    public void onRequestSign(SignRequest signRequest) {
        mCheckInView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestSign(signRequest).compose(mCheckInView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSignSuccess, throwable -> mCheckInView.showErrMessage(throwable),
                        () -> mCheckInView.dismissLoading());
        mCheckInView.addSubscription(disposable);
    }

    /**
     * 签到 成功
     */
    private void requestSignSuccess(ResponseData responseData) {
        mCheckInView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            RecommendResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), RecommendResponse.class);
            mCheckInView.getSignSuccess();
        } else {
            mCheckInView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 领取任务
     */
    @Override
    public void onRequestReceiveTask(ReceiveTaskRequest receiveTaskRequest) {
        mCheckInView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestReceiveTask(receiveTaskRequest).compose(mCheckInView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::receiveTaskRequestSuccess, throwable -> mCheckInView.showErrMessage(throwable),
                        () -> mCheckInView.dismissLoading());
        mCheckInView.addSubscription(disposable);
    }

    /**
     * 领取任务 成功
     */
    private void receiveTaskRequestSuccess(ResponseData responseData) {
        mCheckInView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mCheckInView.getReceiveTaskSuccess();
        } else {
            mCheckInView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
