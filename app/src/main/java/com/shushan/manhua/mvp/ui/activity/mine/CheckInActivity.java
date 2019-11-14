package com.shushan.manhua.mvp.ui.activity.mine;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCheckInComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CheckInModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 签到
 */
public class CheckInActivity extends BaseActivity implements CheckInControl.CheckInView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_check_in);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerCheckInComponent.builder().appComponent(getAppComponent())
                .checkInModule(new CheckInModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
