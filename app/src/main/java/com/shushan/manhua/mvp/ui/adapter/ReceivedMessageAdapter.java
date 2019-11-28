package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.MessageResponse;

import java.util.List;

/**
 * 回复我的评论adapter
 */
public class ReceivedMessageAdapter extends BaseQuickAdapter<MessageResponse.DataBean, BaseViewHolder> {

    public ReceivedMessageAdapter(@Nullable List<MessageResponse.DataBean> data) {
        super(R.layout.item_received_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse.DataBean item) {

    }
}
