package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.utils.SharePreferenceUtil;


/**
 * 阅读设置页PopupWindow
 */
public class ReadSettingPopupWindow {
    private Activity mContext;
    private ReadSettingPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private SharePreferenceUtil mSharePreferenceUtil;
    ImageView pageTurningIv;
    ImageView nightModelIv;
    ImageView barrageSwitchIv;

    public ReadSettingPopupWindow(Activity context, ReadSettingPopupWindowListener popupWindowListener, SharePreferenceUtil sharePreferenceUtil) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        mSharePreferenceUtil = sharePreferenceUtil;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_read_setting, null);
        //处理popWindow 显示内容
        handlePopListView(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void handlePopListView(View contentView) {
        pageTurningIv = contentView.findViewById(R.id.page_turning_iv);
        nightModelIv = contentView.findViewById(R.id.night_model_iv);
        barrageSwitchIv = contentView.findViewById(R.id.barrage_switch_iv);
        TextView moreTv = contentView.findViewById(R.id.more_tv);
        boolean pageTurning = mSharePreferenceUtil.getBooleanData("pageTurning", true);
        boolean nightModel = mSharePreferenceUtil.getBooleanData("nightModel", false);
        boolean barrageSwitch = mSharePreferenceUtil.getBooleanData("barrageSwitch", false);

        if (pageTurning) {
            pageTurningIv.setImageResource(R.mipmap.switch_open);
        } else {
            pageTurningIv.setImageResource(R.mipmap.switch_close);
        }
        if (nightModel) {
            nightModelIv.setImageResource(R.mipmap.switch_open);
        } else {
            nightModelIv.setImageResource(R.mipmap.switch_close);
        }
        if (barrageSwitch) {
            pageTurningIv.setImageResource(R.mipmap.switch_open);
        } else {
            pageTurningIv.setImageResource(R.mipmap.switch_close);
        }

        pageTurningIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.pageTurningBtnListener();

            }
        });
        nightModelIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                checkPermissions();
            }
        });
        barrageSwitchIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.barrageSwitchBtnListener();
            }
        });
        moreTv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.clickMoreBtnListener();
            }
        });
//        cancelTv.setOnClickListener(v -> mCustomPopWindow.dissmiss());
    }


    /**
     * 检查app 权限
     */
    private void checkPermissions() {
        //设置系统亮度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(mContext)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                //有了权限，具体的动作
                boolean nightModel = mSharePreferenceUtil.getBooleanData("nightModel", false);
                if (nightModel) {
                    //设置白天模式
                    mSharePreferenceUtil.setData("nightModel", false);
                    nightModelIv.setImageResource(R.mipmap.switch_close);
                } else {
                    //设置夜间模式
                    mSharePreferenceUtil.setData("nightModel", true);
                    nightModelIv.setImageResource(R.mipmap.switch_open);
                }
                mPopupWindowListener.nightModelBtnListener(nightModel);
            }
        }

    }

    public interface ReadSettingPopupWindowListener {
        void pageTurningBtnListener();

        void nightModelBtnListener(boolean nightModel);

        void barrageSwitchBtnListener();

        void clickMoreBtnListener();
    }
}
