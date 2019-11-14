package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.RechargeRecordFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class RechargeRecordFragmentModule {

    private RechargeRecordFragmentControl.RechargeRecordView mFragmentView;

    public RechargeRecordFragmentModule(LoadDataView view) {
        if (view instanceof RechargeRecordFragmentControl.RechargeRecordView) {
            mFragmentView = (RechargeRecordFragmentControl.RechargeRecordView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    RechargeRecordFragmentControl.RechargeRecordView rechargeRecordFragmentView() {
        return this.mFragmentView;
    }


}
