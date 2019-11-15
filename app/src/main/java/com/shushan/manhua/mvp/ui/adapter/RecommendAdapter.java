package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.RecommendResponse;

import java.util.List;

/**
 * 书架推荐adapter
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendResponse, BaseViewHolder> {

    public RecommendAdapter(@Nullable List<RecommendResponse> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendResponse item) {
        helper.addOnClickListener(R.id.item_recommend_layout);
    }
}
