package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.NoticeMessageResponse;

import java.util.List;

/**
 * 通知消息adapter
 */
public class NoticeMessageAdapter extends BaseQuickAdapter<NoticeMessageResponse, BaseViewHolder> {

    public NoticeMessageAdapter(@Nullable List<NoticeMessageResponse> data) {
        super(R.layout.item_notice_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeMessageResponse item) {

    }
}
