package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.RankingRequest;
import com.shushan.manhua.entity.response.RankingResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class RankingPresenterImpl implements RankingControl.PresenterRanking {

    private RankingControl.RankingView mRankingView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public RankingPresenterImpl(Context context, BookModel model, RankingControl.RankingView view) {
        mContext = context;
        mBookModel = model;
        mRankingView = view;
    }

    /**
     * 排行榜数据
     */
    @Override
    public void onRequestRanking(RankingRequest rankingRequest) {
        mRankingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestRanking(rankingRequest).compose(mRankingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestRankingSuccess, throwable -> mRankingView.showErrMessage(throwable),
                        () -> mRankingView.dismissLoading());
        mRankingView.addSubscription(disposable);
    }

    /**
     * 排行榜数据 成功
     */
    private void requestRankingSuccess(ResponseData responseData) {
        mRankingView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(RankingResponse.class);
            if (responseData.parsedData != null) {
                RankingResponse response = (RankingResponse) responseData.parsedData;
                mRankingView.getRankingSuccess(response);
            }
        } else {
            mRankingView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
