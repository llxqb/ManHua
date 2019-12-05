package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.ReceivedMessageResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import java.util.List;

/**
 * 回复我的评论adapter
 */
public class ReceivedMessageAdapter extends BaseQuickAdapter<ReceivedMessageResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public ReceivedMessageAdapter(@Nullable List<ReceivedMessageResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_received_message, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceivedMessageResponse.DataBean item) {
        CircleImageView avatarIv = helper.getView(R.id.avatar_iv);
        mImageLoaderHelper.displayImage(mContext, item.getHead_portrait(), avatarIv, Constant.LOADING_AVATOR);
        helper.setText(R.id.name_tv, item.getName());
        helper.setText(R.id.time_tv, DateUtil.getStrTime(item.getReview_time(), DateUtil.TIME_MMDD_HHMM));
        helper.setText(R.id.other_people_comment_tv, item.getContent());
        ReceivedMessageResponse.DataBean.CommentBean commentBean = item.getComment();
        helper.setText(R.id.mine_comment_tv, commentBean.getContent());
        ImageView bookCoverIv = helper.getView(R.id.book_cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getDetail_cover(), bookCoverIv, Constant.LOADING_DEFAULT_1);
        helper.setText(R.id.book_chapter_tv,item.getCatalogue_name());
        helper.setText(R.id.chapter_desc_tv,item.getBook_name());
    }
}
