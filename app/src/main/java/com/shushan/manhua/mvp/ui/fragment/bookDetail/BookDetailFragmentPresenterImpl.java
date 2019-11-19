package com.shushan.manhua.mvp.ui.fragment.bookDetail;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;

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


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mBookDetailView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestMineInfo(tokenRequest).compose(mBookDetailView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mBookDetailView.showErrMessage(throwable),
//                        () -> mBookDetailView.dismissLoading());
//        mBookDetailView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mBookDetailView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mBookDetailView.getMineInfoSuccess(response);
//            }
//        } else {
//            mBookDetailView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mBookDetailView = null;
    }
}
