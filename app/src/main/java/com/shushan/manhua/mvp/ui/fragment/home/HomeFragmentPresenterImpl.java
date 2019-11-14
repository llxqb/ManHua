package com.shushan.manhua.mvp.ui.fragment.home;

import android.content.Context;

import com.shushan.homework101.help.RetryWithDelay;
import com.shushan.manhua.R;
import com.shushan.manhua.mvp.model.MainModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class HomeFragmentPresenterImpl implements HomeFragmentControl.homeFragmentPresenter {

    private HomeFragmentControl.HomeView mHomeView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public HomeFragmentPresenterImpl(Context context, MainModel model, HomeFragmentControl.HomeView homeView) {
        mContext = context;
        mHomeFragmentModel = model;
        mHomeView = homeView;
    }

//    /**
//     * 查看是否有未读消息
//     */
//    @Override
//    public void onRequestUnReadInfo(TokenRequest tokenRequest) {
//        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mHomeFragmentModel.onRequestUnReadInfo(tokenRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestUnReadInfoSuccess, throwable -> mHomeView.showErrMessage(throwable),
//                        () -> mHomeView.dismissLoading());
//        mHomeView.addSubscription(disposable);
//    }
//
//    /**
//     * 查看是否有未读消息成功
//     */
//    private void requestUnReadInfoSuccess(ResponseData responseData) {
//        mHomeView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(UnReadNewsResponse.class);
//            if (responseData.parsedData != null) {
//                UnReadNewsResponse response = (UnReadNewsResponse) responseData.parsedData;
//                mHomeView.getUnReadInfoSuccess(response);
//            }
//        } else {
//            mHomeView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }


}
