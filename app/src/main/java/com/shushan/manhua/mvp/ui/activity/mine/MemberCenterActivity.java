package com.shushan.manhua.mvp.ui.activity.mine;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMemberCenterComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MemberCenterModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity implements MemberCenterControl.MemberCenterView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_member_center);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerMemberCenterComponent.builder().appComponent(getAppComponent())
                .memberCenterModule(new MemberCenterModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
