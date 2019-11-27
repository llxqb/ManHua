package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.DeleteBookShelfRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


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


    /**
     * 删除书架漫画
     */
    @Override
    public void onRequestDeleteBook(DeleteBookShelfRequest deleteBookShelfRequest) {
        mLongDeleteView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestDeleteBook(deleteBookShelfRequest).compose(mLongDeleteView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::deleteBookShelfSuccess, throwable -> mLongDeleteView.showErrMessage(throwable),
                        () -> mLongDeleteView.dismissLoading());
        mLongDeleteView.addSubscription(disposable);
    }

    /**
     * 删除书架漫画 成功
     */
    private void deleteBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mLongDeleteView.getDeleteBookShelfSuccess();
        } else {
            mLongDeleteView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 请求我的书架数据
     */
    @Override
    public void onRequestBookShelfInfo(BookShelfInfoRequest bookShelfInfoRequest) {
        mLongDeleteView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestBookShelfInfo(bookShelfInfoRequest).compose(mLongDeleteView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::onRequestBookShelfInfoSuccess, throwable -> mLongDeleteView.showErrMessage(throwable),
                        () -> mLongDeleteView.dismissLoading());
        mLongDeleteView.addSubscription(disposable);
    }

    /**
     * 请求我的书架数据成功
     */
    private void onRequestBookShelfInfoSuccess(ResponseData responseData) {
        mLongDeleteView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BookShelfResponse.class);
            if (responseData.parsedData != null) {
                BookShelfResponse response = (BookShelfResponse) responseData.parsedData;
                mLongDeleteView.getBookShelfInfoSuccess(response);
            }
        } else {
            mLongDeleteView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
