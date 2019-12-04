package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.DeleteReadingHistoryRequest;
import com.shushan.manhua.entity.request.ReadingHistoryRequest;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


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


    /**
     * 阅读历史
     */
    @Override
    public void onRequestReadingHistory(ReadingHistoryRequest readingHistoryRequest) {
        mReadingHistoryView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestReadingHistory(readingHistoryRequest).compose(mReadingHistoryView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadingHistorySuccess, throwable -> mReadingHistoryView.showErrMessage(throwable),
                        () -> mReadingHistoryView.dismissLoading());
        mReadingHistoryView.addSubscription(disposable);
    }

    /**
     * 阅读历史 成功
     */
    private void requestReadingHistorySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            ReadingHistoryResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), ReadingHistoryResponse.class);
            mReadingHistoryView.getReadingHistorySuccess(response);
//            responseData.parseData(ReadingHistoryResponse.class);
//            if (responseData.parsedData != null) {
//                ReadingHistoryResponse response = (ReadingHistoryResponse) responseData.parsedData;
//                mReadingHistoryView.getReadingHistorySuccess(response);
//            }
        } else {
            mReadingHistoryView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 删除阅读历史
     */
    @Override
    public void onDeleteReadingHistoryRequest(DeleteReadingHistoryRequest deleteReadingHistoryRequest) {
        mReadingHistoryView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onDeleteReadingHistoryRequest(deleteReadingHistoryRequest).compose(mReadingHistoryView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDeleteReadingHistorySuccess, throwable -> mReadingHistoryView.showErrMessage(throwable),
                        () -> mReadingHistoryView.dismissLoading());
        mReadingHistoryView.addSubscription(disposable);
    }

    /**
     * 删除阅读历史 成功
     */
    private void requestDeleteReadingHistorySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadingHistoryView.getDeleteReadingHistorySuccess();
//            responseData.parseData(ReadingHistoryResponse.class);
//            if (responseData.parsedData != null) {
//                ReadingHistoryResponse response = (ReadingHistoryResponse) responseData.parsedData;
//                mReadingHistoryView.getReadingHistorySuccess(response);
//            }
        } else {
            mReadingHistoryView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
