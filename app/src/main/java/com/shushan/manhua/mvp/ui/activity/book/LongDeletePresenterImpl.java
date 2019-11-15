package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class LongDeletePresenterImpl implements LongDeleteControl.PresenterLongDelete {

    private LongDeleteControl.LongDeleteView mLongDeleteView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public LongDeletePresenterImpl(Context context, BookModel model, LongDeleteControl.LongDeleteView view) {
        mContext = context;
        mBookModel = model;
        mLongDeleteView = view;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mLongDeleteView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mLongDeleteView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mLongDeleteView.showErrMessage(throwable),
//                        () -> mLongDeleteView.dismissLoading());
//        mLongDeleteView.addSubscription(disposable);
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
//                mLongDeleteView.getLoginSuccess(response);
//            }
//        } else {
//            mLongDeleteView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
