package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.activity.book.ReadBaseActivity;
import com.shushan.manhua.mvp.utils.DataUtils;

import java.util.List;

/**
 * 书架推荐adapter
 */
public class RecommendAdapter extends BaseQuickAdapter<RecommendBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public RecommendAdapter(@Nullable List<RecommendBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_recommend, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBean item) {
        helper.addOnClickListener(R.id.item_recommend_layout);
        ImageView imageView = helper.getView(R.id.book_iv);
        if (mContext instanceof ReadBaseActivity) {
            mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getOblong_cover(), imageView, 6, Constant.LOADING_DEFAULT_4);
        } else {
            mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getSquare_cover(), imageView, 6, Constant.LOADING_DEFAULT_4);
        }
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.book_desc_tv, DataUtils.ListToString(item.getLabel()));//label 标签
    }
}
