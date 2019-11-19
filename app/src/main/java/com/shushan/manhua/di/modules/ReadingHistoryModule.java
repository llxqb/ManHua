package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.book.ReadingHistoryControl;
import com.shushan.manhua.mvp.ui.activity.book.ReadPresenterImpl;
import com.shushan.manhua.mvp.ui.activity.book.ReadingHistoryPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class ReadingHistoryModule {
    private final AppCompatActivity activity;
    private ReadingHistoryControl.ReadingHistoryView view;

    public ReadingHistoryModule(AppCompatActivity activity, ReadingHistoryControl.ReadingHistoryView view) {
        this.activity = activity;
        this.view = view;
    }

    public ReadingHistoryModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    ReadingHistoryControl.ReadingHistoryView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ReadingHistoryControl.PresenterReadingHistory providePresenterReadingHistory(ReadingHistoryPresenterImpl readingHistoryPresenter) {
        return readingHistoryPresenter;
    }

    @Provides
    @PerActivity
    BookModel provideBookModel(Gson gson, ModelTransform modelTransform) {
        return new BookModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.WORK_STU_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BookApi.class), gson, modelTransform);
    }




}