package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BuyBeansResponse;

import java.util.List;

/**
 * 购买漫豆adapter
 */
public class BuyBeansAdapter extends BaseQuickAdapter<BuyBeansResponse, BaseViewHolder> {

    public BuyBeansAdapter(@Nullable List<BuyBeansResponse> data) {
        super(R.layout.item_buy_beans, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuyBeansResponse item) {
        helper.addOnClickListener(R.id.item_buy_beans_layout);
        if (item.isCheck) {
            helper.setBackgroundRes(R.id.item_buy_beans_layout, R.mipmap.buy_item_orange_bg);
        } else {
            helper.setBackgroundRes(R.id.item_buy_beans_layout, R.mipmap.buy_item_gray_bg);
        }

    }
}
