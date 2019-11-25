package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.utils.ToastUtils;


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

    EditText messageEt;

    private void handlePopListView(View contentView) {

        ImageView sendMessageLeftIv = contentView.findViewById(R.id.send_message_left_iv);
        ImageView sendMessageRightIv = contentView.findViewById(R.id.send_message_right_iv);
        messageEt = contentView.findViewById(R.id.message_et);
        messageEt.requestFocus();//获取焦点
        TextView sendTv = contentView.findViewById(R.id.send_tv);
        messageEt.addTextChangedListener(search_text_OnChange);
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
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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


    public TextWatcher search_text_OnChange = new TextWatcher() {
        private int selectionStart;
        private int selectionEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            selectionStart = messageEt.getSelectionStart();
            selectionEnd = messageEt.getSelectionEnd();
            if (s.length() > 30) {
                ToastUtils.showShort(mContext, mContext.getResources().getString(R.string.BarrageSoftKeyPopupWindow_limit_text));
                s.delete(selectionStart - 1, selectionEnd);
            }
        }
    };


}
