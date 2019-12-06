package com.shushan.manhua.listener;

import android.content.Context;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 友盟分享回调
 */
public class MyUMShareListener implements UMShareListener {

    private Context mContext;
    private ShareResultListener mShareResultListener;

    public MyUMShareListener(ShareResultListener shareResultListener, Context context) {
        mShareResultListener = shareResultListener;
        mContext = context;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
//        Toast.makeText(mContext, "success", Toast.LENGTH_LONG).show();
        if (mShareResultListener != null) {
            mShareResultListener.shareSuccess();
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Toast.makeText(mContext, "cancel", Toast.LENGTH_LONG).show();
    }

    public interface ShareResultListener {
        void shareSuccess();
    }
}
