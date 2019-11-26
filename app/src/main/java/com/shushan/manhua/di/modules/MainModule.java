package com.shushan.manhua.di.modules;


import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.MainModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.main.MainControl;
import com.shushan.manhua.mvp.ui.activity.main.MainPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.bookshelf.BookShelfFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.bookshelf.BookShelfFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.home.HomeFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.home.HomeFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.mine.MineFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.mine.MineFragmentPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.MainApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class MainModule {
    private final AppCompatActivity activity;
    private MainControl.MainView view;

    public MainModule(AppCompatActivity activity, MainControl.MainView view) {
        this.activity = activity;
        this.view = view;
    }

    public MainModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainControl.MainView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(MainPresenterImpl presenterMain) {
        return presenterMain;
    }

    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform) {
        return new MainModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.MAN_HUA_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform);
    }


    /**
     * 首页
     */
    @Provides
    @PerActivity
    HomeFragmentControl.homeFragmentPresenter providePresenterHomeFragment(HomeFragmentPresenterImpl homeFragmentPresenter) {
        return homeFragmentPresenter;
    }

    /**
     * 书架
     */
    @Provides
    @PerActivity
    BookShelfFragmentControl.BookShelfFragmentPresenter providePresenterBookShelfFragment(BookShelfFragmentPresenterImpl bookShelfFragmentPresenter) {
        return bookShelfFragmentPresenter;
    }


    /**
     * 我的
     */
    @Provides
    @PerActivity
    MineFragmentControl.MineFragmentPresenter providePresenterMineFragment(MineFragmentPresenterImpl mineFragmentPresenter) {
        return mineFragmentPresenter;
    }


}
