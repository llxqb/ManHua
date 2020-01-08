package com.shushan.manhua;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.shushan.manhua.di.components.AppComponent;
import com.shushan.manhua.di.components.DaggerAppComponent;
import com.shushan.manhua.di.modules.AppModule;
import com.shushan.manhua.entity.constants.ServerConstant;
import com.umeng.commonsdk.UMConfigure;

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
        initUM();
        logActivatedAppEvent();
    }

    /**
     * 初始化友盟
     * 用到了友盟分享
     */
    private void initUM() {
        UMConfigure.init(this, ServerConstant.UM_APP_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }


    /**
     * 记录facebook活跃用户
     */
    public void logActivatedAppEvent() {
        //初始化Facebook SDK
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP);
    }

}
