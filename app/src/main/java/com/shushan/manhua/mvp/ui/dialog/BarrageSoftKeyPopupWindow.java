package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;


/**
 * 弹幕自定义软键盘PopupWindow
 */
public class BarrageSoftKeyPopupWindow {
    private Activity mContext;
    private BarrageSoftKeyPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;

    public BarrageSoftKeyPopupWindow(Activity context, BarrageSoftKeyPopupWindowListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_barrage_soft_key, null);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //处理popWindow 显示内容
        handlePopListView(contentView);
    }


    private void handlePopListView(View contentView) {
        ImageView sendMessageLeftIv = contentView.findViewById(R.id.send_message_left_iv);
        ImageView sendMessageRightIv = contentView.findViewById(R.id.send_message_right_iv);
        EditText messageEt = contentView.findViewById(R.id.message_et);
        messageEt.requestFocus();//获取焦点
        TextView sendTv = contentView.findViewById(R.id.send_tv);
        sendMessageLeftIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.switchStyleLayoutBtnListenerByBarrageSoftKey();
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //隐藏软键盘
                mCustomPopWindow.dissmiss();
            }
        });
        sendMessageRightIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.showStyleBtnListenerByBarrageSoftKey();
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                mCustomPopWindow.dissmiss();
            }
        });

        sendTv.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(messageEt.getText())) {
                if (mPopupWindowListener != null) {
                    mPopupWindowListener.sendMessageBtnListenerByBarrageSoftKey(messageEt.getText().toString());
//                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mCustomPopWindow.dissmiss();
                }
            }
        });


    }


    public interface BarrageSoftKeyPopupWindowListener {
        void switchStyleLayoutBtnListenerByBarrageSoftKey();

        void showStyleBtnListenerByBarrageSoftKey();

        void sendMessageBtnListenerByBarrageSoftKey(String message);
    }


}
