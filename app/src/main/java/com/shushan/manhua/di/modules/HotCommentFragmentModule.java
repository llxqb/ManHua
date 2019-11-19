package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.moreComment.HotCommentFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class HotCommentFragmentModule {

    private HotCommentFragmentControl.HotCommentView mFragmentView;

    public HotCommentFragmentModule(LoadDataView view) {
        if (view instanceof HotCommentFragmentControl.HotCommentView) {
            mFragmentView = (HotCommentFragmentControl.HotCommentView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    HotCommentFragmentControl.HotCommentView hotCommentFragmentView() {
        return this.mFragmentView;
    }


}
