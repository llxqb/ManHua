package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


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


    /**
     * 章节详情
     */
    @Override
    public void onRequestReadingInfo(ReadingRequest readingRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestReadingInfo(readingRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::readingRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 章节详情 成功
     */
    private void readingRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ReadingInfoResponse.class);
            if (responseData.parsedData != null) {
                ReadingInfoResponse response = (ReadingInfoResponse) responseData.parsedData;
                mReadView.getReadingInfoSuccess(response);
            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
