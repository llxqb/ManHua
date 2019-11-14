package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.MessageModule;
import com.shushan.manhua.di.modules.NoticeMessageFragmentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.message.NoticeMessageFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MessageModule.class, NoticeMessageFragmentModule.class})
public interface NoticeMessageFragmentComponent {
    AppCompatActivity activity();

    void inject(NoticeMessageFragment fragment);
}
