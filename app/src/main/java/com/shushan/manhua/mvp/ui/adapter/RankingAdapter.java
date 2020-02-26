package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.RankingResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 漫画排行榜dapter
 */
public class RankingAdapter extends BaseQuickAdapter<RankingResponse.ListBean, BaseViewHolder> {
    private ImageLoaderHelper mImageLoaderHelper;

    public RankingAdapter(@Nullable List<RankingResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_ranking, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RankingResponse.ListBean item) {
        helper.addOnClickListener(R.id.item_ranking_layout);
        ImageView coverIv = helper.getView(R.id.cover_iv);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.author_tv, "Penulis：" + item.getAuthor());
        helper.setText(R.id.update_to_chapter_tv, "Perbarui ke " + item.getLastBookCatalogue());
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getOblong_cover(), coverIv, 5, Constant.LOADING_DEFAULT_4);
        RecyclerView labelRecyclerView = helper.getView(R.id.label_recycler_view);
        RankingLabelAdapter mRankingLabelAdapter = new RankingLabelAdapter(item.getLabel());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        labelRecyclerView.setLayoutManager(linearLayoutManager);
        labelRecyclerView.setAdapter(mRankingLabelAdapter);
    }
}
