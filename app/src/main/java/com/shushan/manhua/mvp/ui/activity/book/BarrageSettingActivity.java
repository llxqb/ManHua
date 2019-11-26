package com.shushan.manhua.mvp.ui.activity.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 弹幕设置
 */
public class BarrageSettingActivity extends BaseActivity {

    @BindView(R.id.page_turning_iv)
    ImageView mPageTurningIv;
    @BindView(R.id.night_model_iv)
    ImageView mNightModelIv;
    @BindView(R.id.barrage_switch_iv)
    ImageView mBarrageSwitchIv;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.seek_bar_tv)
    TextView mSeekBarTv;
    @BindView(R.id.play_seek_bar)
    SeekBar mPlaySeekBar;
    @BindView(R.id.play_seek_bar_tv)
    TextView mPlaySeekBarTv;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_barrage_setting);
        setStatusBar();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_left_iv, R.id.model_tv, R.id.page_turning_iv, R.id.night_model_iv, R.id.barrage_switch_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.model_tv:
                break;
            case R.id.page_turning_iv:
                break;
            case R.id.night_model_iv:
                break;
            case R.id.barrage_switch_iv:
                break;
        }
    }
}
