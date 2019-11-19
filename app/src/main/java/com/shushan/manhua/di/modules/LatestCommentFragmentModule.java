package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.moreComment.LatestCommentFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class LatestCommentFragmentModule {

    private LatestCommentFragmentControl.LatestCommentView mFragmentView;

    public LatestCommentFragmentModule(LoadDataView view) {
        if (view instanceof LatestCommentFragmentControl.LatestCommentView) {
            mFragmentView = (LatestCommentFragmentControl.LatestCommentView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    LatestCommentFragmentControl.LatestCommentView latestCommentFragmentView() {
        return this.mFragmentView;
    }


}
