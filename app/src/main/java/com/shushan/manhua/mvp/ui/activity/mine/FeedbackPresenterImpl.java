package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.SubmitFeedbackRequest;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class FeedbackPresenterImpl implements FeedbackControl.PresenterFeedback {

    private FeedbackControl.FeedbackView mFeedbackView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public FeedbackPresenterImpl(Context context, MineModel model, FeedbackControl.FeedbackView view) {
        mContext = context;
        mMineModel = model;
        mFeedbackView = view;
    }


    /**
     * 提交辅导反馈
     */
    @Override
    public void onSubmitFeedbackRequest(SubmitFeedbackRequest submitFeedbackRequest) {
        mFeedbackView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onSubmitFeedbackRequest(submitFeedbackRequest).compose(mFeedbackView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSubmitFeedbackSuccess, throwable -> mFeedbackView.showErrMessage(throwable),
                        () -> mFeedbackView.dismissLoading());
        mFeedbackView.addSubscription(disposable);
    }

    /**
     * 提交辅导反馈 成功
     */
    private void requestSubmitFeedbackSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mFeedbackView.getSubmitFeedbackSuccess();
        } else {
            mFeedbackView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
