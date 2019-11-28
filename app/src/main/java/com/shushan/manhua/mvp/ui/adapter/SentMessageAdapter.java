package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.MessageResponse;

import java.util.List;

/**
 * 我发出的评论adapter
 */
public class SentMessageAdapter extends BaseQuickAdapter<MessageResponse.DataBean, BaseViewHolder> {

    public SentMessageAdapter(@Nullable List<MessageResponse.DataBean> data) {
        super(R.layout.item_sent_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse.DataBean item) {

    }
}
