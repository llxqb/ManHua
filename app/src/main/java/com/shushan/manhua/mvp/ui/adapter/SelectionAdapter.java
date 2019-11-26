package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response. SelectionResponse;

import java.util.List;

/**
 * 书籍选集adapter
 */
public class SelectionAdapter extends BaseQuickAdapter< SelectionResponse, BaseViewHolder> {

    public SelectionAdapter(@Nullable List< SelectionResponse> data) {
        super(R.layout.item_selection, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,  SelectionResponse item) {

    }
}
