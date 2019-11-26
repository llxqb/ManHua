package com.shushan.manhua.mvp.ui.activity.login;


import com.shushan.manhua.entity.request.LoginRequest;
import com.shushan.manhua.entity.response.LoginResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
        void getLoginSuccess(LoginResponse loginResponse);
    }

    public interface PresenterLogin extends Presenter<LoginView> {

        /**
         * 登录
         */
        void onRequestLogin(LoginRequest loginRequest);
    }

}
