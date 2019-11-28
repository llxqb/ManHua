package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SelectionResponse;

import java.util.List;

/**
 * 书架adapter
 */
public class ReadingChapterAdapter extends BaseQuickAdapter<SelectionResponse.AnthologyBean, BaseViewHolder> {

    public ReadingChapterAdapter(@Nullable List<SelectionResponse.AnthologyBean> data) {
        super(R.layout.item_reading_chapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectionResponse.AnthologyBean item) {

//        helper.setText(R.id.title_tv,item.title);
    }
}
