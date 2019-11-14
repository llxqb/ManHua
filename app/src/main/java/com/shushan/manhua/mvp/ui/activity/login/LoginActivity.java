package com.shushan.manhua.mvp.ui.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLoginComponent;
import com.shushan.manhua.di.components.DaggerMainComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LoginModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.mvp.ui.activity.main.MainActivity;
import com.shushan.manhua.mvp.ui.activity.main.MainControl;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.Module;

public class LoginActivity extends BaseActivity implements LoginControl.LoginView {

    @Inject
    LoginControl.PresenterLogin mPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
