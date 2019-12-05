package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.response.ReceivedMessageResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.ResponseData;
import com.shushan.manhua.mvp.model.UserModel;
import com.shushan.manhua.mvp.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class ReceivedMessageFragmentPresenterImpl implements ReceivedMessageFragmentControl.ReceivedMessageFragmentPresenter {

    private ReceivedMessageFragmentControl.ReceivedMessageView mReceivedMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public ReceivedMessageFragmentPresenterImpl(Context context, UserModel model, ReceivedMessageFragmentControl.ReceivedMessageView view) {
        mContext = context;
        mUserModel = model;
        mReceivedMessageView = view;
    }


    /**
     * 请求消息列表
     */
    @Override
    public void onRequestMessageInfo(MessageRequest messageRequest) {
        mReceivedMessageView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.onRequestMessageInfo(messageRequest).compose(mReceivedMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRequestMessageSuccess, throwable -> mReceivedMessageView.showErrMessage(throwable),
                        () -> mReceivedMessageView.dismissLoading());
        mReceivedMessageView.addSubscription(disposable);
    }

    /**
     * 请求消息列表 成功
     */
    private void requestRequestMessageSuccess(ResponseData responseData) {
        mReceivedMessageView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            ReceivedMessageResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), ReceivedMessageResponse.class);
            mReceivedMessageView.getMessageInfoSuccess(response);
        } else {
            mReceivedMessageView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mReceivedMessageView = null;
    }
}
