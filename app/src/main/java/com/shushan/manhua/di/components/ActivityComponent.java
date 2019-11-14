package com.shushan.manhua.di.components;

import android.app.Activity;

import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.scopes.PerActivity;

import dagger.Component;

/**
 *
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();
}
