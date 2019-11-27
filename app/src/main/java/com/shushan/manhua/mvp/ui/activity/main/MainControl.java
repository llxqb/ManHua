package com.shushan.manhua.mvp.ui.activity.main;


import com.shushan.manhua.entity.request.LoginTouristModeRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.entity.response.LoginTouristModeResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {
        void getManHuaTypeSuccess(BookTypeResponse bookTypeResponse);
        void getLoginTouristModeSuccess(LoginTouristModeResponse loginTouristModeResponse);
    }

    public interface PresenterMain extends Presenter<MainView> {
//        /**
//         * 检查版本更新
//         */
//        void onRequestVersionUpdate(VersionUpdateRequest versionUpdateRequest);
//

        /**
         * 请求漫画类型
         */
        void onRequestManHuaType();

        /**
         * 游客模式注册登陆
         */
        void onLoginTouristModeRequest(LoginTouristModeRequest loginTouristModeRequest);
    }

}
