package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.di.modules.SelectionFragmentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionDetailFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {BookDetailModule.class, SelectionFragmentModule.class})
public interface SelectionFragmentComponent {
    AppCompatActivity activity();

    void inject(SelectionDetailFragment fragment);
}
