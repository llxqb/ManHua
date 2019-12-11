package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

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
        GifImageView gifImageView = helper.getView(R.id.app_loading);
        ResizableImageView imgIv = helper.getView(R.id.img_iv);
//        gifImageView.setVisibility(View.VISIBLE);
//        imgIv.setVisibility(View.GONE);

        int width = SystemUtils.getScreenWidth(mContext);
        //计算放大比例
        float sy = width / item.getWidth();
        int height = (int) (item.getHeight() * sy);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.read_default)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.read_default)
                .override(width, height)
                .dontAnimate();

        Glide.with(mContext).load(item.getUrl()).apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("LogInterceptor", "e:" + e.toString());
//                        gifImageView.setVisibility(View.GONE);
//                        imgIv.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("LogInterceptor", "resource:" + resource.getMinimumWidth());
//                        gifImageView.setVisibility(View.GONE);
//                        imgIv.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(imgIv);
    }
}
