package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class ReadPresenterImpl implements ReadControl.PresenterRead {

    private ReadControl.ReadView mReadView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public ReadPresenterImpl(Context context, BookModel model, ReadControl.ReadView ReadView) {
        mContext = context;
        mBookModel = model;
        mReadView = ReadView;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mReadView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mReadView.showErrMessage(throwable),
//                        () -> mReadView.dismissLoading());
//        mReadView.addSubscription(disposable);
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
//                mReadView.getLoginSuccess(response);
//            }
//        } else {
//            mReadView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
