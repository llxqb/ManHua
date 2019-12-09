package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 弹幕设置
 */
public class BarrageSettingActivity extends BaseActivity implements CommonDialog.CommonDialogListener {

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
        turnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE, true);
        nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL, false);
        barrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE, true);
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
        if (barrageFlag) {
            mBarrageSwitchIv.setImageResource(R.mipmap.switch_open);
        } else {
            mBarrageSwitchIv.setImageResource(R.mipmap.switch_close);
        }
        int transparency = mSharePreferenceUtil.getIntData(Constant.TRANSPARENCY, 80);
        int playSpeed = mSharePreferenceUtil.getIntData(Constant.PLAY_SPEED, 80);
        mSeekBar.setProgress(transparency);
        mPlaySeekBar.setProgress(playSpeed);
    }

    @Override
    public void initData() {
        initSeekBar();
    }

    private void initSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSharePreferenceUtil.setData(Constant.TRANSPARENCY, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mPlaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSharePreferenceUtil.setData(Constant.PLAY_SPEED, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @OnClick({R.id.common_left_iv, R.id.right_text_tv, R.id.page_turning_iv, R.id.night_model_iv, R.id.barrage_switch_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.right_text_tv:
                DialogFactory.showCommonDialog(this, "Anda yakin ingin mengembalikan pengaturan default", null, getResources().getString(R.string.SettingActivity_cancel), getResources().getString(R.string.SettingActivity_sure), Constant.COMMON_DIALOG_STYLE_1);
                break;
            case R.id.page_turning_iv:
                turnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE);
                if (turnPageFlag) {//设置不可点击翻页
                    mSharePreferenceUtil.setData(Constant.IS_TURN_PAGE, false);
                    mPageTurningIv.setImageResource(R.mipmap.switch_close);
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_TURN_PAGE, true);
                    mPageTurningIv.setImageResource(R.mipmap.switch_open);
                }
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BARRAGE_SETTING));
                break;
            case R.id.night_model_iv:
                nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL);
                if (nightModelFlag) {
                    mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, false);
                    mNightModelIv.setImageResource(R.mipmap.switch_close);
                    //设置白天模式
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 110);//屏幕亮度值范围必须位于：0～255
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, true);
                    mNightModelIv.setImageResource(R.mipmap.switch_open);
                    //设置夜间模式
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
                }
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BARRAGE_SETTING));
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
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BARRAGE_SETTING));
                break;
        }
    }

    @Override
    public void commonDialogBtnOkListener() {
        mSharePreferenceUtil.setData(Constant.IS_TURN_PAGE, true);
        mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, false);
        mSharePreferenceUtil.setData(Constant.IS_BARRAGE, true);
        mPageTurningIv.setImageResource(R.mipmap.switch_open);
        mNightModelIv.setImageResource(R.mipmap.switch_close);
        mBarrageSwitchIv.setImageResource(R.mipmap.switch_open);
        //设置夜间模式
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BARRAGE_SETTING));
    }
}
