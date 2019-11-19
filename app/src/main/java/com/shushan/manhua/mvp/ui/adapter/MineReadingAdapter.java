package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.MineReadingResponse;

import java.util.List;

/**
 * 阅读偏好adapter
 */
public class MineReadingAdapter extends BaseQuickAdapter<MineReadingResponse, BaseViewHolder> {

    public MineReadingAdapter(@Nullable List<MineReadingResponse> data) {
        super(R.layout.item_mine_reading, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineReadingResponse item) {
        helper.addOnClickListener(R.id.mine_reading_layout);
        helper.setImageResource(R.id.cover_iv, item.cover).setText(R.id.name_tv, item.name);

    }
}
