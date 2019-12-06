package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.List;

/**
 * 漫画章节图片adapter
 */
public class ReadingPicAdapter extends BaseQuickAdapter<ReadingInfoResponse.CatalogueBean.CatalogueContentBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    private View mView;
    LinearLayout itemReadingPic;

    public ReadingPicAdapter(@Nullable List<ReadingInfoResponse.CatalogueBean.CatalogueContentBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_pic, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

//    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//        mView = super.getItemView(layoutResId, parent);
//        return mView;
//    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingInfoResponse.CatalogueBean.CatalogueContentBean item) {
        helper.addOnClickListener(R.id.item_reading_pic);
        ResizableImageView picIv = helper.getView(R.id.resizableImageView);
        if (!TextUtils.isEmpty(item.getUrl())) {
            mImageLoaderHelper.displayImage(mContext, item.getUrl(), picIv, Constant.LOADING_DEFAULT_4);//Constant.LOADING_DEFAULT_4
        }
    }

    /**
     * 增加弹幕view
     */
//    public void addTvView() {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(R.layout.text_view, null);
//        TextView textView = view.findViewById(R.id.text_tv);
////            TextView textView = new TextView(this);
//        textView.setText("我是弹幕我是弹幕");
////            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(200, 200, 0, 0);
//        view.setLayoutParams(layoutParams);
//        mView.addView(view);  // 调用一个参数的addView方法
//    }

}
