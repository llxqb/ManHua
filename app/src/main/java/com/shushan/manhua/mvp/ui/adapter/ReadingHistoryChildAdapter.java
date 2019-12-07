package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 浏览历史adapter
 */
public class ReadingHistoryChildAdapter extends BaseQuickAdapter<ReadingHistoryResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public ReadingHistoryChildAdapter(@Nullable List<ReadingHistoryResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_reading_history_child, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingHistoryResponse.DataBean item) {
        helper.addOnClickListener(R.id.check_iv);
        ImageView checkIv = helper.getView(R.id.check_iv);
        if (item.isEditState) {
            checkIv.setVisibility(View.VISIBLE);
        } else {
            checkIv.setVisibility(View.GONE);
        }
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_unchoose);
        }
        ImageView coverIv = helper.getView(R.id.cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getOblong_cover(), coverIv, Constant.LOADING_DEFAULT_2);
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.read_chapter_tv, "Baca untuk:" + item.getCatalogue_name());
        helper.setText(R.id.remaining_chapter_tv, "Bab " + item.getResidue_words() + ": Belum Dibaca");
        helper.setText(R.id.date_tv, DateUtil.getStrTime(item.getCreate_time(), "MM-dd"));
    }
}
