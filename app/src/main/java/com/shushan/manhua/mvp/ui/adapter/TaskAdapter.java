package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SignDataResponse;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 任务adapter
 */
public class TaskAdapter extends BaseQuickAdapter<SignDataResponse.QuestBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public TaskAdapter(@Nullable List<SignDataResponse.QuestBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_task, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//            TextView stateValueTv = holder.getView(R.id.state_value_tv);
//            stateValueTv.setBackgroundResource(R.mipmap.beans_completed);
//            stateValueTv.setTextColor(mContext.getResources().getColor(R.color.buy_no_check_color));
//            stateValueTv.setText("Selesai");
//        }
//    }


    @Override
    protected void convert(BaseViewHolder helper, SignDataResponse.QuestBean item) {
        helper.addOnClickListener(R.id.state_value_tv);
        ImageView imageView = helper.getView(R.id.icon_iv);
        mImageLoaderHelper.displayImage(mContext, item.getIcon(), imageView, R.mipmap.beans_task_read);
        helper.setText(R.id.task_name_tv, item.getQuest_name());
        TextView taskFinishHintTv = helper.getView(R.id.task_finish_hint_tv);
        if (item.getNum() != 0) {
            taskFinishHintTv.setVisibility(View.VISIBLE);
        } else {
            taskFinishHintTv.setVisibility(View.GONE);
        }
        helper.setText(R.id.task_finish_hint_tv, "Total hari ini " + item.getNum() + " chapter");
        //1未完成 2已完成未领取 3已完成
        if (item.getStatus() == 1) {
            helper.setBackgroundRes(R.id.state_value_tv, R.mipmap.beans_incomplete);
            helper.setText(R.id.state_value_tv, "+" + item.getBean() + mContext.getString(R.string.MineFragment_beans_tv));
        } else if (item.getStatus() == 2) {
            helper.setBackgroundRes(R.id.state_value_tv, R.mipmap.beans_receive);
            helper.setText(R.id.state_value_tv, "ambil " + item.getBean() + mContext.getString(R.string.MineFragment_beans_tv));
        } else {
            helper.setBackgroundRes(R.id.state_value_tv, R.mipmap.beans_completed);
            helper.setText(R.id.state_value_tv, "Selesai");
            helper.setTextColor(R.id.state_value_tv, mContext.getResources().getColor(R.color.buy_no_check_color));
        }
    }
}
