package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class LatestCommentFragmentPresenterImpl implements LatestCommentFragmentControl.LatestCommentFragmentPresenter {

    private LatestCommentFragmentControl.LatestCommentView mLatestCommentView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public LatestCommentFragmentPresenterImpl(Context context, BookModel model, LatestCommentFragmentControl.LatestCommentView view) {
        mContext = context;
        mBookModel = model;
        mLatestCommentView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestMineInfo(tokenRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
//                        () -> mLatestCommentView.dismissLoading());
//        mLatestCommentView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mLatestCommentView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mLatestCommentView.getMineInfoSuccess(response);
//            }
//        } else {
//            mLatestCommentView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mLatestCommentView = null;
    }
}
