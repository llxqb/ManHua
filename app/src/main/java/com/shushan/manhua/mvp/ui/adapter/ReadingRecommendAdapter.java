package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingRecommendResponse;

import java.util.List;

/**
 * 阅读页面推荐 其它人还在看
 */
public class ReadingRecommendAdapter extends BaseQuickAdapter<ReadingRecommendResponse, BaseViewHolder> {

    public ReadingRecommendAdapter(@Nullable List<ReadingRecommendResponse> data) {
        super(R.layout.item_reading_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingRecommendResponse item) {

    }
}
