package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class MoreCommentPresenterImpl implements MoreCommentControl.PresenterMoreComment {

    private MoreCommentControl.MoreCommentView mMoreCommentView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public MoreCommentPresenterImpl(Context context, BookModel model, MoreCommentControl.MoreCommentView view) {
        mContext = context;
        mBookModel = model;
        mMoreCommentView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mMoreCommentView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mMoreCommentView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mMoreCommentView.showErrMessage(throwable),
//                        () -> mMoreCommentView.dismissLoading());
//        mMoreCommentView.addSubscription(disposable);
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
//                mMoreCommentView.getLoginSuccess(response);
//            }
//        } else {
//            mMoreCommentView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
