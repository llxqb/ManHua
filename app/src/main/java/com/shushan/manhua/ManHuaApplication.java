package com.shushan.manhua;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.di.components.AppComponent;
import com.shushan.manhua.di.components.DaggerAppComponent;
import com.shushan.manhua.di.modules.AppModule;

import javax.inject.Inject;


/**
 * 漫画 application
 */
public class ManHuaApplication extends Application {
    private String TAG = "HomeworkApplication";
    private AppComponent mAppComponent;
    public Context mContext;
    @Inject
    Gson mGson;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
        //初始化内存泄漏检查工具
    }


}
