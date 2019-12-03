package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.request.ExchangeBarrageStyleRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SendBarrageRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class ReadControl {
    public interface ReadView extends LoadDataView {
        void getReadingInfoSuccess(ReadingInfoResponse readingInfoResponse);

        void getSupportSuccess();

        void getAddBookShelfSuccess();

        void getSelectionInfoSuccess(SelectionResponse selectionResponse);

        void getSendBarrageSuccess();

        void getExchangeBarrageStyleSuccess();

        void getBarrageListSuccess(BarrageListResponse barrageListResponse);
    }

    public interface PresenterRead extends Presenter<ReadView> {
        /**
         * 章节详情
         */
        void onRequestReadingInfo(ReadingRequest readingRequest);

        /**
         * 点赞
         */
        void onSupportRequest(SupportRequest supportRequest);

        /**
         * 加入书架
         */
        void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest);

        /**
         * 请求漫画选集信息
         */
        void onRequestSelectionInfo(SelectionRequest selectionRequest);

        /**
         * 上传阅读记录
         */
        void onRequestReadRecording(ReadRecordingRequest readRecordingRequest);

        /**
         * 发送弹幕
         */
        void sendBarrageRequest(SendBarrageRequest sendBarrageRequest);

        /**
         * 兑换弹幕样式
         */
        void exchangeBarrageStyleRequest(ExchangeBarrageStyleRequest exchangeBarrageStyleRequest);

        /**
         * 获取弹幕列表
         */
        void getBarrageListRequest(BarrageListRequest barrageListRequest);
    }

}
