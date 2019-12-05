package com.shushan.manhua.mvp.ui.fragment.mine;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.MineRequest;
import com.shushan.manhua.entity.request.UnReadMessageRequest;
import com.shushan.manhua.entity.response.MineInfoResponse;
import com.shushan.manhua.entity.response.UnReadMessageResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MainModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


    /**
     * 查询我的
     */
    @Override
    public void onRequestMineInfo(MineRequest mineRequest) {
        mMineView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestMineInfo(mineRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestMineInfoSuccess, throwable -> mMineView.showErrMessage(throwable),
                        () -> mMineView.dismissLoading());
        mMineView.addSubscription(disposable);
    }

    /**
     * 查询我的 成功
     */
    private void requestMineInfoSuccess(ResponseData responseData) {
        mMineView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(MineInfoResponse.class);
            if (responseData.parsedData != null) {
                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
                mMineView.getMineInfoSuccess(response);
            }
        } else {
            mMineView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 查询是否有未读消息
     */
    @Override
    public void onRequestUnReadMessage(UnReadMessageRequest unReadMessageRequest) {
        mMineView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestUnReadMessage(unReadMessageRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUnReadMessageSuccess, throwable -> mMineView.showErrMessage(throwable),
                        () -> mMineView.dismissLoading());
        mMineView.addSubscription(disposable);
    }

    /**
     * 查询是否有未读消息 成功
     */
    private void requestUnReadMessageSuccess(ResponseData responseData) {
        mMineView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(UnReadMessageResponse.class);
            if (responseData.parsedData != null) {
                UnReadMessageResponse response = (UnReadMessageResponse) responseData.parsedData;
                mMineView.getUnReadMessageSuccess(response);
            }
        } else {
            mMineView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mMineView = null;
    }
}
