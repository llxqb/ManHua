package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class HotCommentFragmentPresenterImpl implements HotCommentFragmentControl.HotCommentFragmentPresenter {

    private HotCommentFragmentControl.HotCommentView mHotCommentView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public HotCommentFragmentPresenterImpl(Context context, BookModel model, HotCommentFragmentControl.HotCommentView view) {
        mContext = context;
        mBookModel = model;
        mHotCommentView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mHotCommentView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestMineInfo(tokenRequest).compose(mHotCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mHotCommentView.showErrMessage(throwable),
//                        () -> mHotCommentView.dismissLoading());
//        mHotCommentView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mHotCommentView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mHotCommentView.getMineInfoSuccess(response);
//            }
//        } else {
//            mHotCommentView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mHotCommentView = null;
    }
}
