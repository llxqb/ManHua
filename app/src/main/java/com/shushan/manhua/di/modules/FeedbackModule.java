package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.mine.FeedbackControl;
import com.shushan.manhua.mvp.ui.activity.mine.FeedbackPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.MineApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class FeedbackModule {
    private final AppCompatActivity activity;
    private FeedbackControl.FeedbackView view;

    public FeedbackModule(AppCompatActivity activity, FeedbackControl.FeedbackView view) {
        this.activity = activity;
        this.view = view;
    }

    public FeedbackModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    FeedbackControl.FeedbackView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    FeedbackControl.PresenterFeedback providePresenterFeedback(FeedbackPresenterImpl feedbackPresenter) {
        return feedbackPresenter;
    }

    @Provides
    @PerActivity
    MineModel provideMineModel(Gson gson, ModelTransform modelTransform) {
        return new MineModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.MAN_HUA_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MineApi.class), gson, modelTransform);
    }


}
