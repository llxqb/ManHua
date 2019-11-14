package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.ExpensesRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.ExpensesRecordFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {TransactionDetailsModule.class, ExpensesRecordFragmentModule.class})
public interface ExpensesRecordFragmentComponent {
    AppCompatActivity activity();

    void inject(ExpensesRecordFragment fragment);
}
