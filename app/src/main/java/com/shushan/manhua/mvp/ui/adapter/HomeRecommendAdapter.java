package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DataUtils;

import java.util.List;

/**
 * 首页推荐adapter
 */
public class HomeRecommendAdapter extends BaseQuickAdapter<HomeResponse.HomeCommonBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public HomeRecommendAdapter(@Nullable List<HomeResponse.HomeCommonBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_home_recommend, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeResponse.HomeCommonBean item) {
        helper.addOnClickListener(R.id.item_home_recommend_layout);
        ImageView imageView = helper.getView(R.id.book_iv);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getOblong_cover(), imageView, 6, Constant.LOADING_DEFAULT_4);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.book_desc_tv, DataUtils.ListToString(item.getLabel()));//label 标签
    }
}
