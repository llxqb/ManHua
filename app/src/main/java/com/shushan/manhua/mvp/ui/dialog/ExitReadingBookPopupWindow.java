package com.shushan.manhua.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.R;


/**
 * 拍照PopupWindow
 */
public class ExitReadingBookPopupWindow {
    private Context mContext;
    private PopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;

    public ExitReadingBookPopupWindow(Context context, PopupWindowListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_exit_read_book, null);
        //处理popWindow 显示内容
        handlePopListView(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void handlePopListView(View contentView) {
        TextView addBookshelfTv = contentView.findViewById(R.id.add_bookshelf_tv);
        TextView exitTv = contentView.findViewById(R.id.exit_tv);
        TextView cancelTv = contentView.findViewById(R.id.cancel_tv);
        addBookshelfTv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.addBookShelf();
                mCustomPopWindow.dissmiss();
            }
        });
        exitTv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.exitReading();
                mCustomPopWindow.dissmiss();
            }
        });
        cancelTv.setOnClickListener(v -> mCustomPopWindow.dissmiss());
    }

    public interface PopupWindowListener {
        void addBookShelf();

        void exitReading();
    }
}
