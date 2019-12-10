package com.shushan.manhua.mvp.ui.activity.splash;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.ServerConstant;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 协议
 */
public class ProtocolActivity extends BaseActivity {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_protocol);
        setStatusBar();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getString(R.string.ProtocolActivity_title));
        mProgressbar.setMax(100);
        initWebView();
    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings wetSettings = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeViewClient());
        wetSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        wetSettings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        wetSettings.setSupportZoom(true);//是否可以缩放，默认true
        wetSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        wetSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        wetSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        wetSettings.setAppCacheEnabled(true);//是否使用缓存
        wetSettings.setDomStorageEnabled(true);//DOM Storage 解决对某些标签的不支持出现白屏
// wetSettings.setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        wetSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wetSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//设置图文混排适用
        }
        //https://tpi.zuoye101.com/agreement   隐私协议
        String url = ServerConstant.LOGIN_PROTOCOL_URL;
        mWebView.loadUrl(url);
    }


    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private class WebChromeViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
