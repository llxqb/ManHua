package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ProfitResponse;

import java.util.List;

/**
 * 会员权益adapter
 */
public class ProfitAdapter extends BaseQuickAdapter<ProfitResponse, BaseViewHolder> {

    public ProfitAdapter(@Nullable List<ProfitResponse> data) {
        super(R.layout.item_profit, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProfitResponse item) {
        helper.addOnClickListener(R.id.item_profit_layout);
        helper.setText(R.id.name_tv, item.name).setImageResource(R.id.cover_iv, item.cover);
    }
}
