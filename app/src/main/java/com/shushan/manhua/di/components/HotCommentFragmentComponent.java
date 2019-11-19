package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.HotCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.moreComment.HotCommentFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MoreCommentModule.class, HotCommentFragmentModule.class})
public interface HotCommentFragmentComponent {
    AppCompatActivity activity();

    void inject(HotCommentFragment fragment);
}
