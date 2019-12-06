package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ExpensesRecordResponse;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 消费记录adapter
 */
public class ExpensesRecordAdapter extends BaseQuickAdapter<ExpensesRecordResponse.DataBean, BaseViewHolder> {

    public ExpensesRecordAdapter(@Nullable List<ExpensesRecordResponse.DataBean> data) {
        super(R.layout.item_expenses_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpensesRecordResponse.DataBean item) {
        //消费 Biaya penggunaan
        //充值 Isis Ulang
        //充值赠送 Hadiah Isi ulang
        //签到领取  tanda tangan smapai diambil
        //任务获取 ambil tugas
        //会员领取 ambil anggota
        //会员赠送 hadiah anggota
        //发送弹幕 kirim komentar langsung
        //兑换弹幕样式 tukar model komentar langsung
        helper.setText(R.id.title_tv, item.getBook_name());//0消费 1充值 2充值赠送 3签到领取 4任务获取 5会员领取 6会员赠送 7发送弹幕 8兑换弹幕样式
        helper.setText(R.id.beans_num_tv, "-" + item.getCount());
        helper.setText(R.id.date_tv, DateUtil.getStrTime(item.getCreate_time(), "yyyy-MM-dd HH:mm"));

        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state0));
                break;
            case 1:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state1));
                break;
            case 2:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state2));
                break;
            case 3:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state3));
                break;
            case 4:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state4));
                break;
            case 5:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state5));
                break;
            case 6:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state6));
                break;
            case 7:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state7));
                break;
            case 8:
                helper.setText(R.id.book_name_tv, mContext.getString(R.string.ExpensesRecordAdapter_state8));
                break;
        }

    }
}
