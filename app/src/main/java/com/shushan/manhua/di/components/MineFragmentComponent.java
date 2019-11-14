package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.di.modules.MineFragmentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.mine.MineFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, MineFragmentModule.class})
public interface MineFragmentComponent {
    AppCompatActivity activity();

    void inject(MineFragment fragment);
}
