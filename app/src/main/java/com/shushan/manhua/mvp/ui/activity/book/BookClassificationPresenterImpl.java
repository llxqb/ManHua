package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BookClassificationRequest;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.response.BookClassificationResponse;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class BookClassificationPresenterImpl implements BookClassificationControl.PresenterBookClassification {

    private BookClassificationControl.BookClassificationView mBookClassificationView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public BookClassificationPresenterImpl(Context context, BookModel model, BookClassificationControl.BookClassificationView view) {
        mContext = context;
        mBookModel = model;
        mBookClassificationView = view;
    }

    /**
     *  分类  漫画/小说列表
     */
    @Override
    public void onRequestClassification(BookClassificationRequest bookClassificationRequest) {
        mBookClassificationView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestClassification(bookClassificationRequest).compose(mBookClassificationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestClassificationSuccess, throwable -> mBookClassificationView.showErrMessage(throwable),
                        () -> mBookClassificationView.dismissLoading());
        mBookClassificationView.addSubscription(disposable);
    }

    /**
     *  分类  漫画/小说列表  成功
     */
    private void requestClassificationSuccess(ResponseData responseData) {
        mBookClassificationView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BookClassificationResponse.class);
            if (responseData.parsedData != null) {
                BookClassificationResponse response = (BookClassificationResponse) responseData.parsedData;
                mBookClassificationView.getClassificationSuccess(response);
            }
        } else {
            mBookClassificationView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


  
}
