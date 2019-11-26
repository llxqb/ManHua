package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.BookModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailControl;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionFragmentPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.BookApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class BookDetailModule {
    private final AppCompatActivity activity;
    private BookDetailControl.BookDetailView view;

    public BookDetailModule(AppCompatActivity activity, BookDetailControl.BookDetailView view) {
        this.activity = activity;
        this.view = view;
    }

    public BookDetailModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    BookDetailControl.BookDetailView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    BookDetailControl.PresenterBookDetail providePresenterBookDetail(BookDetailPresenterImpl bookDetailPresenter) {
        return bookDetailPresenter;
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
     * 漫画详情
     */
    @Provides
    @PerActivity
    BookDetailFragmentControl.BookDetailFragmentPresenter providePresenterBookDetailFragment(BookDetailFragmentPresenterImpl bookDetailFragmentPresenter) {
        return bookDetailFragmentPresenter;
    }
    /**
     * 漫画选集
     */
    @Provides
    @PerActivity
    SelectionFragmentControl.SelectionFragmentPresenter providePresenterSelectionFragment(SelectionFragmentPresenterImpl selectionFragmentPresenter) {
        return selectionFragmentPresenter;
    }



}
