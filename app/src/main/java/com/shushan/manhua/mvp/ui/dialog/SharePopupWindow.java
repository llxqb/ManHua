package com.shushan.manhua.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.R;


/**
 * 拍照PopupWindow
 */
public class SharePopupWindow {
    private Context mContext;
    private PopupWindowShareListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;

    public SharePopupWindow(Context context, PopupWindowShareListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_share, null);
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
        LinearLayout shareFacebookLl = contentView.findViewById(R.id.share_facebook_ll);
        LinearLayout shareWhatsappLl = contentView.findViewById(R.id.share_whatsapp_ll);
        TextView cancelTv = contentView.findViewById(R.id.cancel_tv);
        shareFacebookLl.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.shareFacebookBtnListener();
                mCustomPopWindow.dissmiss();
            }
        });
        shareWhatsappLl.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.shareWhatsAppBtnListener();
                mCustomPopWindow.dissmiss();
            }
        });
        cancelTv.setOnClickListener(v -> mCustomPopWindow.dissmiss());
    }

    public interface PopupWindowShareListener {
        void shareFacebookBtnListener();

        void shareWhatsAppBtnListener();
    }
}
