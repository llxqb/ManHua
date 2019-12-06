package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
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
    }
}
