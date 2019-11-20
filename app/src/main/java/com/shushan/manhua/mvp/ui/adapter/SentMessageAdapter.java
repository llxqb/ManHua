package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SentMessageResponse;

import java.util.List;

/**
 * 我发出的评论adapter
 */
public class SentMessageAdapter extends BaseQuickAdapter<SentMessageResponse, BaseViewHolder> {

    public SentMessageAdapter(@Nullable List<SentMessageResponse> data) {
        super(R.layout.item_sent_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SentMessageResponse item) {

    }
}
