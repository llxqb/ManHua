package com.shushan.manhua.mvp.ui.activity.mine;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBuyComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BuyModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 充值中心 ，购买
 */
public class BuyActivity extends BaseActivity implements BuyControl.BuyView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_buy);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerBuyComponent.builder().appComponent(getAppComponent())
                .buyModule(new BuyModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
