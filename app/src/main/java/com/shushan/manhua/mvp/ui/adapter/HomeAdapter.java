package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.HomeResponse;

import java.util.List;

/**
 * 首页书城adapter
 */
public class HomeAdapter extends BaseQuickAdapter<HomeResponse, BaseViewHolder> {

    public HomeAdapter(@Nullable List<HomeResponse> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeResponse item) {
        RecyclerView labelRecyclerView = helper.getView(R.id.label_recycler_view);
        HomeLabelAdapter mLabelAdapter = new HomeLabelAdapter(item.getLabelBeanList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        labelRecyclerView.setLayoutManager(linearLayoutManager);
        labelRecyclerView.setAdapter(mLabelAdapter);

    }
}
