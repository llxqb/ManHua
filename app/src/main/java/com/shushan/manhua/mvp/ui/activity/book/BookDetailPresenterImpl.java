package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class BookDetailPresenterImpl implements BookDetailControl.PresenterBookDetail {

    private BookDetailControl.BookDetailView mBookDetailView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public BookDetailPresenterImpl(Context context, BookModel model, BookDetailControl.BookDetailView view) {
        mContext = context;
        mBookModel = model;
        mBookDetailView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mBookDetailView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mBookDetailView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mBookDetailView.showErrMessage(throwable),
//                        () -> mBookDetailView.dismissLoading());
//        mBookDetailView.addSubscription(disposable);
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
//                mBookDetailView.getLoginSuccess(response);
//            }
//        } else {
//            mBookDetailView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
