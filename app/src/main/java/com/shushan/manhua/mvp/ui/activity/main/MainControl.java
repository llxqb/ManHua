package com.shushan.manhua.mvp.ui.activity.main;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {
//        void getVersionUpdateSuccess(VersionUpdateResponse versionUpdateResponse);
    }

    public interface PresenterMain extends Presenter<MainView> {
//        /**
//         * 检查版本更新
//         */
//        void onRequestVersionUpdate(VersionUpdateRequest versionUpdateRequest);
//
//        /**
//         * 上传设备信息
//         */
//        void uploadDeviceInfo(DeviceInfoRequest deviceInfoRequest);
//
//        /**
//         * 根据融云第三方id获取用户头像和昵称
//         */
//        UserInfo onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest);
    }

}
