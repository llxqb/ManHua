package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class ReadControl {
    public interface ReadView extends LoadDataView {
        void getReadingInfoSuccess(ReadingInfoResponse readingInfoResponse);
    }

    public interface PresenterRead extends Presenter<ReadView> {
        /**
         * 章节详情
         */
        void onRequestReadingInfo(ReadingRequest readingRequest);
    }

}
