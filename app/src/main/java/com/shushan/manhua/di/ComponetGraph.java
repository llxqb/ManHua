package com.shushan.manhua.di;


import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.mvp.ui.activity.book.BarrageSettingActivity;
import com.shushan.manhua.mvp.ui.activity.book.LookPhotoActivity;
import com.shushan.manhua.mvp.ui.activity.setting.SettingActivity;
import com.shushan.manhua.mvp.ui.activity.splash.SplashActivity;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

/**
 * Created by wxl on 16/3/30.
 */
public interface ComponetGraph {

    void inject(ManHuaApplication application);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(SettingActivity settingActivity);//设置

    void inject(BarrageSettingActivity barrageSettingActivity);//弹幕设置

    void inject(LookPhotoActivity lookPhotoActivity);//查看大图

    void inject(SplashActivity splashActivity);//启动页
}
