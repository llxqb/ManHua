package com.shushan.manhua.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.manhua.di.modules.RechargeRecordFragmentModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.RechargeRecordFragment;

import dagger.Component;

/**
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {TransactionDetailsModule.class, RechargeRecordFragmentModule.class})
public interface RechargeRecordFragmentComponent {
    AppCompatActivity activity();

    void inject(RechargeRecordFragment fragment);
}
