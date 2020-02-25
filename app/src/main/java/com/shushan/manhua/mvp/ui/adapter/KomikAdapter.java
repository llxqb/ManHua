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

import java.util.List;

/**
 * 漫画adapter
 */
public class KomikAdapter extends BaseQuickAdapter<HomeResponse.HomeCommonBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public KomikAdapter(@Nullable List<HomeResponse.HomeCommonBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_komik, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeResponse.HomeCommonBean item) {
        helper.addOnClickListener(R.id.item_home_layout);
        ImageView coverIv = helper.getView(R.id.cover_iv);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getOblong_cover(), coverIv, 5,Constant.LOADING_DEFAULT_3);

        RecyclerView labelRecyclerView = helper.getView(R.id.label_recycler_view);
        HomeLabelAdapter mLabelAdapter = new HomeLabelAdapter(item.getLabel());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        labelRecyclerView.setLayoutManager(linearLayoutManager);
        labelRecyclerView.setAdapter(mLabelAdapter);
    }
}
