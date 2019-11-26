package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DataUtils;

import java.util.List;

/**
 * 书架推荐adapter
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public RecommendAdapter(@Nullable List<RecommendResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_recommend, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendResponse.DataBean item) {
        helper.addOnClickListener(R.id.item_recommend_layout);
        ImageView imageView = helper.getView(R.id.book_iv);
        mImageLoaderHelper.displayImage(mContext, item.getSquare_cover(), imageView, Constant.LOADING_DEFAULT_4);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.book_desc_tv, DataUtils.ListToString(item.getLabel()));//label 标签
    }
}
