package com.shushan.manhua;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.shushan.manhua.di.components.AppComponent;
import com.shushan.manhua.di.components.DaggerAppComponent;
import com.shushan.manhua.di.modules.AppModule;
import com.shushan.manhua.entity.constants.ServerConstant;
import com.umeng.commonsdk.UMConfigure;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 漫画 application
 */
public class ManHuaApplication extends Application {
    private String TAG = "HomeworkApplication";
    private AppComponent mAppComponent;
    public Context mContext;
    @Inject
    Gson mGson;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
        initUM();
        logActivatedAppEvent();
//        handleSSLHandshake();
    }

    /**
     * 初始化友盟
     * 用到了友盟分享
     */
    private void initUM() {
        UMConfigure.init(this, ServerConstant.UM_APP_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }


    /**
     * 记录facebook活跃用户
     */
    public void logActivatedAppEvent() {
        //初始化Facebook SDK
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
//        FacebookSdk.sdkInitialize(getApplicationContext());
        com.umeng.facebook.FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP);
    }


    /**
     * 忽略https的证书校验
     * <p>
     * 避免Glide加载https图片报错：
     * <p>
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */
    private void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
