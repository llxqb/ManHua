package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.activity.mine.TransactionDetailsActivity;

import dagger.Component;

/**
 * LoginComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by li.liu on 18/1/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {TransactionDetailsModule.class, ActivityModule.class})
public interface TransactionDetailsComponent extends ActivityComponent {
    void inject(TransactionDetailsActivity activity);

    AppCompatActivity activity();


}
