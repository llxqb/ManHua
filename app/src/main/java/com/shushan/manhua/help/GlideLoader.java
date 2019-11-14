package com.shushan.manhua.help;

import android.content.Context;
import android.widget.ImageView;

import com.shushan.manhua.help.GlideInterface;

/**
 * Created by lei.he on 2017/7/10.
 * GlideLoader
 */

public abstract class GlideLoader implements GlideInterface<ImageView> {
    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }
}
