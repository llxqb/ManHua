package com.shushan.manhua.mvp.ui.activity.main;


import com.shushan.manhua.entity.request.LoginTouristModeRequest;
import com.shushan.manhua.entity.request.PaySwitchRequest;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.request.ScoreFinishRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.entity.response.LoginTouristModeResponse;
import com.shushan.manhua.entity.response.PaySwitchResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {
        void getManHuaTypeSuccess(BookTypeResponse bookTypeResponse);

        void getLoginTouristModeSuccess(LoginTouristModeResponse loginTouristModeResponse);

        void getPaySwitchSuccess(PaySwitchResponse paySwitchResponse);

    }

    public interface PresenterMain extends Presenter<MainView> {
        /**
         * 请求漫画类型
         */
        void onRequestManHuaType();

        /**
         * 游客模式注册登陆
         */
        void onLoginTouristModeRequest(LoginTouristModeRequest loginTouristModeRequest);

        /**
         * 设置阅读偏好
         */
        void onReadingSettingRequest(ReadingSettingRequest readingSettingRequest);

        /**
         * 查询开关，应对过审
         */
        void onRequestPaySwitch(PaySwitchRequest paySwitchRequest);

        /**
         * 评分完成
         */
        void onRequestScoreFinish(ScoreFinishRequest scoreFinishRequest);

    }

}
