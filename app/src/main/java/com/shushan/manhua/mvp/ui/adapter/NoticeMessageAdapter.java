package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.MessageResponse;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 通知消息adapter
 */
public class NoticeMessageAdapter extends BaseQuickAdapter<MessageResponse.DataBean, BaseViewHolder> {

    public NoticeMessageAdapter(@Nullable List<MessageResponse.DataBean> data) {
        super(R.layout.item_notice_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResponse.DataBean item) {
        helper.setText(R.id.date_tv, DateUtil.getStrTime(item.getSend_time(), "yyyy-MM-dd HH:mm"));
        helper.setText(R.id.message_content_tv, item.getContent());
    }
}
