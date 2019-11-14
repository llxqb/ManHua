package com.shushan.manhua.help;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shushan.manhua.mvp.utils.TranTools;
import com.shushan.manhua.mvp.views.GlideRoundTransform;

import javax.inject.Inject;

/**
 * Created by li.liu on 2017/7/10.
 * ImageLoaderHelper
 */

public class ImageLoaderHelper extends GlideLoader {

    @Inject
    public ImageLoaderHelper(final Context ctx) {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int loadPic) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .skipMemoryCache(true)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }


    /**
     * 设置圆角图片
     */
    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView, int loadPic) {
        RequestOptions options;
        //只是绘制圆角  false表示圆角
        GlideRoundTransform transformation = new GlideRoundTransform(context, TranTools.dip2px(context, 10));
        transformation.setExceptCorner(false, false, false, false);
        if (loadPic == 0) {
            options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transform(transformation)
                    .skipMemoryCache(true)
                    .dontAnimate();
        } else {
            options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transform(transformation)
                    .error(loadPic)
                    .skipMemoryCache(true)
                    .placeholder(loadPic)
                    .dontAnimate();
        }
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size, int loadPic) {
        //只是绘制圆角  false表示圆角
        GlideRoundTransform transformation = new GlideRoundTransform(context, TranTools.dip2px(context, size));
        transformation.setExceptCorner(false, false, false, false);
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transform(transformation)
                .error(loadPic)
                .skipMemoryCache(true)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    /**
     * 设置自适应图片
     */
    @Override
    public void displayMatchImage(Context context, Object path, ImageView imageView, int loadPic) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .skipMemoryCache(true)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }


}
