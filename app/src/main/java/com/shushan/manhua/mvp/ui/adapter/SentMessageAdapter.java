package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.SentMessageResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 我发出的评论adapter
 */
public class SentMessageAdapter extends BaseQuickAdapter<SentMessageResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public SentMessageAdapter(@Nullable List<SentMessageResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_sent_message, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SentMessageResponse.DataBean item) {
        helper.addOnClickListener(R.id.comment_content_tv).addOnClickListener(R.id.book_detail_ll).addOnClickListener(R.id.delete_tv);
        helper.setText(R.id.comment_content_tv, item.getContent());
        ImageView bookCoverIv = helper.getView(R.id.book_cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getDetail_cover(), bookCoverIv, Constant.LOADING_DEFAULT_2);
        helper.setText(R.id.book_title_tv, item.getCatalogue_name());
        helper.setText(R.id.book_desc_tv, item.getBook_name());
        helper.setText(R.id.time_tv, DateUtil.getStrTime(item.getComment_time(), DateUtil.TIME_MMDD_HHMM));
        helper.setText(R.id.suggest_tv, String.valueOf(item.getLike()));
    }
}
