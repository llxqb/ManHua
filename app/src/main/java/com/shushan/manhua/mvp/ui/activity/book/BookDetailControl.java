package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class BookDetailControl {
    public interface BookDetailView extends LoadDataView {
//        void getVerifyCodeSuccess(VerifyCodeResponse verifyCodeResponse);
//
//        void getLoginSuccess(LoginResponse loginResponse);
    }

    public interface PresenterBookDetail extends Presenter<BookDetailView> {

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
