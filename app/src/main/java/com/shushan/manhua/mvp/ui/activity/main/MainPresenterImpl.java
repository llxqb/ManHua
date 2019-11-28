package com.shushan.manhua.mvp.ui.activity.main;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.LoginTouristModeRequest;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.entity.response.LoginTouristModeResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MainModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class MainPresenterImpl implements MainControl.PresenterMain {

    private MainControl.MainView mMainView;
    private final MainModel mMainModel;
    private final Context mContext;

    @Inject
    public MainPresenterImpl(Context context, MainModel model, MainControl.MainView mainView) {
        mContext = context;
        mMainModel = model;
        mMainView = mainView;
    }


    /**
     * 请求漫画类型
     */
    @Override
    public void onRequestManHuaType() {
        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestManHuaType().compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestManHuaTypeSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    /**
     * 请求漫画类型 成功
     */
    private void requestManHuaTypeSuccess(ResponseData responseData) {
        mMainView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            BookTypeResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), BookTypeResponse.class);
            mMainView.getManHuaTypeSuccess(response);
//            responseData.parseData(BookTypeResponse.class);
//            if (responseData.parsedData != null) {
//                BookTypeResponse response = (BookTypeResponse) responseData.parsedData;
//                mMainView.getManHuaTypeSuccess(response);
//            }
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 游客模式注册登陆
     */
    @Override
    public void onLoginTouristModeRequest(LoginTouristModeRequest request) {
        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onLoginTouristModeRequest(request).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLoginTouristModeSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    /**
     * 游客模式注册登陆 成功
     */
    private void requestLoginTouristModeSuccess(ResponseData responseData) {
        mMainView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(LoginTouristModeResponse.class);
            if (responseData.parsedData != null) {
                LoginTouristModeResponse response = (LoginTouristModeResponse) responseData.parsedData;
                mMainView.getLoginTouristModeSuccess(response);
            }
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 设置阅读偏好
     */
    @Override
    public void onReadingSettingRequest(ReadingSettingRequest request) {
        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onReadingSettingRequest(request).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadingSettingSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    /**
     * 设置阅读偏好 成功
     */
    private void requestReadingSettingSuccess(ResponseData responseData) {
        mMainView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            responseData.parseData(BookTypeResponse.class);
//            if (responseData.parsedData != null) {
//                LoginTouristModeResponse response = (LoginTouristModeResponse) responseData.parsedData;
//                mMainView.getLoginTouristModeSuccess(response);
//            }
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
