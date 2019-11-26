package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.ReplyBean;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读页面评论adapter
 */
public class ReadingCommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public ReadingCommentAdapter(@Nullable List<CommentBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_rommend, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.addOnClickListener(R.id.comment_ll);
        CircleImageView circleImageView = helper.getView(R.id.avatar_iv);
        mImageLoaderHelper.displayImage(mContext, item.getHead_portrait(), circleImageView);
        helper.setText(R.id.name_tv, item.getName());
        helper.setText(R.id.time_tv, DateUtil.getStrTime(item.getComment_time(), DateUtil.TIME_MMDD_HHMM));
        helper.setText(R.id.suggest_num_tv, String.valueOf(item.getLike()));

        TextView suggestNumTv = helper.getView(R.id.suggest_num_tv);
        if (item.getIs_like() == 0) {//未点赞
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            suggestNumTv.setCompoundDrawables(drawable, null, null, null);
        } else {//点赞
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            suggestNumTv.setCompoundDrawables(drawable, null, null, null);
        }

        helper.setText(R.id.content_tv, item.getContent());

        //发表评论的图片
        RecyclerView picRecyclerView = helper.getView(R.id.pic_recycler_view);
        if (item.getPics().isEmpty()) {
            picRecyclerView.setVisibility(View.GONE);
        } else {
            picRecyclerView.setVisibility(View.VISIBLE);
            PicAdapter picAdapter = new PicAdapter(item.getPics(), mImageLoaderHelper);
            picRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            picRecyclerView.setAdapter(picAdapter);
        }

        List<ReplyBean> replyBeanList = new ArrayList<>();
        for (CommentBean.ReviewBean reviewBean : item.getReview()) {
            ReplyBean replyBean = new ReplyBean(reviewBean.getUser_id(), reviewBean.getUser_name(), reviewBean.getBe_user_id(), reviewBean.getBe_user_name(), reviewBean.getContent());
            replyBeanList.add(replyBean);
        }
        //回复评论的recycleView
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        //user_id ->user_id->be_user_id
        //A  ->  B -->C    A发出评论 B 评论A ,C评论B
        ReplyAdapter replyAdapter = new ReplyAdapter(replyBeanList, item.getUser_id());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(replyAdapter);

        //Lihat semua 10 balasan 查看全部10条回复
        helper.setText(R.id.look_all_comment_tv, "Lihat semua " + item.getReview_count() + " balasan");

    }
}
