package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class ReadingSettingControl {
    public interface ReadingSettingView extends LoadDataView {
        void getManHuaTypeSuccess(BookTypeResponse bookTypeResponse);

        void getReadingSettingSuccess();
    }

    public interface PresenterReadingSetting extends Presenter<ReadingSettingView> {

        /**
         * 请求漫画类型
         */
        void onRequestManHuaType();

        /**
         * 设置阅读偏好
         */
        void onReadingSettingRequest(ReadingSettingRequest readingSettingRequest);
    }

}
