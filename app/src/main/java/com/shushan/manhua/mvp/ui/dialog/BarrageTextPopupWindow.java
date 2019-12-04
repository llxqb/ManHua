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
public class BarrageTextPopupWindow {
    private Context mContext;
    private CustomPopWindow mCustomPopWindow;

    public BarrageTextPopupWindow(Context context) {
        mContext = context;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_barrage_text, null);
        //处理popWindow 显示内容
        handlePopListView(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(false)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void handlePopListView(View contentView) {
        TextView textTv = contentView.findViewById(R.id.text_tv);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(200, 200, 0, 0);
        textTv.setLayoutParams(layoutParams);
    }

}
