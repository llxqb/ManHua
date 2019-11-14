package com.shushan.manhua.mvp.ui.fragment.message;

import android.content.Context;

import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class NoticeMessageFragmentPresenterImpl implements NoticeMessageFragmentControl.NoticeMessageFragmentPresenter {

    private NoticeMessageFragmentControl.NoticeMessageView mNoticeMessageView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public NoticeMessageFragmentPresenterImpl(Context context, UserModel model, NoticeMessageFragmentControl.NoticeMessageView view) {
        mContext = context;
        mUserModel = model;
        mNoticeMessageView = view;
    }


//    /**
//     * 查询我的（包含购买的服务信息）
//     */
//    @Override
//    public void onRequestMineInfo(TokenRequest tokenRequest) {
//        mNoticeMessageView.showLoading(mContext.getResources().getString(R.string.loading));
//        Disposable disposable = mUserModel.onRequestMineInfo(tokenRequest).compose(mNoticeMessageView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestMineInfoSuccess, throwable -> mNoticeMessageView.showErrMessage(throwable),
//                        () -> mNoticeMessageView.dismissLoading());
//        mNoticeMessageView.addSubscription(disposable);
//    }
//
//    /**
//     * 查询我的（包含购买的服务信息）成功
//     */
//    private void requestMineInfoSuccess(ResponseData responseData) {
//        mNoticeMessageView.judgeToken(responseData.resultCode);
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MineInfoResponse.class);
//            if (responseData.parsedData != null) {
//                MineInfoResponse response = (MineInfoResponse) responseData.parsedData;
//                mNoticeMessageView.getMineInfoSuccess(response);
//            }
//        } else {
//            mNoticeMessageView.showToast(responseData.errorMsg);
//        }
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mNoticeMessageView = null;
    }
}
