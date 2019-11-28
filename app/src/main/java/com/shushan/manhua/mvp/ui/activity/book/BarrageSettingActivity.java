package com.shushan.manhua.mvp.ui.activity.book;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
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
    boolean barrageFlag;//弹幕开关
    boolean turnPageFlag;//上下翻页
    boolean nightModelFlag;//夜间模式

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_barrage_setting);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
        setStatusBar();
    }

    @Override
    public void initView() {
        barrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE);
        turnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE);
        nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL);
        if (barrageFlag) {
            mBarrageSwitchIv.setImageResource(R.mipmap.switch_open);
        } else {
            mBarrageSwitchIv.setImageResource(R.mipmap.switch_close);
        }
        if (turnPageFlag) {
            mPageTurningIv.setImageResource(R.mipmap.switch_open);
        } else {
            mPageTurningIv.setImageResource(R.mipmap.switch_close);
        }
        if (nightModelFlag) {
            mNightModelIv.setImageResource(R.mipmap.switch_open);
        } else {
            mNightModelIv.setImageResource(R.mipmap.switch_close);
        }
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
                turnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE);
                if (turnPageFlag) {
                    mSharePreferenceUtil.setData(Constant.IS_TURN_PAGE, false);
                    mNightModelIv.setImageResource(R.mipmap.switch_close);
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_TURN_PAGE, true);
                    mNightModelIv.setImageResource(R.mipmap.switch_open);
                }
                break;
            case R.id.night_model_iv:
                nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL);
                if (nightModelFlag) {
                    mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, false);
                    mBarrageSwitchIv.setImageResource(R.mipmap.switch_close);
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, true);
                    mBarrageSwitchIv.setImageResource(R.mipmap.switch_open);
                }
                break;
            case R.id.barrage_switch_iv:
                barrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE);
                if (barrageFlag) {
                    mSharePreferenceUtil.setData(Constant.IS_BARRAGE, false);
                    mBarrageSwitchIv.setImageResource(R.mipmap.switch_close);
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_BARRAGE, true);
                    mBarrageSwitchIv.setImageResource(R.mipmap.switch_open);
                }
                break;
        }
    }
}
