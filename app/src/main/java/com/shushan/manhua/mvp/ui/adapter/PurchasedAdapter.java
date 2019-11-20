package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.PurchasedResponse;

import java.util.List;

/**
 * 已购漫画adapter
 */
public class PurchasedAdapter extends BaseQuickAdapter<PurchasedResponse, BaseViewHolder> {

    public PurchasedAdapter(@Nullable List<PurchasedResponse> data) {
        super(R.layout.item_purchased, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchasedResponse item) {

    }
}
