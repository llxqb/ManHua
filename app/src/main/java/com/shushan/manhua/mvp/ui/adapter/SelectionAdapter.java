package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 书籍选集adapter
 */
public class SelectionAdapter extends BaseQuickAdapter<SelectionResponse.AnthologyBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private int mVipCost;
    private int mIsVip;
    private boolean mIsBook;//true 小说  false 漫画

    public SelectionAdapter(@Nullable List<SelectionResponse.AnthologyBean> data, ImageLoaderHelper imageLoaderHelper, boolean isBook) {
        super(R.layout.item_selection, data);
        mImageLoaderHelper = imageLoaderHelper;
        mIsBook = isBook;
    }

    public void setVipCost(int isVip, int vipCost) {
        mIsVip = isVip;
        mVipCost = vipCost;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            int payload = (int) payloads.get(0);
//            LogUtils.e("payload:" + payload);
            TextView suggestNumTv = holder.getView(R.id.support_tv);
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_purple);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            suggestNumTv.setCompoundDrawables(drawable, null, null, null);
            suggestNumTv.setText(String.valueOf(++payload));
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, SelectionResponse.AnthologyBean item) {
        helper.addOnClickListener(R.id.support_tv).addOnClickListener(R.id.item_selection_layout);
        ConstraintLayout comicLayout = helper.getView(R.id.comic_layout);
        TextView bookChapterTv = helper.getView(R.id.book_chapter_tv);
        if (!mIsBook) {//漫画
            comicLayout.setVisibility(View.VISIBLE);
            bookChapterTv.setVisibility(View.GONE);
            ImageView coverIv = helper.getView(R.id.cover_iv);
            mImageLoaderHelper.displayImage(mContext, item.getCatalogue_cover(), coverIv, Constant.LOADING_DEFAULT_2);
            helper.setText(R.id.title_tv, item.getCatalogue_name());
            helper.setText(R.id.date_tv, DateUtil.getStrTime(item.getCreate_time(), DateUtil.TIME_YYMMDD));
            helper.setText(R.id.support_tv, String.valueOf(item.getLike()));

            ImageView lockIv = helper.getView(R.id.lock_iv);
            if (item.getType() == 0) {//1收费0免费
                lockIv.setVisibility(View.GONE);
            } else {
                if (mIsVip == 1 && mVipCost == 0) {
                    lockIv.setVisibility(View.GONE);
                } else {
                    lockIv.setVisibility(View.VISIBLE);
                }
            }
            TextView suggestNumTv = helper.getView(R.id.support_tv);
            if (item.getIs_like() == 0) {//未点赞
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                suggestNumTv.setCompoundDrawables(drawable, null, null, null);
            } else {//点赞
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.title_praise_purple);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                suggestNumTv.setCompoundDrawables(drawable, null, null, null);
            }
        } else {//小说
            comicLayout.setVisibility(View.GONE);
            bookChapterTv.setVisibility(View.VISIBLE);
            helper.setText(R.id.book_chapter_tv, item.getCatalogue_name());
            if (item.getType() == 0) {//1收费0免费
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.bg_app_color_360);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                bookChapterTv.setCompoundDrawables(drawable, null, null, null);
            } else {
                if (mIsVip == 1 && mVipCost == 0) {
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.bg_app_color_360);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    bookChapterTv.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = mContext.getResources().getDrawable(R.mipmap.lock_book);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    bookChapterTv.setCompoundDrawables(drawable, null, null, null);
                }
            }
        }
    }
}
