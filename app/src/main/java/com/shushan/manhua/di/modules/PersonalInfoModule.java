package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.model.UserModel;
import com.shushan.manhua.mvp.ui.activity.user.PersonalInfoControl;
import com.shushan.manhua.mvp.ui.activity.user.PersonalInfoPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.UserApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class PersonalInfoModule {
    private final AppCompatActivity activity;
    private PersonalInfoControl.PersonalInfoView view;

    public PersonalInfoModule(AppCompatActivity activity, PersonalInfoControl.PersonalInfoView view) {
        this.activity = activity;
        this.view = view;
    }

    public PersonalInfoModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    PersonalInfoControl.PersonalInfoView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    PersonalInfoControl.PresenterPersonalInfo providePresenterPersonalInfo(PersonalInfoPresenterImpl personalInfoPresenter) {
        return personalInfoPresenter;
    }

    @Provides
    @PerActivity
    UserModel provideUserModel(Gson gson, ModelTransform modelTransform) {
        return new UserModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.MAN_HUA_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(UserApi.class), gson, modelTransform);
    }

}
