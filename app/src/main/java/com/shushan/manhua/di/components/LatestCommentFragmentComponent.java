package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.LatestCommentFragmentModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.moreComment.LatestCommentFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MoreCommentModule.class, LatestCommentFragmentModule.class})
public interface LatestCommentFragmentComponent {
    AppCompatActivity activity();

    void inject(LatestCommentFragment fragment);
}
