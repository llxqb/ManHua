package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.book.CommentDetailControl;
import com.shushan.manhua.mvp.ui.activity.book.CommentDetailPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class CommentDetailModule {
    private final AppCompatActivity activity;
    private CommentDetailControl.CommentDetailView view;

    public CommentDetailModule(AppCompatActivity activity, CommentDetailControl.CommentDetailView view) {
        this.activity = activity;
        this.view = view;
    }

    public CommentDetailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    CommentDetailControl.CommentDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    CommentDetailControl.PresenterCommentDetail providePresenterCommentDetail(CommentDetailPresenterImpl commentDetailPresenter) {
        return commentDetailPresenter;
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
