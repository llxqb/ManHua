package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadActivity;
import com.shushan.manhua.mvp.ui.activity.book.ReadBaseActivity;

import dagger.Component;

/**
 * LoginComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by li.liu on 18/1/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ReadModule.class, ActivityModule.class})
public interface ReadComponent extends ActivityComponent {
    //对LoginActivity进行依赖注入
    void inject(ReadBaseActivity activity);
    void inject(ReadActivity activity);

    AppCompatActivity activity();


}
