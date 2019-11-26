package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MainModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class BookShelfFragmentPresenterImpl implements BookShelfFragmentControl.BookShelfFragmentPresenter {

    private BookShelfFragmentControl.BookShelfView mBookShelfView;
    private final MainModel mMainModel;
    private final Context mContext;

    @Inject
    public BookShelfFragmentPresenterImpl(Context context, MainModel model, BookShelfFragmentControl.BookShelfView bookShelfView) {
        mContext = context;
        mMainModel = model;
        mBookShelfView = bookShelfView;
    }

    /**
     * 请求书架数据
     */
    @Override
    public void onRequestBookShelfInfo(BookShelfInfoRequest bookShelfInfoRequest) {
        mBookShelfView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestBookShelfInfo(bookShelfInfoRequest).compose(mBookShelfView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::onRequestBookShelfInfoSuccess, throwable -> mBookShelfView.showErrMessage(throwable),
                        () -> mBookShelfView.dismissLoading());
        mBookShelfView.addSubscription(disposable);
    }

    /**
     * 请求首页信息成功
     */
    private void onRequestBookShelfInfoSuccess(ResponseData responseData) {
        mBookShelfView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BookShelfResponse.class);
            if (responseData.parsedData != null) {
                BookShelfResponse response = (BookShelfResponse) responseData.parsedData;
                mBookShelfView.getBookShelfInfoSuccess(response);
            }
        } else {
            mBookShelfView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mBookShelfView = null;
    }
}
