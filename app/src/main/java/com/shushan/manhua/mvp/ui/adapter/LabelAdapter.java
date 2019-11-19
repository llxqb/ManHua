package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.LabelResponse;

import java.util.List;

/**
 * 书籍标签adapter
 */
public class LabelAdapter extends BaseQuickAdapter<LabelResponse, BaseViewHolder> {

    public LabelAdapter(@Nullable List<LabelResponse> data) {
        super(R.layout.item_book_label, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LabelResponse item) {

    }
}
