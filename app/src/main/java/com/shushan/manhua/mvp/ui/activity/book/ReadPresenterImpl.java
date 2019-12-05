package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.request.BuyBarrageStyleRequest;
import com.shushan.manhua.entity.request.ExchangeBarrageStyleRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SendBarrageRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.BuyBarrageStyleResponse;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class ReadPresenterImpl implements ReadControl.PresenterRead {

    private ReadControl.ReadView mReadView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public ReadPresenterImpl(Context context, BookModel model, ReadControl.ReadView ReadView) {
        mContext = context;
        mBookModel = model;
        mReadView = ReadView;
    }


    /**
     * 章节详情
     */
    @Override
    public void onRequestReadingInfo(ReadingRequest readingRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestReadingInfo(readingRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::readingRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 章节详情 成功
     */
    private void readingRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ReadingInfoResponse.class);
            if (responseData.parsedData != null) {
                ReadingInfoResponse response = (ReadingInfoResponse) responseData.parsedData;
                mReadView.getReadingInfoSuccess(response);
            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 点赞
     */
    @Override
    public void onSupportRequest(SupportRequest supportRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onSupportRequest(supportRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::supportRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 点赞 成功
     */
    private void supportRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadView.getSupportSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 加入书架
     */
    @Override
    public void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onAddBookShelfRequest(addBookShelfRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAddBookShelfSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 加入书架 成功
     */
    private void requestAddBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadView.getAddBookShelfSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 请求漫画选集信息
     */
    @Override
    public void onRequestSelectionInfo(SelectionRequest selectionRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestSelectionInfo(selectionRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSelectionInfoSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 请求漫画选集信息 成功
     */
    private void requestSelectionInfoSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(SelectionResponse.class);
            if (responseData.parsedData != null) {
                SelectionResponse response = (SelectionResponse) responseData.parsedData;
                mReadView.getSelectionInfoSuccess(response);
            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传阅读记录
     */
    @Override
    public void onRequestReadRecording(ReadRecordingRequest readRecordingRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestReadRecording(readRecordingRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadRecordingSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 上传阅读记录 成功
     */
    private void requestReadRecordingSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadView.getReadRecordingSuccess();
//            responseData.parseData(SelectionResponse.class);
//            if (responseData.parsedData != null) {
//                SelectionResponse response = (SelectionResponse) responseData.parsedData;
//                mReadView.getSelectionInfoSuccess(response);
//            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 发送弹幕
     */
    @Override
    public void sendBarrageRequest(SendBarrageRequest sendBarrageRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.sendBarrageRequest(sendBarrageRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSendBarrageSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 发送弹幕 成功
     */
    private void requestSendBarrageSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadView.getSendBarrageSuccess();
//            responseData.parseData(SelectionResponse.class);
//            if (responseData.parsedData != null) {
//                SelectionResponse response = (SelectionResponse) responseData.parsedData;
//                mReadView.getSelectionInfoSuccess(response);
//            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 兑换弹幕样式
     */
    @Override
    public void exchangeBarrageStyleRequest(ExchangeBarrageStyleRequest exchangeBarrageStyleRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.exchangeBarrageStyleRequest(exchangeBarrageStyleRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::exchangeBarrageStyleSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 兑换弹幕样式 成功
     */
    private void exchangeBarrageStyleSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadView.getSendBarrageSuccess();
//            responseData.parseData(SelectionResponse.class);
//            if (responseData.parsedData != null) {
//                SelectionResponse response = (SelectionResponse) responseData.parsedData;
//                mReadView.getSelectionInfoSuccess(response);
//            }
            mReadView.getExchangeBarrageStyleSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 获取弹幕列表
     */
    @Override
    public void getBarrageListRequest(BarrageListRequest barrageListRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.getBarrageListRequest(barrageListRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::barrageListRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 获取弹幕列表 成功
     */
    private void barrageListRequestSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            responseData.parseData(BarrageListResponse.class);
//            if (responseData.parsedData != null) {
//                BarrageListResponse response = (BarrageListResponse) responseData.parsedData;
//                mReadView.getBarrageListSuccess(response);
//            }
            BarrageListResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), BarrageListResponse.class);
            mReadView.getBarrageListSuccess(response);
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求购买的弹幕样式
     */
    @Override
    public void onRequestBuyBarrageStyle(BuyBarrageStyleRequest buyBarrageStyleRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestBuyBarrageStyle(buyBarrageStyleRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::buyBarrageStyleRequestSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 请求购买的弹幕样式 成功
     */
    private void buyBarrageStyleRequestSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BuyBarrageStyleResponse.class);
            if (responseData.parsedData != null) {
                BuyBarrageStyleResponse response = (BuyBarrageStyleResponse) responseData.parsedData;
                mReadView.getBuyBarrageStyleSuccess(response);
            }
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 上传图片
     */
    @Override
    public void uploadImageRequest(UploadImage uploadPicRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.uploadImageRequest(uploadPicRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::uploadPicSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 上传图片 成功
     */
    private void uploadPicSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadView.getUploadPicSuccess(responseData.result);
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 发布评论
     */
    @Override
    public void onRequestPublishComment(PublishCommentRequest publishCommentRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestPublishComment(publishCommentRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPublishCommentSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 发布评论 成功
     */
    private void requestPublishCommentSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadView.getPublishCommentSuccess();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 请求分享任务
     */
    @Override
    public void onRequestShareTask(ShareTaskRequest shareTaskRequest) {
        mReadView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestShareTask(shareTaskRequest).compose(mReadView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestShareTaskSuccess, throwable -> mReadView.showErrMessage(throwable),
                        () -> mReadView.dismissLoading());
        mReadView.addSubscription(disposable);
    }

    /**
     * 请求分享任务 成功
     */
    private void requestShareTaskSuccess(ResponseData responseData) {
        mReadView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            mReadView.getPublishCommentSuccess();
//            mReadView.showToast();
        } else {
            mReadView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
