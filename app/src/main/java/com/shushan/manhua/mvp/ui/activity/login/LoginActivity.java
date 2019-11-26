package com.shushan.manhua.mvp.ui.activity.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLoginComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LoginModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.GoogleLoginHelper;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginControl.LoginView {

    @Inject
    LoginControl.PresenterLogin mPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.login_google_tv, R.id.login_facebook_tv, R.id.protocol_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_google_tv:
                showLoading(getResources().getString(R.string.loading));
                GoogleLoginHelper.googleLogin(this);
                break;
            case R.id.login_facebook_tv:
                break;
            case R.id.protocol_tv:
                break;
        }
    }

    /**
     * 登录回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            //facebook回调  64206
//            if (faceBookLoginManager != null) {
//                faceBookLoginManager.mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
//            }
        }
    }

    //处理google回调
    private void handleSignInResult(GoogleSignInResult result) {
        dismissLoading();
        Log.e("ddd", "handleSignInResult----" + new Gson().toJson(result));
        LogUtils.e("success:"+result.isSuccess());
        if (result != null && result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            //登录后台系统
            assert account != null;
            appGoogleLogin(account.getId(), account.getIdToken());
        } else {
            showToast("google login fail");
        }
    }

    private void appGoogleLogin(String gId, String accessToken) {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.id = gId;
//        loginRequest.deviceId = SystemUtils.getUUID(this, mSharePreferenceUtil);
//        loginRequest.access_token = accessToken;
//        loginRequest.from = Constant.FROM;
//        mPresenterLogin.onRequestLogin(loginRequest);
    }



    private void initInjectData() {
        DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
