package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.CommentDetailResponse;

import java.util.List;

/**
 * 阅读页面评论adapter
 */
public class CommentDetailAdapter extends BaseQuickAdapter<CommentDetailResponse.ReviewBean, BaseViewHolder> {

    public CommentDetailAdapter(@Nullable List<CommentDetailResponse.ReviewBean> data) {
        super(R.layout.item_recomment_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentDetailResponse.ReviewBean item) {
        helper.addOnClickListener(R.id.comment_iv).addOnClickListener(R.id.item_recommend_layout);

    }
}
