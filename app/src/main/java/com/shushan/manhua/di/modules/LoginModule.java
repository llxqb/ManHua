package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.LoginModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.login.LoginControl;
import com.shushan.manhua.mvp.ui.activity.login.LoginPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.LoginApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class LoginModule {
    private final AppCompatActivity activity;
    private LoginControl.LoginView view;

    public LoginModule(AppCompatActivity activity, LoginControl.LoginView view) {
        this.activity = activity;
        this.view = view;
    }

    public LoginModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    LoginControl.LoginView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoginControl.PresenterLogin providePresenterLogin(LoginPresenterImpl loginPresenter) {
        return loginPresenter;
    }

    @Provides
    @PerActivity
    LoginModel provideLoginModel(Gson gson, ModelTransform modelTransform) {
        return new LoginModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.WORK_STU_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(LoginApi.class), gson, modelTransform);
    }




}
