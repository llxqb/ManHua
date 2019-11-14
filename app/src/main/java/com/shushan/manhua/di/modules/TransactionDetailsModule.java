package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.ui.activity.mine.TransactionDetailsControl;
import com.shushan.manhua.mvp.ui.activity.mine.TransactionDetailsPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.ExpensesRecordFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.ExpensesRecordFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.RechargeRecordFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.RechargeRecordFragmentPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.MineApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class TransactionDetailsModule {
    private final AppCompatActivity activity;
    private TransactionDetailsControl.TransactionDetailsView view;

    public TransactionDetailsModule(AppCompatActivity activity, TransactionDetailsControl.TransactionDetailsView view) {
        this.activity = activity;
        this.view = view;
    }

    public TransactionDetailsModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    TransactionDetailsControl.TransactionDetailsView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    TransactionDetailsControl.PresenterTransactionDetails provideTransactionDetails(TransactionDetailsPresenterImpl transactionDetailsPresenter) {
        return transactionDetailsPresenter;
    }

    @Provides
    @PerActivity
    MineModel provideMineModel(Gson gson, ModelTransform modelTransform) {
        return new MineModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.WORK_STU_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MineApi.class), gson, modelTransform);
    }

    /**
     * 充值记录
     */
    @Provides
    @PerActivity
    RechargeRecordFragmentControl.RechargeRecordFragmentPresenter providePresenterRechargeRecordFragment(RechargeRecordFragmentPresenterImpl rechargeRecordFragmentPresenter) {
        return rechargeRecordFragmentPresenter;
    }
    /**
     * 消费记录
     */
    @Provides
    @PerActivity
    ExpensesRecordFragmentControl.ExpensesRecordFragmentPresenter providePresenterExpensesRecordFragment(ExpensesRecordFragmentPresenterImpl expensesRecordFragmentPresenter) {
        return expensesRecordFragmentPresenter;
    }
}
