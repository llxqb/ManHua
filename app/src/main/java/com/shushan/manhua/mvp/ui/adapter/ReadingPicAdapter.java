package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
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
//        ImageView picIv = helper.getView(R.id.resizableImageView);
//        mImageLoaderHelper.displayImage(mContext, item.getUrl(), picIv, R.mipmap.read_default);//Constant.LOADING_DEFAULT_4

        ImageView imgIv = helper.getView(R.id.img_iv);
        mImageLoaderHelper.displayImage(mContext, item.getUrl(), imgIv, R.mipmap.read_default);//Constant.LOADING_DEFAULT_4

//        Glide.with(mContext) .asBitmap().load(item.getUrl())
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        imgIv.setImageBitmap(resource);
//                    }
//                });

//        ImageView imgIv = helper.getView(R.id.img_iv);
//        RequestOptions options = new RequestOptions()
//                .override(item.getWidth(), item.getHeight())
//                .placeholder(Constant.LOADING_DEFAULT_4)
//                .error(Constant.LOADING_DEFAULT_4);
//        Glide.with(mContext)
//                .load(item.getUrl())
//                .apply(options)
//                .into(imgIv);

//        mImageLoaderHelper.displayAutoMatchImage(mContext, item.getUrl(), imgIv, R.mipmap.read_default);//Constant.LOADING_DEFAULT_4
//        imgIv.setMaxWidth(item.getWidth());
//        imgIv.setMaxHeight(item.getHeight());
//        imgIv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//        imgIv.setMinScale(1.0F);//最小显示比例
//        imgIv.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）

//下载图片保存到本地
//        Glide.with(mContext).asBitmap().load(item.getUrl())
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        imgIv.setImage(ImageSource.bitmap(resource));
//                    }
//                });

//        webView.getSettings().setSupportZoom(true);//缩放
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);//不显示控制器
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.loadUrl(item.getUrl());

    }
}
