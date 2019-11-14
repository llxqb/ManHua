package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.MessageModule;
import com.shushan.manhua.di.modules.ReceivedMessageFragmentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.message.ReceivedMessageFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MessageModule.class, ReceivedMessageFragmentModule.class})
public interface ReceivedMessageFragmentComponent {
    AppCompatActivity activity();

    void inject(ReceivedMessageFragment fragment);
}
