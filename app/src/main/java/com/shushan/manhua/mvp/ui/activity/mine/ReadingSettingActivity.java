package com.shushan.manhua.mvp.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 阅读偏好
 */
public class ReadingSettingActivity extends BaseActivity {

    @BindView(R.id.channel_female_iv)
    ImageView mChannelFemaleIv;
    @BindView(R.id.channel_male_iv)
    ImageView mChannelMaleIv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_reading_setting);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.common_left_iv, R.id.channel_female_ll, R.id.channel_male_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                break;
            case R.id.channel_female_ll:
                break;
            case R.id.channel_male_ll:
                break;
        }
    }
}
