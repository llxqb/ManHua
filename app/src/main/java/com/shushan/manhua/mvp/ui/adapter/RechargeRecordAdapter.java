package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.RechargeRecordResponse;

import java.util.List;

/**
 * 充值记录adapter
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordResponse, BaseViewHolder> {

    public RechargeRecordAdapter(@Nullable List<RechargeRecordResponse> data) {
        super(R.layout.item_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordResponse item) {
    }
}
