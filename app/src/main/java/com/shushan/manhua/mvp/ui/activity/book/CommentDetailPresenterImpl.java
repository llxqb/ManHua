package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class CommentDetailPresenterImpl implements CommentDetailControl.PresenterCommentDetail {

    private CommentDetailControl.CommentDetailView mCommentDetailView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public CommentDetailPresenterImpl(Context context, BookModel model, CommentDetailControl.CommentDetailView view) {
        mContext = context;
        mBookModel = model;
        mCommentDetailView = view;
    }


    /**
     * 评论详情
     */
    @Override
    public void onRequestCommentDetail(CommentDetailRequest commentDetailRequest) {
        mCommentDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestCommentDetail(commentDetailRequest).compose(mCommentDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCommentDetailSuccess, throwable -> mCommentDetailView.showErrMessage(throwable),
                        () -> mCommentDetailView.dismissLoading());
        mCommentDetailView.addSubscription(disposable);
    }

    /**
     * 评论详情 成功
     */
    private void requestCommentDetailSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CommentDetailResponse.class);
            if (responseData.parsedData != null) {
                CommentDetailResponse response = (CommentDetailResponse) responseData.parsedData;
                mCommentDetailView.getCommentDetailSuccess(response);
            }
        } else {
            mCommentDetailView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 评论点赞
     */
    @Override
    public void onSupportRequest(SupportRequest commentSuggestRequest) {
        mCommentDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(commentSuggestRequest).compose(mCommentDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::commentSuggestSuccess, throwable -> mCommentDetailView.showErrMessage(throwable),
                        () -> mCommentDetailView.dismissLoading());
        mCommentDetailView.addSubscription(disposable);
    }


    /**
     * 评论点赞 成功
     */
    private void commentSuggestSuccess(ResponseData responseData) {
        mCommentDetailView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mCommentDetailView.getSupportSuccess();
        } else {
            mCommentDetailView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 评论用户评论
     */
    @Override
    public void onPublishCommentUser(PublishCommentUserRequest publishCommentUserRequest) {
        mCommentDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onPublishCommentUser(publishCommentUserRequest).compose(mCommentDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::publishCommentUserSuccess, throwable -> mCommentDetailView.showErrMessage(throwable),
                        () -> mCommentDetailView.dismissLoading());
        mCommentDetailView.addSubscription(disposable);
    }

    /**
     * 评论用户评论 成功
     */
    private void publishCommentUserSuccess(ResponseData responseData) {
        mCommentDetailView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mCommentDetailView.getCommentUserSuccess();
        } else {
            mCommentDetailView.showToast(responseData.errorMsg);
        }
    }

    

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
