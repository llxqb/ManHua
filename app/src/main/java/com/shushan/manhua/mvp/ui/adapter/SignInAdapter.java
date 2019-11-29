package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SignDataResponse;

import java.util.List;

/**
 * 连续签到adapter
 */
public class SignInAdapter extends BaseQuickAdapter<SignDataResponse.SignBean, BaseViewHolder> {

    public SignInAdapter(@Nullable List<SignDataResponse.SignBean> data) {
        super(R.layout.item_chick_in, data);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
//            int payload = (int) payloads.get(0);
//            LogUtils.e("payload:" + payload);
            ImageView checkedInIv = holder.getView(R.id.checked_in_iv);
            checkedInIv.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, SignDataResponse.SignBean item) {
        if (item.isSign) {
            helper.setVisible(R.id.checked_in_iv, true);
        } else {
            helper.setVisible(R.id.checked_in_iv, false);
        }
        helper.setText(R.id.get_beans_num_tv, "+" + item.getBean());
        helper.setText(R.id.sign_in_day_tv, item.getSign_name());
    }
}
