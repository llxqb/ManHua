package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class BookDetailPresenterImpl implements BookDetailControl.PresenterBookDetail {

    private BookDetailControl.BookDetailView mBookDetailView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public BookDetailPresenterImpl(Context context, BookModel model, BookDetailControl.BookDetailView view) {
        mContext = context;
        mBookModel = model;
        mBookDetailView = view;
    }


    /**
     * 加入书架
     */
    @Override
    public void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest) {
        mBookDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onAddBookShelfRequest(addBookShelfRequest).compose(mBookDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAddBookShelfSuccess, throwable -> mBookDetailView.showErrMessage(throwable),
                        () -> mBookDetailView.dismissLoading());
        mBookDetailView.addSubscription(disposable);
    }

    /**
     * 加入书架 成功
     */
    private void requestAddBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mBookDetailView.getBookShelfSuccess();
        } else {
            mBookDetailView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
