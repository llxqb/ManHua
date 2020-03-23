package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BookContentRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingBookRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.response.BookContentResponse;
import com.shushan.manhua.entity.response.ReadingBookResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.ireader.model.bean.ChapterInfoBean;
import com.shushan.manhua.ireader.model.local.BookRepository;
import com.shushan.manhua.ireader.widget.page.TxtChapter;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ResponseData;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterReadImpl
 */

public class ReadBookPresenterImpl implements ReadBookControl.PresenterReadBook {

    private ReadBookControl.ReadBookView mReadBookView;
    private final BookModel mBookModel;
    private final Context mContext;

    @Inject
    public ReadBookPresenterImpl(Context context, BookModel model, ReadBookControl.ReadBookView view) {
        mContext = context;
        mBookModel = model;
        mReadBookView = view;
    }


    /**
     * 小说阅读
     */
    @Override
    public void onRequestBookInfo(ReadingBookRequest readingBookRequest) {
        Disposable disposable = mBookModel.onRequestBookInfo(readingBookRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLoginSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 小说阅读 成功
     */
    private void requestLoginSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ReadingBookResponse.class);
            if (responseData.parsedData != null) {
                ReadingBookResponse response = (ReadingBookResponse) responseData.parsedData;
                mReadBookView.getReadingBookInfoSuccess(response);
            }
        } else {
            mReadBookView.getReadingBookInfoFail();
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传阅读记录
     */
    @Override
    public void onRequestReadRecording(ReadRecordingRequest readRecordingRequest) {
        Disposable disposable = mBookModel.onRequestReadRecording(readRecordingRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestReadRecordingSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 上传阅读记录 成功
     */
    private void requestReadRecordingSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mReadBookView.getReadRecordingSuccess();
//            responseData.parseData(SelectionResponse.class);
//            if (responseData.parsedData != null) {
//                SelectionResponse response = (SelectionResponse) responseData.parsedData;
//                mReadBookView.getSelectionInfoSuccess(response);
//            }
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求漫画选集信息
     */
    @Override
    public void onRequestSelectionInfo(SelectionRequest selectionRequest) {
        Disposable disposable = mBookModel.onRequestSelectionInfo(selectionRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestSelectionInfoSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 请求漫画选集信息 成功
     */
    private void requestSelectionInfoSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(SelectionResponse.class);
            if (responseData.parsedData != null) {
                SelectionResponse response = (SelectionResponse) responseData.parsedData;
                mReadBookView.getSelectionInfoSuccess(response);
            }
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 加入书架
     */
    @Override
    public void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest) {
        Disposable disposable = mBookModel.onAddBookShelfRequest(addBookShelfRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAddBookShelfSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 加入书架 成功
     */
    private void requestAddBookShelfSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mReadBookView.getAddBookShelfSuccess();
        } else {
//            mReadBookView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求分享任务
     */
    @Override
    public void onRequestShareTask(ShareTaskRequest shareTaskRequest) {
        mReadBookView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mBookModel.onRequestShareTask(shareTaskRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestShareTaskSuccess, throwable -> mReadBookView.showErrMessage(throwable),
                        () -> mReadBookView.dismissLoading());
        mReadBookView.addSubscription(disposable);
    }

    /**
     * 请求分享任务 成功
     */
    private void requestShareTaskSuccess(ResponseData responseData) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
//            mReadBookView.getPublishCommentSuccess();
//            mReadBookView.showToast();
        } else {
            mReadBookView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void loadChapter(String bookId, List<TxtChapter> bookChapters,String token) {
        int size = bookChapters.size();

        //取消上次的任务，防止多次加载
//        if (mChapterSub != null) {
//            mChapterSub.cancel();
//        }

        List<Single<ChapterInfoBean>> chapterInfos = new ArrayList<>(bookChapters.size());
        ArrayDeque<String> titles = new ArrayDeque<>(bookChapters.size());

        // 将要下载章节，转换成网络请求。
        for (int i = 0; i < size; ++i) {
            TxtChapter bookChapter = bookChapters.get(i);
            // 网络中获取数据 bookChapter.getLink()
            BookContentRequest bookContentRequest = new BookContentRequest();
            bookContentRequest.token = token;
            bookContentRequest.catalogue_id = bookChapter.getChapterId();
//            Single<ChapterInfoBean> chapterInfoSingle = RemoteRepository.getInstance()
//                    .getChapterInfo(bookContentRequest);
            onRequestChapterInfo(bookContentRequest, bookId, bookChapter.getTitle());
//            chapterInfos.add(chapterInfoSingle);
            titles.add(bookChapter.getTitle());
        }
    }

    /**
     * 根据章节id获取章节内容
     */
    private void onRequestChapterInfo(BookContentRequest bookContentRequest, String bookId, String title) {
        Log.e("ddd", "bookContentRequest:" + new Gson().toJson(bookContentRequest));
        mBookModel.onRequestChapterInfo(bookContentRequest).compose(mReadBookView.applySchedulers()).retryWhen(new RetryWithDelay(2, 3000))
                .subscribe(new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseData responseData) {
                        requestChapterInfoSuccess(responseData, bookId, title);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mReadBookView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    /**
     * 根据章节id获取章节内容 成功
     */
    private void requestChapterInfoSuccess(ResponseData responseData, String bookId, String title) {
        mReadBookView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            responseData.parseData(BookContentResponse.class);
            if (responseData.parsedData != null) {
                BookContentResponse response = (BookContentResponse) responseData.parsedData;
//                mReadBookView.getSelectionInfoSuccess(response);
                //将获取到的数据进行存储
                //存储数据
                BookRepository.getInstance().saveChapterInfo(
                        bookId, title, response.getContent()
                );
                mReadBookView.finishChapter();
            }
        } else {
            mReadBookView.showToast(responseData.errorMsg);
            //只有第一个加载失败才会调用errorChapter
            mReadBookView.errorChapter();
//            if (bookChapters.get(0).getTitle().equals(title)) {
//            }
        }

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
