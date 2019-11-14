package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class ReadingHistoryPresenterImpl implements ReadingHistoryControl.PresenterReadingHistory {

    private ReadingHistoryControl.ReadingHistoryView mReadingHistoryView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public ReadingHistoryPresenterImpl(Context context, BookModel model, ReadingHistoryControl.ReadingHistoryView ReadingHistoryView) {
        mContext = context;
        mBookModel = model;
        mReadingHistoryView = ReadingHistoryView;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mReadingHistoryView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mReadingHistoryView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mReadingHistoryView.showErrMessage(throwable),
//                        () -> mReadingHistoryView.dismissLoading());
//        mReadingHistoryView.addSubscription(disposable);
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
//                mReadingHistoryView.getLoginSuccess(response);
//            }
//        } else {
//            mReadingHistoryView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
