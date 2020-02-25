package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.BookClassificationResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 漫画adapter
 */
public class BookClassificationAdapter extends BaseQuickAdapter<BookClassificationResponse.ListBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public BookClassificationAdapter(@Nullable List<BookClassificationResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_book_classification, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookClassificationResponse.ListBean item) {
        helper.addOnClickListener(R.id.item_book_classification_layout);
        ImageView coverIv = helper.getView(R.id.cover_iv);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getOblong_cover(), coverIv, 4, Constant.LOADING_DEFAULT_3);

        RecyclerView labelRecyclerView = helper.getView(R.id.label_recycler_view);
        HomeLabelAdapter mLabelAdapter = new HomeLabelAdapter(item.getLabel());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        labelRecyclerView.setLayoutManager(linearLayoutManager);
        labelRecyclerView.setAdapter(mLabelAdapter);

        TextView supportTv = helper.getView(R.id.support_tv);
        TextView commentTv = helper.getView(R.id.comment_tv);
        supportTv.setText(String.valueOf(item.getLike()));
        commentTv.setText(String.valueOf(item.getComment_count()));
        if (item.getIs_like() == 0) {//是否已经点赞 0 未点赞
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            supportTv.setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            supportTv.setCompoundDrawables(drawable, null, null, null);
        }
    }
}
