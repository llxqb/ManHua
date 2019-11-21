package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.TaskResponse;

import java.util.List;

/**
 * 任务adapter
 */
public class TaskAdapter extends BaseQuickAdapter<TaskResponse, BaseViewHolder> {

    public TaskAdapter(@Nullable List<TaskResponse> data) {
        super(R.layout.item_task, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskResponse item) {

    }
}
