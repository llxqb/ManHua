package com.shushan.manhua.mvp.ui.fragment.home;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.help.RetryWithDelay;
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

    /**
     * 请求首页信息
     */
    @Override
    public void onRequestHomeInfo(HomeInfoRequest homeInfoRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestHomeInfo(homeInfoRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeInfoSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    /**
     * 请求首页信息成功
     */
    private void requestHomeInfoSuccess(ResponseData responseData) {
        mHomeView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeResponse.class);
            if (responseData.parsedData != null) {
                HomeResponse response = (HomeResponse) responseData.parsedData;
                mHomeView.getHomeInfoSuccess(response);
            }
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }


}
