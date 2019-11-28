package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
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

    /**
     * 点赞
     */
    @Override
    public void onSupportRequest(SupportRequest supportRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(supportRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::supportRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 点赞 成功
     */
    private void supportRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadView.getSupportSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 加入书架
     */
    @Override
    public void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onAddBookShelfRequest(addBookShelfRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAddBookShelfSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 加入书架 成功
     */
    private void requestAddBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadView.getAddBookShelfSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 请求漫画选集信息
     */
    @Override
    public void onRequestSelectionInfo(SelectionRequest selectionRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestSelectionInfo(selectionRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSelectionInfoSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 请求漫画选集信息 成功
     */
    private void requestSelectionInfoSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(SelectionResponse.class);
            if (responseData.parsedData != null) {
                SelectionResponse response = (SelectionResponse) responseData.parsedData;
                mReadView.getSelectionInfoSuccess(response);
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
