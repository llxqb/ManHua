package com.shushan.manhua.mvp.ui.activity.login;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
//        void getVerifyCodeSuccess(VerifyCodeResponse verifyCodeResponse);
//
//        void getLoginSuccess(LoginResponse loginResponse);
    }

    public interface PresenterLogin extends Presenter<LoginView> {

//        /**
//         * 获取验证码
//         */
//        void onRequestVerifyCode(VerifyCodeRequest verifyCodeRequest);
//
//        /**
//         * 登录
//         */
//        void onRequestLogin(LoginRequest loginRequest);
    }

}
