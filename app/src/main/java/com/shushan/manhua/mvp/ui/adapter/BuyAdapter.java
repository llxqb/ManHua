package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.MemberCenterResponse;

import java.util.List;

/**
 * 购买adapter
 */
public class BuyAdapter extends BaseQuickAdapter<MemberCenterResponse.VipinfoBean, BaseViewHolder> {

    public BuyAdapter(@Nullable List<MemberCenterResponse.VipinfoBean> data) {
        super(R.layout.item_buy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberCenterResponse.VipinfoBean item) {
        helper.addOnClickListener(R.id.item_buy_layout);
//        TextView originalPriceTv = helper.getView(R.id.original_price_tv);
//        originalPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
//        if (item.isCheck) {
//            helper.setTextColor(R.id.subscription_tv, mContext.getResources().getColor(R.color.subscription_check_color))
//                    .setTextColor(R.id.current_price_tv, mContext.getResources().getColor(R.color.buy_check_color))
//                    .setTextColor(R.id.original_price_tv, mContext.getResources().getColor(R.color.buy_check_color))
//                    .setTextColor(R.id.desc_tv, mContext.getResources().getColor(R.color.buy_check_color))
//                    .setBackgroundRes(R.id.item_buy_layout, R.drawable.buy_check_bg);
//        } else {
//            helper.setTextColor(R.id.subscription_tv, mContext.getResources().getColor(R.color.buy_no_check_color))
//                    .setTextColor(R.id.current_price_tv, mContext.getResources().getColor(R.color.buy_no_check_color))
//                    .setTextColor(R.id.original_price_tv, mContext.getResources().getColor(R.color.buy_no_check_color))
//                    .setTextColor(R.id.desc_tv, mContext.getResources().getColor(R.color.buy_no_check_color))
//                    .setBackgroundRes(R.id.item_buy_layout, R.drawable.buy_un_check_bg);
//        }
    }
}
