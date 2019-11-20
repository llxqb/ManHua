package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SignInResponse;

import java.util.List;

/**
 * 连续签到adapter
 */
public class SignInAdapter extends BaseQuickAdapter<SignInResponse, BaseViewHolder> {

    public SignInAdapter(@Nullable List<SignInResponse> data) {
        super(R.layout.item_chick_in, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignInResponse item) {
        if (item.isSign) {
            helper.setVisible(R.id.checked_in_iv, true);
        } else {
            helper.setVisible(R.id.checked_in_iv, false);
        }
    }
}
