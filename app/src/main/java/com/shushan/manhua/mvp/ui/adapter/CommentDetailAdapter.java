package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.activity.book.LookPhotoActivity;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import java.util.List;

/**
 * 阅读页面评论adapter
 */
public class CommentDetailAdapter extends BaseQuickAdapter<CommentDetailResponse.ReviewBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public CommentDetailAdapter(@Nullable List<CommentDetailResponse.ReviewBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_recomment_detail, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            int payload = (int) payloads.get(0);
            TextView suggestNumTv = holder.getView(R.id.suggest_num_tv);
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            suggestNumTv.setCompoundDrawables(drawable, null, null, null);
            suggestNumTv.setText(String.valueOf(++payload));
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentDetailResponse.ReviewBean item) {
        helper.addOnClickListener(R.id.suggest_num_tv).addOnClickListener(R.id.item_recommend_layout);
        CircleImageView circleImageView = helper.getView(R.id.avatar_iv);
        mImageLoaderHelper.displayImage(mContext,item.getUser_head_portrait(),circleImageView, Constant.LOADING_AVATOR);
        helper.setText(R.id.name_tv,item.getUser_name());
        helper.setText(R.id.time_tv, DateUtil.getStrTime(item.getReview_time(), DateUtil.TIME_MMDD_HHMM));
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
        helper.setText(R.id.content_tv,item.getContent());

        //发表评论的图片
        RecyclerView picRecyclerView = helper.getView(R.id.pic_recycler_view);
        PicAdapter picAdapter = new PicAdapter(item.getPics(), mImageLoaderHelper);
        picRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        picRecyclerView.setAdapter(picAdapter);
        picAdapter.setOnItemChildClickListener((adapter, view, position) -> LookPhotoActivity.start(mContext, (String) adapter.getItem(position)));
    }
}
