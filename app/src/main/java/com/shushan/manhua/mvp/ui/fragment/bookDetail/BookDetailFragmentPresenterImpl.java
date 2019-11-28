package com.shushan.manhua.mvp.ui.fragment.bookDetail;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class BookDetailFragmentPresenterImpl implements BookDetailFragmentControl.BookDetailFragmentPresenter {

    private BookDetailFragmentControl.BookDetailView mBookDetailView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public BookDetailFragmentPresenterImpl(Context context, BookModel model, BookDetailFragmentControl.BookDetailView view) {
        mContext = context;
        mBookModel = model;
        mBookDetailView = view;
    }

    /**
     * 查询书籍详情
     */
    @Override
    public void onRequestBookDetailInfo(BookDetailRequest bookDetailRequest) {
        mBookDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestBookDetailInfo(bookDetailRequest).compose(mBookDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestBookDetailInfoSuccess, throwable -> mBookDetailView.showErrMessage(throwable),
                        () -> mBookDetailView.dismissLoading());
        mBookDetailView.addSubscription(disposable);
    }

    /**
     * 查询书籍详情 成功
     */
    private void requestBookDetailInfoSuccess(ResponseData responseData) {
        mBookDetailView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BookDetailInfoResponse.class);
            if (responseData.parsedData != null) {
                BookDetailInfoResponse response = (BookDetailInfoResponse) responseData.parsedData;
                mBookDetailView.getBookDetailInfoSuccess(response);
            }
        } else {
            mBookDetailView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 评论点赞
     */
    @Override
    public void onCommentSuggestRequest(SupportRequest commentSuggestRequest) {
        mBookDetailView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(commentSuggestRequest).compose(mBookDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::commentSuggestSuccess, throwable -> mBookDetailView.showErrMessage(throwable),
                        () -> mBookDetailView.dismissLoading());
        mBookDetailView.addSubscription(disposable);
    }

    /**
     * 评论点赞 成功
     */
    private void commentSuggestSuccess(ResponseData responseData) {
        mBookDetailView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mBookDetailView.getSuggestSuccess();
//            responseData.parseData(BookDetailInfoResponse.class);
//            if (responseData.parsedData != null) {
//                BookDetailInfoResponse response = (BookDetailInfoResponse) responseData.parsedData;
//                mBookDetailView.getBookDetailInfoSuccess(response);
//            }
        } else {
            mBookDetailView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mBookDetailView = null;
    }


}
