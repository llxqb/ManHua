package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.DeleteMessageRequest;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.response.SentMessageResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.ResponseData;
import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class SentMessageFragmentPresenterImpl implements SentMessageFragmentControl.SentMessageFragmentPresenter {

    private SentMessageFragmentControl.SentMessageView mSentMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public SentMessageFragmentPresenterImpl(Context context, UserModel model, SentMessageFragmentControl.SentMessageView view) {
        mContext = context;
        mUserModel = model;
        mSentMessageView = view;
    }


    /**
     * 请求消息列表
     */
    @Override
    public void onRequestMessageInfo(MessageRequest messageRequest) {
        mSentMessageView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.onRequestMessageInfo(messageRequest).compose(mSentMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRequestMessageSuccess, throwable -> mSentMessageView.showErrMessage(throwable),
                        () -> mSentMessageView.dismissLoading());
        mSentMessageView.addSubscription(disposable);
    }

    /**
     * 请求消息列表 成功
     */
    private void requestRequestMessageSuccess(ResponseData responseData) {
        mSentMessageView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            SentMessageResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), SentMessageResponse.class);
            mSentMessageView.getMessageInfoSuccess(response);
//            responseData.parseData(SentMessageResponse.class);
//            if (responseData.parsedData != null) {
//                SentMessageResponse response = (SentMessageResponse) responseData.parsedData;
//                mSentMessageView.getMineInfoSuccess(response);
//            }
        } else {
            mSentMessageView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 删除评论/回复
     */
    @Override
    public void onDeleteMessageRequest(DeleteMessageRequest deleteMessageRequest) {
        mSentMessageView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.onDeleteMessageRequest(deleteMessageRequest).compose(mSentMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDeleteMessageSuccess, throwable -> mSentMessageView.showErrMessage(throwable),
                        () -> mSentMessageView.dismissLoading());
        mSentMessageView.addSubscription(disposable);
    }

    /**
     * 删除评论/回复 成功
     */
    private void requestDeleteMessageSuccess(ResponseData responseData) {
        mSentMessageView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mSentMessageView.getDeleteMessageSuccess();
        } else {
            mSentMessageView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mSentMessageView = null;
    }
}
