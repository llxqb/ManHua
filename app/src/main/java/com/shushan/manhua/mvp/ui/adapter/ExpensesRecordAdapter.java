package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ExpensesRecordResponse;

import java.util.List;

/**
 * 消费记录adapter
 */
public class ExpensesRecordAdapter extends BaseQuickAdapter<ExpensesRecordResponse, BaseViewHolder> {

    public ExpensesRecordAdapter(@Nullable List<ExpensesRecordResponse> data) {
        super(R.layout.item_expenses_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpensesRecordResponse item) {
    }
}
