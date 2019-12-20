package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingBookRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.response.ReadingBookResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class ReadBookControl {
    public interface ReadBookView extends LoadDataView {
        void getReadingBookInfoSuccess(ReadingBookResponse readingBookResponse);

        void getReadRecordingSuccess();

        void getSelectionInfoSuccess(SelectionResponse selectionResponse);

        void getAddBookShelfSuccess();
    }

    public interface PresenterReadBook extends Presenter<ReadBookView> {

        /**
         * 小说阅读
         */
        void onRequestBookInfo(ReadingBookRequest readingBookRequest);
        /**
         * 使用漫豆  购买阅读非免费章节
         * 上传阅读记录
         */
        void onRequestReadRecording(ReadRecordingRequest readRecordingRequest);

        /**
         * 请求书籍选集信息
         */
        void onRequestSelectionInfo(SelectionRequest selectionRequest);

        /**
         * 加入书架
         */
        void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest);

        /**
         * 请求分享任务
         */
        void onRequestShareTask(ShareTaskRequest shareTaskRequest);
    }

}
