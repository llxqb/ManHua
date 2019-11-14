package com.shushan.manhua.mvp.ui.activity.book;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadingHistoryComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadingHistoryModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 浏览历史
 */
public class ReadingHistoryActivity extends BaseActivity implements ReadingHistoryControl.ReadingHistoryView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_history_read);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerReadingHistoryComponent.builder().appComponent(getAppComponent())
                .readingHistoryModule(new ReadingHistoryModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
