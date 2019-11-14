package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class SearchPresenterImpl implements SearchControl.PresenterSearch {

    private SearchControl.SearchView mSearchView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public SearchPresenterImpl(Context context, BookModel model, SearchControl.SearchView SearchView) {
        mContext = context;
        mBookModel = model;
        mSearchView = SearchView;
    }


//    /**
//     * 登录
//     */
//    @Override
//    public void onRequestLogin(LoginRequest loginRequest) {
//        mSearchView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestLogin(loginRequest).compose(mSearchView.applySchedulers()).retryWhen(new com.shushan.homework101.help.RetryWithDelay(3, 3000))
//                .subscribe(this::requestLoginSuccess, throwable -> mSearchView.showErrMessage(throwable),
//                        () -> mSearchView.dismissLoading());
//        mSearchView.addSubscription(disposable);
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
//                mSearchView.getLoginSuccess(response);
//            }
//        } else {
//            mSearchView.showToast(responseData.errorMsg);
//        }
//    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
