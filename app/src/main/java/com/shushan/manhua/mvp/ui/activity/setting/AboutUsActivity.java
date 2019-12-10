package com.shushan.manhua.mvp.ui.activity.setting;

import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_about_us);
        setStatusBar();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText("Tentang kami");
    }

    @Override
    public void initData() {
    }


    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }
}
