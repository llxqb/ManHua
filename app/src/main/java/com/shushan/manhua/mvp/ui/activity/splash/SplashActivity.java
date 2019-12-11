package com.shushan.manhua.mvp.ui.activity.splash;

import android.net.Uri;
import android.view.WindowManager;

import com.facebook.applinks.AppLinkData;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.user.BuProcessor;
import com.shushan.manhua.mvp.ui.activity.book.ReadBaseActivity;
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
        getDelayFbDeepLink();
    }

    @Override
    public void initData() {

    }


    /**
     * facebook 延迟深度链接
     */
    private void getDelayFbDeepLink() {
        AppLinkData.fetchDeferredAppLinkData(this, appLinkData -> {
            if (appLinkData != null) {
                Uri targetUrl = appLinkData.getTargetUri();
                if (targetUrl != null) {
                    String url = targetUrl.toString();
                    if (url.contains("pulaukomik://com.shushan.manhua/read")) {
                        String bookId = "book_id";
                        String catalogueId = "catalogue_id";
//                            String book_id_key = "bookId";
//                            String book_title_key = "title";
//                            String bookTitle = "";
//                            String book_id = url.substring(url.indexOf(book_id_key) + book_id_key.length() + 1);
//                            if (url.contains("title")) {
//                                bookTitle = url.substring(url.indexOf(book_title_key) + book_title_key.length() + 1);
//                            }
//                            if (TextUtils.isEmpty(book_id)) {
//                                return;
//                            }
/////////这里获取到参数后跳转到对应的页面
//                            Intent i = new Intent(SplashActivity.this, ReadActivity.class);
//                            i.putExtra("bookId", book_id);
//                            i.putExtra("title", bookTitle);
//                            startActivity(i);
                        ReadBaseActivity.start(SplashActivity.this, bookId, Integer.parseInt(catalogueId));
                        finish();
                    }
                }
            }
        });
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
