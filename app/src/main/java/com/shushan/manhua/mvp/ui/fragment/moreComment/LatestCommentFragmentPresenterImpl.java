package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class LatestCommentFragmentPresenterImpl implements LatestCommentFragmentControl.LatestCommentFragmentPresenter {

    private LatestCommentFragmentControl.LatestCommentView mLatestCommentView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public LatestCommentFragmentPresenterImpl(Context context, BookModel model, LatestCommentFragmentControl.LatestCommentView view) {
        mContext = context;
        mBookModel = model;
        mLatestCommentView = view;
    }

    /**
     * 查询评论列表
     */
    @Override
    public void onRequestCommentInfo(CommentRequest commentRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestCommentInfo(commentRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCommentInfoSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 查询评论列表 成功
     */
    private void requestCommentInfoSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            CommentListBean response = new Gson().fromJson(responseData.mJsonObject.toString(), CommentListBean.class);
            mLatestCommentView.getCommentInfoSuccess(response);
//            responseData.parseData(CommentBean.class);
//            if (responseData.parsedData != null) {
//                CommentBean response = (CommentBean) responseData.parsedData;
//                mLatestCommentView.getCommentInfoSuccess(response);
//            }
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public void uploadImageRequest(UploadImage uploadPicRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.uploadImageRequest(uploadPicRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::uploadPicSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 上传图片 成功
     */
    private void uploadPicSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mLatestCommentView.getUploadPicSuccess(responseData.result);
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 发布评论
     */
    @Override
    public void onRequestPublishComment(PublishCommentRequest publishCommentRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestPublishComment(publishCommentRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPublishCommentSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 发布评论 成功
     */
    private void requestPublishCommentSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mLatestCommentView.getPublishCommentSuccess();
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 评论点赞
     */
    @Override
    public void onCommentSuggestRequest(SupportRequest commentSuggestRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(commentSuggestRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::commentSuggestSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 评论点赞 成功
     */
    private void commentSuggestSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mLatestCommentView.getSuggestSuccess();
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 评论用户评论
     */
    @Override
    public void onPublishCommentUser(PublishCommentUserRequest publishCommentUserRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onPublishCommentUser(publishCommentUserRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::publishCommentUserSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 评论用户评论 成功
     */
    private void publishCommentUserSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            mLatestCommentView.getSuggestSuccess();
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mLatestCommentView = null;
    }
}
