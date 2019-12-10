package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 书架adapter
 */
public class ReadingChapterAdapter extends BaseQuickAdapter<SelectionResponse.AnthologyBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private int mVipCost;
    private int mIsVip;

    public void setVipCost(int isVip, int vipCost) {
        mIsVip = isVip;
        mVipCost = vipCost;
    }

    public ReadingChapterAdapter(@Nullable List<SelectionResponse.AnthologyBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_chapter, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectionResponse.AnthologyBean item) {
//        helper.setText(R.id.title_tv,item.title);
        helper.addOnClickListener(R.id.item_reading_chapter_layout);
        ImageView coverIv = helper.getView(R.id.cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getCatalogue_cover(), coverIv, Constant.LOADING_DEFAULT_2);
        helper.setText(R.id.title_tv, item.getCatalogue_name());
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
    }
}
