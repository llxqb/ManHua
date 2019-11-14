package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.components.DaggerSearchComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.di.modules.SearchModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements SearchControl.SearchView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_search);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerSearchComponent.builder().appComponent(getAppComponent())
                .searchModule(new SearchModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
