package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.message.SentMessageFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class SentMessageFragmentModule {

    private SentMessageFragmentControl.SentMessageView mFragmentView;

    public SentMessageFragmentModule(LoadDataView view) {
        if (view instanceof SentMessageFragmentControl.SentMessageView) {
            mFragmentView = (SentMessageFragmentControl.SentMessageView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    SentMessageFragmentControl.SentMessageView sentMessageFragmentView() {
        return this.mFragmentView;
    }


}
