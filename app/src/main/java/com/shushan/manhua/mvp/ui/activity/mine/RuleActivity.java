package com.shushan.manhua.mvp.ui.activity.mine;

import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 规则
 */
public class RuleActivity extends BaseActivity {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_rule);
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText("Aturan");
    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }
}
