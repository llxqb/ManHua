package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.message.NoticeMessageFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class NoticeMessageFragmentModule {

    private NoticeMessageFragmentControl.NoticeMessageView mFragmentView;

    public NoticeMessageFragmentModule(LoadDataView view) {
        if (view instanceof NoticeMessageFragmentControl.NoticeMessageView) {
            mFragmentView = (NoticeMessageFragmentControl.NoticeMessageView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    NoticeMessageFragmentControl.NoticeMessageView noticeMessageFragmentView() {
        return this.mFragmentView;
    }


}
