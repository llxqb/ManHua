package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.PurchasedResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 已购漫画adapter
 */
public class PurchasedAdapter extends BaseQuickAdapter<PurchasedResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public PurchasedAdapter(@Nullable List<PurchasedResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_purchased, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchasedResponse.DataBean item) {
        helper.addOnClickListener(R.id.item_purchased_layout);
        ImageView bookCoverIv = helper.getView(R.id.book_cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getOblong_cover(), bookCoverIv, Constant.LOADING_DEFAULT_2);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.buy_num_tv, item.getBuy_words() + " Chapter yang telah dibeli");
        helper.setText(R.id.all_chapter_tv, "Seluruhnya " + item.getWords() + " chapter");
    }
}
