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
public class BookShelfDeleteAdapter extends BaseQuickAdapter<BookShelfResponse.BookrackBean, BaseViewHolder> {

    public BookShelfDeleteAdapter(@Nullable List<BookShelfResponse.BookrackBean> data) {
        super(R.layout.item_bookshelf_delete, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookShelfResponse.BookrackBean item) {
        helper.addOnClickListener(R.id.item_bookshelf_layout);
        //设置圆角图片
//        mImageLoaderHelper.displayCircularImage(this, mUser.cover, mUploadPhotoIv, 0);
//        helper.setImageResource(R.id.book_iv, item.cover);
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_unchoose);
        }
    }
}
