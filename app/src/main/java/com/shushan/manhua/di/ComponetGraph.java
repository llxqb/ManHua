package com.shushan.manhua.di;


import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.mvp.ui.activity.setting.SettingActivity;
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
}
