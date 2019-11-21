package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.HomeResponse;

import java.util.List;

/**
 * 书籍标签adapter
 */
public class HomeLabelAdapter extends BaseQuickAdapter<HomeResponse.LabelBean, BaseViewHolder> {

    public HomeLabelAdapter(@Nullable List<HomeResponse.LabelBean> data) {
        super(R.layout.item_home_label, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeResponse.LabelBean item) {

    }
}
