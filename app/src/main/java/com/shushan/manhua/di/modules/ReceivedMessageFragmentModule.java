package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.message.ReceivedMessageFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class ReceivedMessageFragmentModule {

    private ReceivedMessageFragmentControl.ReceivedMessageView mFragmentView;

    public ReceivedMessageFragmentModule(LoadDataView view) {
        if (view instanceof ReceivedMessageFragmentControl.ReceivedMessageView) {
            mFragmentView = (ReceivedMessageFragmentControl.ReceivedMessageView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    ReceivedMessageFragmentControl.ReceivedMessageView receivedMessageFragmentView() {
        return this.mFragmentView;
    }


}
