package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BookShelfResponse;

import java.util.List;

/**
 * 书架adapter
 */
public class BookShelfAdapter extends BaseQuickAdapter<BookShelfResponse, BaseViewHolder> {

    public BookShelfAdapter(@Nullable List<BookShelfResponse> data) {
        super(R.layout.item_bookshelf, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookShelfResponse item) {
        helper.addOnClickListener(R.id.item_bookshelf_layout);
        //设置圆角图片
//        mImageLoaderHelper.displayCircularImage(this, mUser.cover, mUploadPhotoIv, 0);
        helper.setImageResource(R.id.book_iv, item.cover);
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
