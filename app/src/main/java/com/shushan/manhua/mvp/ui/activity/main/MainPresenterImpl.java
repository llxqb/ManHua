package com.shushan.manhua.mvp.ui.activity.main;

import android.content.Context;

import com.shushan.manhua.mvp.model.MainModel;

import javax.inject.Inject;

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


//    /**
//     * 上传设备信息
//     */
//    @Override
//    public void uploadDeviceInfo(DeviceInfoRequest deviceInfoRequest) {
//        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMainModel.uploadDeviceInfo(deviceInfoRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::uploadDeviceInfoSuccess, throwable -> mMainView.showErrMessage(throwable),
//                        () -> mMainView.dismissLoading());
//        mMainView.addSubscription(disposable);
//    }
//
//    /**
//     * 上传设备信息 成功
//     */
//    private void uploadDeviceInfoSuccess(ResponseData responseData) {
//        mMainView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode != 0) {
//            mMainView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
