package com.shushan.manhua.mvp.ui.activity.login;

import android.view.View;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLoginComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LoginModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
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
                break;
            case R.id.login_facebook_tv:
                break;
            case R.id.protocol_tv:
                break;
        }
    }


    private void initInjectData() {
        DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
