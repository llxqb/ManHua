package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class ReadingSettingPresenterImpl implements ReadingSettingControl.PresenterReadingSetting {

    private ReadingSettingControl.ReadingSettingView mReadingSettingView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public ReadingSettingPresenterImpl(Context context, MineModel model, ReadingSettingControl.ReadingSettingView view) {
        mContext = context;
        mMineModel = model;
        mReadingSettingView = view;
    }

    /**
     * 请求漫画类型
     */
    @Override
    public void onRequestManHuaType() {
        mReadingSettingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestManHuaType().compose(mReadingSettingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestManHuaTypeSuccess, throwable -> mReadingSettingView.showErrMessage(throwable),
                        () -> mReadingSettingView.dismissLoading());
        mReadingSettingView.addSubscription(disposable);
    }

    /**
     * 请求漫画类型 成功
     */
    private void requestManHuaTypeSuccess(ResponseData responseData) {
        mReadingSettingView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            BookTypeResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), BookTypeResponse.class);
            mReadingSettingView.getManHuaTypeSuccess(response);
        } else {
            mReadingSettingView.showToast(responseData.errorMsg);
        }
    }
    

    /**
     * 设置阅读偏好
     */
    @Override
    public void onReadingSettingRequest(ReadingSettingRequest request) {
        mReadingSettingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onReadingSettingRequest(request).compose(mReadingSettingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadingSettingSuccess, throwable -> mReadingSettingView.showErrMessage(throwable),
                        () -> mReadingSettingView.dismissLoading());
        mReadingSettingView.addSubscription(disposable);
    }

    /**
     * 设置阅读偏好 成功
     */
    private void requestReadingSettingSuccess(ResponseData responseData) {
        mReadingSettingView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadingSettingView.getReadingSettingSuccess();
        } else {
            mReadingSettingView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
