package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.book.MoreCommentControl;
import com.shushan.manhua.mvp.ui.activity.book.MoreCommentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.moreComment.HotCommentFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.moreComment.HotCommentFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.moreComment.LatestCommentFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.moreComment.LatestCommentFragmentPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class MoreCommentModule {
    private final AppCompatActivity activity;
    private MoreCommentControl.MoreCommentView view;

    public MoreCommentModule(AppCompatActivity activity, MoreCommentControl.MoreCommentView view) {
        this.activity = activity;
        this.view = view;
    }

    public MoreCommentModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MoreCommentControl.MoreCommentView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MoreCommentControl.PresenterMoreComment providePresenterMoreComment(MoreCommentPresenterImpl moreCommentPresenter) {
        return moreCommentPresenter;
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

    /**
     * 最新评论
     */
    @Provides
    @PerActivity
    LatestCommentFragmentControl.LatestCommentFragmentPresenter providePresenterLatestCommentFragment(LatestCommentFragmentPresenterImpl latestCommentFragmentPresenter) {
        return latestCommentFragmentPresenter;
    }

    /**
     * 最热评论
     */
    @Provides
    @PerActivity
    HotCommentFragmentControl.HotCommentFragmentPresenter providePresenterHotCommentFragment(HotCommentFragmentPresenterImpl hotCommentFragmentPresenter) {
        return hotCommentFragmentPresenter;
    }


}
