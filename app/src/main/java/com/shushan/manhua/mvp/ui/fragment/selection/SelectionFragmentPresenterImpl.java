package com.shushan.manhua.mvp.ui.fragment.selection;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


    /**
     * 请求漫画选集信息
     */
    @Override
    public void onRequestSelectionInfo(SelectionRequest selectionRequest) {
        mSelectionView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestSelectionInfo(selectionRequest).compose(mSelectionView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSelectionInfoSuccess, throwable -> mSelectionView.showErrMessage(throwable),
                        () -> mSelectionView.dismissLoading());
        mSelectionView.addSubscription(disposable);
    }

    /**
     * 请求漫画选集信息 成功
     */
    private void requestSelectionInfoSuccess(ResponseData responseData) {
        mSelectionView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(SelectionResponse.class);
            if (responseData.parsedData != null) {
                SelectionResponse response = (SelectionResponse) responseData.parsedData;
                mSelectionView.getSelectionInfoSuccess(response);
            }
        } else {
            mSelectionView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 评论点赞
     */
    @Override
    public void onCommentSuggestRequest(SupportRequest commentSuggestRequest) {
        mSelectionView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(commentSuggestRequest).compose(mSelectionView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::commentSuggestSuccess, throwable -> mSelectionView.showErrMessage(throwable),
                        () -> mSelectionView.dismissLoading());
        mSelectionView.addSubscription(disposable);
    }

    /**
     * 评论点赞 成功
     */
    private void commentSuggestSuccess(ResponseData responseData) {
        mSelectionView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mSelectionView.getSuggestSuccess();
        } else {
            mSelectionView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mSelectionView = null;
    }
}
