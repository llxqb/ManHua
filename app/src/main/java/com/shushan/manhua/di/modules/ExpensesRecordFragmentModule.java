package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.ExpensesRecordFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class ExpensesRecordFragmentModule {

    private ExpensesRecordFragmentControl.ExpensesRecordView mFragmentView;

    public ExpensesRecordFragmentModule(LoadDataView view) {
        if (view instanceof ExpensesRecordFragmentControl.ExpensesRecordView) {
            mFragmentView = (ExpensesRecordFragmentControl.ExpensesRecordView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    ExpensesRecordFragmentControl.ExpensesRecordView expensesRecordFragmentView() {
        return this.mFragmentView;
    }



}
