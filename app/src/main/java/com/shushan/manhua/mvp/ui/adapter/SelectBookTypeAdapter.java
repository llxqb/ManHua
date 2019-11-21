package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BookTypeResponse;

import java.util.List;

/**
 * 选择漫画类型adapter
 */
public class SelectBookTypeAdapter extends BaseQuickAdapter<BookTypeResponse, BaseViewHolder> {

    public SelectBookTypeAdapter(@Nullable List<BookTypeResponse> data) {
        super(R.layout.item_select_book_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookTypeResponse item) {
        helper.addOnClickListener(R.id.item_select_book_layout);
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.gou_2);
        }

    }
}
