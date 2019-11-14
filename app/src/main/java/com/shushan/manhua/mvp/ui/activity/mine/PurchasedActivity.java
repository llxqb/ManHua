package com.shushan.manhua.mvp.ui.activity.mine;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerPurchasedComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.PurchasedModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 已购漫画
 */
public class PurchasedActivity extends BaseActivity implements PurchasedControl.PurchasedView{

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_purchased);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerPurchasedComponent.builder().appComponent(getAppComponent())
                .purchasedModule(new PurchasedModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
