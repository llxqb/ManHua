package com.shushan.manhua.help;

import android.content.Context;
import android.view.View;

/**
 * Created by li.liu on 2017/7/10.
 * GlideInterface
 */

public interface GlideInterface<T extends View> {
    /**
     * 展示图片
     */
    void displayImage(Context context, Object path, T imageView);
    /**
     * 展示图片
     * 自定义默认加载中图片
     */
    void displayImage(Context context, Object path, T imageView, int loadPic);
    /**
     * 设置圆角图片
     *用CircleImageView 类替代
     */
    void displayCircularImage(Context context, Object path, T imageView, int loadPic);
    /**
     * 设置圆角图片 , 自定义圆角大小
     *用CircleImageView 类替代
     */
    void displayRoundedCornerImage(Context context, Object path, T imageView, Integer size, int loadPic);

    /**
     * 自适应图片
     */
    void displayMatchImage(Context context, Object path, T imageView, int loadPic);

    T createImageView(Context context);
}
