package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingCommendResponse;

import java.util.List;

/**
 * 阅读页面评论adapter
 */
public class ReadingCommentAdapter extends BaseQuickAdapter<ReadingCommendResponse, BaseViewHolder> {

    public ReadingCommentAdapter(@Nullable List<ReadingCommendResponse> data) {
        super(R.layout.item_reading_rommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingCommendResponse item) {
        helper.addOnClickListener(R.id.look_all_comment_tv);
    }
}
