package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BarrageStyleResponse;
import com.shushan.manhua.mvp.ui.adapter.BarrageStyleAdapter;

import java.util.List;


/**
 * 弹幕样式PopupWindow
 */
public class BarrageStylePopupWindow {
    private Activity mContext;
    private BarrageStylePopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private List<BarrageStyleResponse> barrageStyleResponseList;


    public BarrageStylePopupWindow(Activity context, List<BarrageStyleResponse> barrageStyleResponseList, BarrageStylePopupWindowListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        this.barrageStyleResponseList = barrageStyleResponseList;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_barrage_style, null);
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
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        RelativeLayout sendMessageRightRl = contentView.findViewById(R.id.send_message_rl);
        TextView sendTv = contentView.findViewById(R.id.send_tv);
        BarrageStyleAdapter barrageStyleAdapter = new BarrageStyleAdapter(barrageStyleResponseList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(barrageStyleAdapter);
        barrageStyleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BarrageStyleResponse barrageStyleResponse = (BarrageStyleResponse) adapter.getItem(position);
            for (BarrageStyleResponse barrageStyleResponse1 : barrageStyleResponseList) {
                if (barrageStyleResponse1.isCheck) {
                    barrageStyleResponse1.isCheck = false;
                }
            }
            barrageStyleResponse.isCheck = true;
            adapter.notifyDataSetChanged();
        });

        sendMessageRightRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindowListener != null) {
                    mPopupWindowListener.switchStyleLayoutBtnListener();
                    mCustomPopWindow.dissmiss();
                }
            }
        });

        sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "发送", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface BarrageStylePopupWindowListener {
        void switchStyleLayoutBtnListener();
    }
}
