package com.shushan.manhua.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.SystemUtils;

import java.io.File;
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


    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, ReadingInfoResponse.CatalogueBean.CatalogueContentBean item) {
        helper.addOnClickListener(R.id.item_reading_pic);

        int width = SystemUtils.getScreenWidth(mContext);
        //计算放大比例
        float sy = width / item.getWidth();
        int height = (int) (item.getHeight() * sy);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.read_default)
                .skipMemoryCache(true)
                .override(width, height)
                .dontAnimate();
        FrameLayout gifFrameLayout = helper.getView(R.id.app_loading_fl);
        SubsamplingScaleImageView imageView = helper.getView(R.id.img_iv);
        gifFrameLayout.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
//        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setZoomEnabled(false);//设置不可伸缩
        //下载图片保存到本地  .thumbnail(Glide.with(mContext).load(R.mipmap.app_load))
        Glide.with(mContext).load(item.getUrl()).apply(options).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                imageView.setVisibility(View.VISIBLE);
                gifFrameLayout.setVisibility(View.GONE);
                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)));//, new ImageViewState(2.0F, new PointF(0, 0), 0)
            }
        });
    }


}
