package com.shushan.manhua.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        helper.addOnClickListener(R.id.img_iv);
        ImageView imageView = helper.getView(R.id.img_iv);
        FrameLayout gifFrameLayout = helper.getView(R.id.app_loading_fl);
        gifFrameLayout.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
//        RequestOptions options = new RequestOptions()
////                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .error(R.mipmap.read_default)
//                .override(item.getWidth(), item.getHeight())
//                .dontAnimate();
//        Glide.with(mContext).load(item.getUrl()).apply(options).listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                LogUtils.e("加载失败 errorMsg:" + (e != null ? e.getMessage() : "null"));
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                LogUtils.e("成功  Drawable Name:" + resource.getClass().getCanonicalName() + "  resource:" + resource.toString());
//                //首先设置imageView的ScaleType属性为ScaleType.FIT_XY，让图片不按比例缩放，把图片塞满整个View。
//                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//                //得到当前imageView的宽度（我设置的是屏幕宽度），获取到imageView与图片宽的比例，然后通过这个比例去设置imageView的高
//                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//                float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                imageView.setLayoutParams(params);
//                return false;
//            }
//        }).into(imageView);

        Picasso.with(mContext)
                .load(item.getUrl())
                .error(R.mipmap.read_default)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        gifFrameLayout.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });


//        Glide.with(mContext).load(item.getUrl())
//                .downloadOnly(new SimpleTarget<File>() {
//                    @Override
//                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                        // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                        BitmapFactory.Options opts = new BitmapFactory.Options();            //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
//                        opts.inJustDecodeBounds = true;
//                        Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath(), opts);
//                        // 显示处理好的Bitmap图片
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });


//        FrameLayout gifFrameLayout = helper.getView(R.id.app_loading_fl);
//        SubsamplingScaleImageView imageView = helper.getView(R.id.img_iv);
//        gifFrameLayout.setVisibility(View.VISIBLE);
//        imageView.setVisibility(View.GONE);
////        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//        //源码为除以2，我采取除以3
//        float averageDpi = (metrics.xdpi + metrics.ydpi) / 3.0F;
//        imageView.setMinimumTileDpi((int) averageDpi);
//        imageView.setZoomEnabled(false);//设置不可伸缩
//        //下载图片保存到本地  .thumbnail(Glide.with(mContext).load(R.mipmap.app_load))
//        Glide.with(mContext).load(item.getUrl()).downloadOnly(new SimpleTarget<File>() {
//            @Override
//            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                imageView.setVisibility(View.VISIBLE);
//                gifFrameLayout.setVisibility(View.GONE);
//                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)),new ImageViewState(1.0F, new PointF(0, 0), 0));//, new ImageViewState(2.0F, new PointF(0, 0), 0)
//            }
//        });


    }
}
