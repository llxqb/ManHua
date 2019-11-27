package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 图片九宫格adapter
 */
public class PicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public PicAdapter(@Nullable List<String> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_pic, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView picIv = helper.getView(R.id.pic_iv);
        if(!TextUtils.isEmpty(item)){
            mImageLoaderHelper.displayImage(mContext, item, picIv, Constant.LOADING_DEFAULT_1);
        }

    }
}
