package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;

import java.util.List;

/**
 * 书籍标签adapter
 */
public class RankingLabelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RankingLabelAdapter(@Nullable List<String> data) {
        super(R.layout.item_label, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.label_tv, item);
    }
}