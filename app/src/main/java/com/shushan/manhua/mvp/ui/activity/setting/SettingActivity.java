package com.shushan.manhua.mvp.ui.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.activity.main.MainActivity;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.CommonDialog;
import com.shushan.manhua.mvp.utils.DataCleanManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements CommonDialog.CommonDialogListener {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.clear_cache_tv)
    TextView mClearCacheTv;
    @BindView(R.id.version_update_tv)
    TextView mVersionUpdateTv;
    /**
     * 清理缓存 1
     * 退出登录 2
     */
    private int commonDialogClickType;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_setting);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
        setStatusBar();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.SettingActivity_title));
    }

    @Override
    public void initData() {
        try {
            mClearCacheTv.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.common_left_iv, R.id.clear_cache_ll, R.id.version_update_ll, R.id.protocol_ll, R.id.about_us_ll, R.id.exit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.clear_cache_ll://清理缓存
                commonDialogClickType = 1;
                showClearCacheDialog();
                break;
            case R.id.version_update_ll://版本更新
                showToast("版本更新");
                break;
            case R.id.protocol_ll://用户协议
                showToast("用户协议");
                break;
            case R.id.about_us_ll://关于我们
                showToast("关于我们");
                break;
            case R.id.exit_tv:
                commonDialogClickType = 2;
                showExitLoginDialog();
                break;
        }
    }

    private void showClearCacheDialog() {
        String cacheValue = mClearCacheTv.getText().toString();
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.SettingActivity_cache_hint), cacheValue, getResources().getString(R.string.SettingActivity_cancel), getResources().getString(R.string.SettingActivity_sure), Constant.COMMON_DIALOG_STYLE_1);
    }

    private void showExitLoginDialog() {
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.SettingActivity_exit_hint), "", getResources().getString(R.string.SettingActivity_cancel), getResources().getString(R.string.SettingActivity_sure), Constant.COMMON_DIALOG_STYLE_1);
    }

    @Override
    public void commonDialogBtnOkListener() {
        if (commonDialogClickType == 1) {
            DataCleanManager.clearAllCache(this);
            try {
                mClearCacheTv.setText(DataCleanManager.getTotalCacheSize(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            exitLogin();
        }
    }


    /**
     * 退出登录
     */
    public void exitLogin() {
        mSharePreferenceUtil.clearData();
//        RongIM.getInstance().logout();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//表示 不创建新的实例activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//表示 移除该activity上面的activity
        intent.putExtra("exitLogin", true);
        startActivity(intent);
        finish();
    }
}
