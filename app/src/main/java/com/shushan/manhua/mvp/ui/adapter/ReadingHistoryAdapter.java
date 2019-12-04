package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;

import java.util.List;

/**
 * 浏览历史adapter
 */
public class ReadingHistoryAdapter extends BaseQuickAdapter<ReadingHistoryResponse.DataBean, BaseViewHolder> {

    public ReadingHistoryAdapter(@Nullable List<ReadingHistoryResponse.DataBean> data) {
        super(R.layout.item_reading_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadingHistoryResponse.DataBean item) {
        RecyclerView historyChildRecyclerView = helper.getView(R.id.history_child_recycler_view);
//        ReadingHistoryChildAdapter mReadingHistoryChildAdapter = new ReadingHistoryChildAdapter(item.getReadingHistoryChildBeanList());
//        historyChildRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        historyChildRecyclerView.setAdapter(mReadingHistoryChildAdapter);
//
//        mReadingHistoryChildAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                ReadingHistoryResponse.ReadingHistoryChildBean readingHistoryChildBean = (ReadingHistoryResponse.ReadingHistoryChildBean) adapter.getItem(position);
//                if (readingHistoryChildBean != null) {
//                    readingHistoryChildBean.isCheck = !readingHistoryChildBean.isCheck;
//                }
//                notifyDataSetChanged();
//            }
//        });
    }


}
