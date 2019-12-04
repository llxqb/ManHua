package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.DeleteReadingHistoryRequest;
import com.shushan.manhua.entity.request.ReadingHistoryRequest;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class ReadingHistoryControl {
    public interface ReadingHistoryView extends LoadDataView {
        void getReadingHistorySuccess(ReadingHistoryResponse readingHistoryResponse);

        void getDeleteReadingHistorySuccess();

    }

    public interface PresenterReadingHistory extends Presenter<ReadingHistoryView> {

        /**
         * 阅读历史
         */
        void onRequestReadingHistory(ReadingHistoryRequest readingHistoryRequest);
        /**
         * 删除阅读历史
         */
        void onDeleteReadingHistoryRequest(DeleteReadingHistoryRequest deleteReadingHistoryRequest);
    }

}
