package com.shushan.manhua.mvp.ui.activity.login;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLoginComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LoginModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.FacebookLoginRequest;
import com.shushan.manhua.entity.request.LoginRequest;
import com.shushan.manhua.entity.response.LoginResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.FacebookLoginHelper;
import com.shushan.manhua.help.GoogleLoginHelper;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.utils.SystemUtils;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginControl.LoginView {

    @Inject
    LoginControl.PresenterLogin mPresenterLogin;
    private User mUser;
    private FacebookLoginHelper faceBookLoginManager;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initView() {
        initFaceBookLogin();
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
                //facebook登录
                faceBookLoginManager.faceBookLogin(this);
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
            if (faceBookLoginManager != null) {
                faceBookLoginManager.mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    //处理google回调
    private void handleSignInResult(GoogleSignInResult result) {
        dismissLoading();
//        Log.e("ddd", "handleSignInResult----" + new Gson().toJson(result));
//        LogUtils.e("success:" + result.isSuccess());
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
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.id = gId;
        loginRequest.deviceId = SystemUtils.getUUID(this, mSharePreferenceUtil);
        loginRequest.access_token = accessToken;
        loginRequest.channel = mBuProcessor.getChannel();
        loginRequest.book_type = mBuProcessor.getbookType();
        mPresenterLogin.onRequestLogin(loginRequest);
    }

    @Override
    public void getLoginSuccess(LoginResponse loginResponse) {
        GoogleLoginHelper.exitGoogleLogin();//执行退出登录  符合当前登录逻辑
        loginSuccess(loginResponse.getUserinfo());
    }

    private void initFaceBookLogin() {
        faceBookLoginManager = new FacebookLoginHelper(result -> {
            //登录成功
            FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest();
            if (result != null) {
//                showToast("facebook-token:" + result.getAccessToken().getToken());
                facebookLoginRequest.access_token = result.getAccessToken().getToken();
            }
            facebookLoginRequest.from = Constant.FROM;
            facebookLoginRequest.deviceId = SystemUtils.getUUID(LoginActivity.this, mSharePreferenceUtil);
            facebookLoginRequest.channel = mBuProcessor.getChannel();
            facebookLoginRequest.book_type = mBuProcessor.getbookType();
            mPresenterLogin.onRequestLoginFacebook(facebookLoginRequest);

        });
        faceBookLoginManager.initFaceBook(getApplicationContext());
    }

    @Override
    public void facebookLoginSuccess(LoginResponse loginResponse) {
        if (faceBookLoginManager != null) {
            faceBookLoginManager.faceBookLoginOut();
        }
        loginSuccess(loginResponse.getUserinfo());
    }

    private void loginSuccess(LoginResponse.UserinfoBean userinfoBean) {
        mSharePreferenceUtil.setData(Constant.LOGIN_MODEL, 2);
        if (userinfoBean != null) {
            User user = new User();
            user.token = userinfoBean.getToken();
            user.vip = userinfoBean.getVip();
            mBuProcessor.setLoginUser(user);
            //刷新main数据 刷新
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA));
//            startActivitys(MainActivity.class);
            finish();
        }
    }

//    /**
//     * 检查app 权限
//     */
//    @SuppressLint("CheckResult")
//    private void checkPermissions(String token) {
//        RxPermissions mRxPermissions = new RxPermissions(this);
//        mRxPermissions.request(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        ).subscribe(permission -> {
//            if (permission) {
//                reqPersonalInfo(token);
//            } else {
//                showToast(getResources().getString(R.string.login_open_permission));
//            }
//        });
//    }


    private void initInjectData() {
        DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
