package com.shushan.manhua.mvp.ui.activity.book;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * 阅读页面
 */
public class ReadActivity extends BaseActivity implements ReadControl.ReadView {

    @Inject
    ReadControl.PresenterRead mPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerReadComponent.builder().appComponent(getAppComponent())
                .readModule(new ReadModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
