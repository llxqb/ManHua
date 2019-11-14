package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.BookShelfFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.bookshelf.BookShelfFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, BookShelfFragmentModule.class})
public interface BookShelfFragmentComponent {
    AppCompatActivity activity();

    void inject(BookShelfFragment fragment);
}
