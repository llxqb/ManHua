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
        helper.setImageResource(R.id.book_iv,item.cover);
//        helper.addOnClickListener(R.id.item_counselling_date);
//        helper.setText(R.id.date_name, item.name);
//        if (item.check) {
//            helper.setVisible(R.id.day_check_iv, true);
//        } else {
//            helper.setVisible(R.id.day_check_iv, false);
//        }
    }
}
