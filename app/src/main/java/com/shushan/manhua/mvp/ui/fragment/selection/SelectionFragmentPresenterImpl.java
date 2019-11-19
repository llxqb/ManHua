package com.shushan.manhua.mvp.ui.fragment.selection;

import android.content.Context;

import com.shushan.manhua.mvp.model.BookModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class SelectionFragmentPresenterImpl implements SelectionFragmentControl.SelectionFragmentPresenter {

    private SelectionFragmentControl.SelectionView mSelectionView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public SelectionFragmentPresenterImpl(Context context, BookModel model, SelectionFragmentControl.SelectionView view) {
        mContext = context;
        mBookModel = model;
        mSelectionView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mSelectionView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mBookModel.onRequestMineInfo(tokenRequest).compose(mSelectionView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mSelectionView.showErrMessage(throwable),
//                        () -> mSelectionView.dismissLoading());
//        mSelectionView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mSelectionView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mSelectionView.getMineInfoSuccess(response);
//            }
//        } else {
//            mSelectionView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mSelectionView = null;
    }
}
