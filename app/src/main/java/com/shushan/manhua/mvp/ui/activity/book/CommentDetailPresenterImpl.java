package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


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


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mCommentDetailView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mCommentDetailView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mCommentDetailView.showErrMessage(throwable),
//                        () -> mCommentDetailView.dismissLoading());
//        mCommentDetailView.addSubscription(disposable);
//    }
//
//    /**
//     * 登录成功
//     */
//    private void requestLoginSuccess(ResponseData responseData) {
//        if (responseData.resultCode == 0) {
//            responseData.parseData(LoginResponse.class);
//            if (responseData.parsedData != null) {
//                LoginResponse response = (LoginResponse) responseData.parsedData;
//                mCommentDetailView.getLoginSuccess(response);
//            }
//        } else {
//            mCommentDetailView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
