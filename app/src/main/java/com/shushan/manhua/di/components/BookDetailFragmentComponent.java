package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.BookDetailFragmentModule;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {BookDetailModule.class, BookDetailFragmentModule.class})
public interface BookDetailFragmentComponent {
    AppCompatActivity activity();

    void inject(BookDetailFragment fragment);
}
