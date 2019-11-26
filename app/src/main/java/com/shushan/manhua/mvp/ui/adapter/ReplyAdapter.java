package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.ReplyBean;

import java.util.List;

/**
 * 评论的回复adapter
 */
public class ReplyAdapter extends BaseQuickAdapter<ReplyBean, BaseViewHolder> {


    private int userA;

    public ReplyAdapter(@Nullable List<ReplyBean> data, int user_A) {
        super(R.layout.item_reply, data);
        userA = user_A;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplyBean item) {
        helper.setText(R.id.b_username_tv, item.user_B_name + ":");
        TextView replyHintTv = helper.getView(R.id.reply_hint_tv);
        TextView cUsernameTv = helper.getView(R.id.c_username_tv);
        //C==A ,就是B 回复A   否则就是B回复C
        if (item.user_C == userA) {
            replyHintTv.setVisibility(View.GONE);
            cUsernameTv.setVisibility(View.GONE);
        } else {
            replyHintTv.setVisibility(View.VISIBLE);
            cUsernameTv.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.c_username_tv, item.user_C_name + ":");
        helper.setText(R.id.b_content_tv, item.user_B_Content);

    }
}
