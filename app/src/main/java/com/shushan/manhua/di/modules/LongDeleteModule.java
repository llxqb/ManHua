package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.book.LongDeleteControl;
import com.shushan.manhua.mvp.ui.activity.book.LongDeletePresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class LongDeleteModule {
    private final AppCompatActivity activity;
    private LongDeleteControl.LongDeleteView view;

    public LongDeleteModule(AppCompatActivity activity, LongDeleteControl.LongDeleteView view) {
        this.activity = activity;
        this.view = view;
    }

    public LongDeleteModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    LongDeleteControl.LongDeleteView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    LongDeleteControl.PresenterLongDelete providePresenterLongDelete(LongDeletePresenterImpl longDeletePresenter) {
        return longDeletePresenter;
    }

    @Provides
    @PerActivity
    BookModel provideBookModel(Gson gson, ModelTransform modelTransform) {
        return new BookModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.MAN_HUA_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BookApi.class), gson, modelTransform);
    }




}
