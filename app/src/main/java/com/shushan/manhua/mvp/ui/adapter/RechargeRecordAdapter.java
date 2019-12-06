package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.RechargeRecordResponse;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 充值记录adapter
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordResponse.DataBean, BaseViewHolder> {

    public RechargeRecordAdapter(@Nullable List<RechargeRecordResponse.DataBean> data) {
        super(R.layout.item_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordResponse.DataBean item) {
        helper.setText(R.id.date_tv, DateUtil.getStrTime(item.getCreate_time(), "yyyy-MM-dd HH:mm"));
        helper.setText(R.id.beans_num_tv, "+" + item.getCount());
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state0));
                break;
            case 1:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state1));
                break;
            case 2:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state2));
                break;
            case 3:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state3));
                break;
            case 4:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state4));
                break;
            case 5:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state5));
                break;
            case 6:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state6));
                break;
            case 7:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state7));
                break;
            case 8:
                helper.setText(R.id.title_tv, mContext.getString(R.string.ExpensesRecordAdapter_state8));
                break;
        }
    }
}
