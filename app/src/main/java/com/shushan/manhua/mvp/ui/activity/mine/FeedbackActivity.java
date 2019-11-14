package com.shushan.manhua.mvp.ui.activity.mine;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerFeedbackComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.FeedbackModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements FeedbackControl.FeedbackView {

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_feedback);
        initInjectData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void initInjectData() {
        DaggerFeedbackComponent.builder().appComponent(getAppComponent())
                .feedbackModule(new FeedbackModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
