package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.List;

/**
 * 漫画章节图片adapter
 */
public class ReadingPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public ReadingPicAdapter(@Nullable List<String> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_pic, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.item_reading_pic);
        ResizableImageView picIv = helper.getView(R.id.resizableImageView);
        if (!TextUtils.isEmpty(item)) {
            mImageLoaderHelper.displayImage(mContext, item, picIv, Constant.LOADING_DEFAULT_4);
        }

    }
}