package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BarrageStyleResponse;

import java.util.List;

/**
 * 弹幕样式adapter
 */
public class BarrageStyleAdapter extends BaseQuickAdapter<BarrageStyleResponse, BaseViewHolder> {

    public BarrageStyleAdapter(@Nullable List<BarrageStyleResponse> data) {
        super(R.layout.item_barrage_style, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BarrageStyleResponse item) {
        helper.addOnClickListener(R.id.item_barrage_style_layout);
        if (item.isCheck) {
            helper.setVisible(R.id.choose_style_iv, true);
            helper.setBackgroundRes(R.id.item_barrage_style_layout, R.drawable.stroke_barrage_style_bg);
        } else {
            helper.setVisible(R.id.choose_style_iv, false);
            helper.setBackgroundRes(R.id.item_barrage_style_layout, R.color.color_transparent);
        }
        helper.setImageResource(R.id.style_bg_iv, item.styleIcon);

    }
}
