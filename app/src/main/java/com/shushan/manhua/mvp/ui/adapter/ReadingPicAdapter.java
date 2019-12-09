package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 漫画章节图片adapter
 */
public class ReadingPicAdapter extends BaseQuickAdapter<ReadingInfoResponse.CatalogueBean.CatalogueContentBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public ReadingPicAdapter(@Nullable List<ReadingInfoResponse.CatalogueBean.CatalogueContentBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_pic, data);
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, ReadingInfoResponse.CatalogueBean.CatalogueContentBean item) {
        helper.addOnClickListener(R.id.item_reading_pic);
        ImageView picIv = helper.getView(R.id.resizableImageView);
        mImageLoaderHelper.displayAutoMatchImage(mContext, item.getUrl(), picIv, Constant.LOADING_DEFAULT_4);//Constant.LOADING_DEFAULT_4
    }
}
