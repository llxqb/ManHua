package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;

import java.util.List;

/**
 * 浏览历史adapter
 */
public class ReadingHistoryChildAdapter extends BaseQuickAdapter<ReadingHistoryResponse.ReadingHistoryChildBean, BaseViewHolder> {

    public ReadingHistoryChildAdapter(@Nullable List<ReadingHistoryResponse.ReadingHistoryChildBean> data) {
        super(R.layout.item_reading_history_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingHistoryResponse.ReadingHistoryChildBean item) {
        helper.addOnClickListener(R.id.check_iv);
        ImageView checkIv = helper.getView(R.id.check_iv);
        if (item.isEditState) {
            checkIv.setVisibility(View.VISIBLE);
        } else {
            checkIv.setVisibility(View.GONE);
        }
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_unchoose);
        }
    }
}
