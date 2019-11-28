package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.response.MessageResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.ResponseData;
import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class NoticeMessageFragmentPresenterImpl implements NoticeMessageFragmentControl.NoticeMessageFragmentPresenter {

    private NoticeMessageFragmentControl.NoticeMessageView mNoticeMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public NoticeMessageFragmentPresenterImpl(Context context, UserModel model, NoticeMessageFragmentControl.NoticeMessageView view) {
        mContext = context;
        mUserModel = model;
        mNoticeMessageView = view;
    }


    /**
     * 请求消息列表
     */
    @Override
    public void onRequestMessageInfo(MessageRequest messageRequest) {
        mNoticeMessageView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.onRequestMessageInfo(messageRequest).compose(mNoticeMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRequestMessageSuccess, throwable -> mNoticeMessageView.showErrMessage(throwable),
                        () -> mNoticeMessageView.dismissLoading());
        mNoticeMessageView.addSubscription(disposable);
    }

    /**
     * 请求消息列表 成功
     */
    private void requestRequestMessageSuccess(ResponseData responseData) {
        mNoticeMessageView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            MessageResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), MessageResponse.class);
            mNoticeMessageView.getMessageInfoSuccess(response);
        } else {
            mNoticeMessageView.showToast(responseData.errorMsg);
        }
    }
    
    
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mNoticeMessageView = null;
    }
}
