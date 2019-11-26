package com.shushan.manhua.mvp.ui.fragment.moreComment;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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

    /**
     * 查询评论列表
     */
    @Override
    public void onRequestCommentInfo(CommentRequest commentRequest) {
        mLatestCommentView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestCommentInfo(commentRequest).compose(mLatestCommentView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestMineInfoSuccess, throwable -> mLatestCommentView.showErrMessage(throwable),
                        () -> mLatestCommentView.dismissLoading());
        mLatestCommentView.addSubscription(disposable);
    }

    /**
     * 查询评论列表 成功
     */
    private void requestMineInfoSuccess(ResponseData responseData) {
        mLatestCommentView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            CommentListBean response = new Gson().fromJson(responseData.mJsonObject.toString(), CommentListBean.class);
            mLatestCommentView.getCommentInfoSuccess(response);
//            responseData.parseData(CommentBean.class);
//            if (responseData.parsedData != null) {
//                CommentBean response = (CommentBean) responseData.parsedData;
//                mLatestCommentView.getCommentInfoSuccess(response);
//            }
        } else {
            mLatestCommentView.showToast(responseData.errorMsg);
        }
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mLatestCommentView = null;
    }
}
