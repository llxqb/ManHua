package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
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

public class HotCommentFragmentPresenterImpl implements HotCommentFragmentControl.HotCommentFragmentPresenter {

    private HotCommentFragmentControl.HotCommentView mHotCommentView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public HotCommentFragmentPresenterImpl(Context context, BookModel model, HotCommentFragmentControl.HotCommentView view) {
        mContext = context;
        mBookModel = model;
        mHotCommentView = view;
    }


    /**
     * 查询评论列表
     */
    @Override
    public void onRequestCommentInfo(CommentRequest commentRequest) {
        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestCommentInfo(commentRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCommentInfoSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
                        () -> mHotCommentView.dismissLoading());
        mHotCommentView.addSubscription(disposable);
    }

    /**
     * 查询评论列表 成功
     */
    private void requestCommentInfoSuccess(ResponseData responseData) {
        mHotCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            CommentListBean response = new Gson().fromJson(responseData.mJsonObject.toString(), CommentListBean.class);
            mHotCommentView.getCommentInfoSuccess(response);

//            responseData.parseData(CommentBean.class);
//            if (responseData.parsedData != null) {
//                CommentBean response = (CommentBean) responseData.parsedData;
//                mHotCommentView.getCommentInfoSuccess(response);
//            }
        } else {
            mHotCommentView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public void uploadImageRequest(UploadImage uploadPicRequest) {
        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.uploadImageRequest(uploadPicRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::uploadPicSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
                        () -> mHotCommentView.dismissLoading());
        mHotCommentView.addSubscription(disposable);
    }

    /**
     * 上传图片 成功
     */
    private void uploadPicSuccess(ResponseData responseData) {
        mHotCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mHotCommentView.getUploadPicSuccess(responseData.result);
        } else {
            mHotCommentView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 发布评论
     */
    @Override
    public void onRequestPublishComment(PublishCommentRequest publishCommentRequest) {
        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestPublishComment(publishCommentRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPublishCommentSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
                        () -> mHotCommentView.dismissLoading());
        mHotCommentView.addSubscription(disposable);
    }

    /**
     * 发布评论 成功
     */
    private void requestPublishCommentSuccess(ResponseData responseData) {
        mHotCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mHotCommentView.getPublishCommentSuccess();
        } else {
            mHotCommentView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 评论点赞
     */
    @Override
    public void onSupportRequest(SupportRequest commentSuggestRequest) {
        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(commentSuggestRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::commentSuggestSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
                        () -> mHotCommentView.dismissLoading());
        mHotCommentView.addSubscription(disposable);
    }

    /**
     * 评论点赞 成功
     */
    private void commentSuggestSuccess(ResponseData responseData) {
        mHotCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mHotCommentView.getSupportSuccess();
        } else {
            mHotCommentView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 评论用户评论
     */
    @Override
    public void onPublishCommentUser(PublishCommentUserRequest publishCommentUserRequest) {
        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onPublishCommentUser(publishCommentUserRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::publishCommentUserSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
                        () -> mHotCommentView.dismissLoading());
        mHotCommentView.addSubscription(disposable);
    }

    /**
     * 评论用户评论 成功
     */
    private void publishCommentUserSuccess(ResponseData responseData) {
        mHotCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            mHotCommentView.getSuggestSuccess();
        } else {
            mHotCommentView.showToast(responseData.errorMsg);
        }
    }
    
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mHotCommentView = null;
    }
}
