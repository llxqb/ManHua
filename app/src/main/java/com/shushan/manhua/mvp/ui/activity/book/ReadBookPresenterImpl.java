package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingBookRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.response.ReadingBookResponse;
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

public class ReadBookPresenterImpl implements ReadBookControl.PresenterReadBook {

    private ReadBookControl.ReadBookView mReadBookView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public ReadBookPresenterImpl(Context context, BookModel model, ReadBookControl.ReadBookView view) {
        mContext = context;
        mBookModel = model;
        mReadBookView = view;
    }


    /**
     * 小说阅读
     */
    @Override
    public void onRequestBookInfo(ReadingBookRequest readingBookRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestBookInfo(readingBookRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLoginSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 小说阅读 成功
     */
    private void requestLoginSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ReadingBookResponse.class);
            if (responseData.parsedData != null) {
                ReadingBookResponse response = (ReadingBookResponse) responseData.parsedData;
                mReadBookView.getReadingBookInfoSuccess(response);
            }
        } else {
            mReadBookView.getReadingBookInfoFail();
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传阅读记录
     */
    @Override
    public void onRequestReadRecording(ReadRecordingRequest readRecordingRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestReadRecording(readRecordingRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadRecordingSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 上传阅读记录 成功
     */
    private void requestReadRecordingSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadBookView.getReadRecordingSuccess();
//            responseData.parseData(SelectionResponse.class);
//            if (responseData.parsedData != null) {
//                SelectionResponse response = (SelectionResponse) responseData.parsedData;
//                mReadBookView.getSelectionInfoSuccess(response);
//            }
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }
    
    /**
     * 请求漫画选集信息
     */
    @Override
    public void onRequestSelectionInfo(SelectionRequest selectionRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestSelectionInfo(selectionRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSelectionInfoSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 请求漫画选集信息 成功
     */
    private void requestSelectionInfoSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(SelectionResponse.class);
            if (responseData.parsedData != null) {
                SelectionResponse response = (SelectionResponse) responseData.parsedData;
                mReadBookView.getSelectionInfoSuccess(response);
            }
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 加入书架
     */
    @Override
    public void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onAddBookShelfRequest(addBookShelfRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAddBookShelfSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 加入书架 成功
     */
    private void requestAddBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadBookView.getAddBookShelfSuccess();
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求分享任务
     */
    @Override
    public void onRequestShareTask(ShareTaskRequest shareTaskRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestShareTask(shareTaskRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestShareTaskSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 请求分享任务 成功
     */
    private void requestShareTaskSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            mReadBookView.getPublishCommentSuccess();
//            mReadBookView.showToast();
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
