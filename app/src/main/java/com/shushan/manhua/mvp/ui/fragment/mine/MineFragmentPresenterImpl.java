package com.shushan.manhua.mvp.ui.fragment.mine;

import android.content.Context;

import com.shushan.manhua.mvp.model.MainModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class MineFragmentPresenterImpl implements MineFragmentControl.MineFragmentPresenter {

    private MineFragmentControl.MineView mMineView;
    private final MainModel mMainModel;
    private final Context mContext;

    @Inject
    public MineFragmentPresenterImpl(Context context, MainModel model, MineFragmentControl.MineView MineView) {
        mContext = context;
        mMainModel = model;
        mMineView = MineView;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mMineView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mMainModel.onRequestMineInfo(tokenRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mMineView.showErrMessage(throwable),
//                        () -> mMineView.dismissLoading());
//        mMineView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mMineView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mMineView.getMineInfoSuccess(response);
//            }
//        } else {
//            mMineView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mMineView = null;
    }
}
