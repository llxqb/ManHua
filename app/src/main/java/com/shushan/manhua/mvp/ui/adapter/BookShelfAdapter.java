package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 书架adapter
 */
public class BookShelfAdapter extends BaseQuickAdapter<BookShelfResponse.BookrackBean, BaseViewHolder> {
    private ImageLoaderHelper mImageLoaderHelper;

    public BookShelfAdapter(@Nullable List<BookShelfResponse.BookrackBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_bookshelf, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookShelfResponse.BookrackBean item) {
        helper.addOnClickListener(R.id.item_bookshelf_layout);
        ImageView bookIv = helper.getView(R.id.book_iv);
        //设置圆角图片
        mImageLoaderHelper.displayCircularImage(mContext, item.getDetail_cover(), bookIv, Constant.LOADING_DEFAULT_1);
        if (item.isMore) {
            helper.setVisible(R.id.more_tv, true);
            helper.setVisible(R.id.date_tv, false).setVisible(R.id.book_name_tv, false).setVisible(R.id.book_desc_tv, false)
                    .setVisible(R.id.support_tv, false).setVisible(R.id.comment_tv, false);
        } else {
            helper.setVisible(R.id.more_tv, false);
            helper.setVisible(R.id.date_tv, true).setVisible(R.id.book_name_tv, true).setVisible(R.id.book_desc_tv, true)
                    .setVisible(R.id.support_tv, true).setVisible(R.id.comment_tv, true);
        }

    }
}
