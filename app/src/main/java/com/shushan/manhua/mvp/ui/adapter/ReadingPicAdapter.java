package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.List;

/**
 * 漫画章节图片adapter
 */
public class ReadingPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    private View mViewGroup;
    LinearLayout itemReadingPic;

    public ReadingPicAdapter(@Nullable List<String> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_pic, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        itemReadingPic = holder.getView(R.id.item_reading_pic);
//        addView();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.item_reading_pic);
        ResizableImageView picIv = helper.getView(R.id.resizableImageView);
        if (!TextUtils.isEmpty(item)) {
            mImageLoaderHelper.displayImage(mContext, item, picIv, Constant.LOADING_DEFAULT_4);
        }
    }

    /**
     * 增加弹幕view
     */
//    private void addView() {
//        for (int i = 0; i < 3; i++) {
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            View view = inflater.inflate(R.layout.text_view, null);
//            TextView textView = view.findViewById(R.id.text_tv);
//            textView.setText("我是弹幕我是弹幕");
//            layoutParams.setMargins(200, 200 + 50 * i, 0, 0);
//            textView.setLayoutParams(layoutParams);
//            itemReadingPic.addView(view);  // 调用一个参数的addView方法
//        }
//    }
}
