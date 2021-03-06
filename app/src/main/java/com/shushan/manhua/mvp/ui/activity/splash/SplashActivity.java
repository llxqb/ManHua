package com.shushan.manhua.mvp.ui.activity.splash;

import android.view.WindowManager;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.user.BuProcessor;
import com.shushan.manhua.mvp.ui.activity.main.MainActivity;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    private ScheduledExecutorService scheduledThreadPool;
    @Inject
    protected BuProcessor mBuProcessor;

    @Override
    protected void initContentView() {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(1);
        }
        scheduledThreadPool.schedule(() -> {
//            if (!mBuProcessor.isValidLogin()) {
//                startActivitys(LoginActivity.class);
//                finish();
//            } else {
//                startActivitys(MainActivity.class);
//                finish();
//            }
            startActivitys(MainActivity.class);
            finish();
        }, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (scheduledThreadPool != null) {
                scheduledThreadPool.shutdownNow();
                scheduledThreadPool = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
