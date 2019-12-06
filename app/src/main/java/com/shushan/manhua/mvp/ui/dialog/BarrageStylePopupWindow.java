package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BarrageStyleResponse;
import com.shushan.manhua.entity.user.BuProcessor;
import com.shushan.manhua.entity.user.User;
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
    private BuProcessor mBuProcessor;

    public BarrageStylePopupWindow(Activity context, List<BarrageStyleResponse> barrageStyleResponseList, BuProcessor buProcessor, BarrageStylePopupWindowListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        mBuProcessor = buProcessor;
        this.barrageStyleResponseList = barrageStyleResponseList;
    }

    public void updateData(List<BarrageStyleResponse> barrageStyleResponseList, int barrageStyle) {
        for (int i = 0; i < barrageStyleResponseList.size(); i++) {
            BarrageStyleResponse barrageStyleResponse = barrageStyleResponseList.get(i);
            if (barrageStyleResponse.isCheck) {
                barrageStyleResponse.isCheck = false;
            }
            if (barrageStyle == i) {
                barrageStyleResponse.isCheck = true;//默认选中
            }
        }
        barrageStyleAdapter.setNewData(barrageStyleResponseList);
    }


    public void setDismiss() {
        if (mCustomPopWindow != null) {
            mCustomPopWindow.getPopupWindow().dismiss();
        }
    }

    public void initPopWindow(View view) {
        if (mCustomPopWindow == null) {
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
    }


    private BarrageStyleAdapter barrageStyleAdapter;

    private void handlePopListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        RelativeLayout sendMessageRl = contentView.findViewById(R.id.send_message_rl);
        TextView messageTv = contentView.findViewById(R.id.message_tv);
        ImageView sendMessageRightIv = contentView.findViewById(R.id.send_message_right_iv);
        barrageStyleAdapter = new BarrageStyleAdapter(barrageStyleResponseList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(barrageStyleAdapter);
        barrageStyleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            User mUser = mBuProcessor.getUser();
            BarrageStyleResponse barrageStyleResponse = (BarrageStyleResponse) adapter.getItem(position);
            for (BarrageStyleResponse barrageStyleResponse1 : barrageStyleResponseList) {
                if (barrageStyleResponse1.isCheck) {
                    barrageStyleResponse1.isCheck = false;
                }
            }
            //vip 提示开通vip   漫豆兑换提示消费漫豆
            if (barrageStyleResponse != null) {
                if (barrageStyleResponse.isBuy) {//是否购买过 且 在有效期期内
                    barrageStyleResponse.isCheck = true;
                    adapter.notifyDataSetChanged();
                    if (mPopupWindowListener != null) {
                        mPopupWindowListener.selectBarrageStyleBtnListener(position);
                    }
                } else {
                    if (barrageStyleResponse.styleType == 0) {//0 免费  1 ： vip兑换   2 ：漫豆兑换
                        barrageStyleResponse.isCheck = true;
                        adapter.notifyDataSetChanged();
                    } else if (barrageStyleResponse.styleType == 1) {//vip兑换
                        if (mUser.vip == 0) {
                            if (mPopupWindowListener != null) {
                                mPopupWindowListener.hintOpenVipBtnListener(position);
                            }
                        } else {
                            barrageStyleResponse.isCheck = true;
                            adapter.notifyDataSetChanged();
                            if (mPopupWindowListener != null) {
                                mPopupWindowListener.selectBarrageStyleBtnListener(position);
                            }
                        }
                    } else {//漫豆兑换
                        if (mPopupWindowListener != null) {
                            mPopupWindowListener.showBeansExchangeBtnListener(position);
                        }
                    }
                }
            }
        });

        sendMessageRl.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.showPublishBarrageBtnListener();
                mCustomPopWindow.dissmiss();
            }
        });
    }


    public interface BarrageStylePopupWindowListener {
        void hintOpenVipBtnListener(int barrageStyle);//成为VIP 弹框

        void showBeansExchangeBtnListener(int barrageStyle);//显示漫豆兑换弹幕弹框

        //        void hintBeansExchangeBtnListener();//隐藏
//
        void selectBarrageStyleBtnListener(int barrageStyle);//显示选择的弹幕样式

        void showPublishBarrageBtnListener();//发送弹幕
    }

}
