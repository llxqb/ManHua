package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.VoucherCenterResponse;

import java.util.List;

/**
 * 购买漫豆adapter
 */
public class BuyBeansAdapter extends BaseQuickAdapter<VoucherCenterResponse.BeaninfoBean, BaseViewHolder> {

    public BuyBeansAdapter(@Nullable List<VoucherCenterResponse.BeaninfoBean> data) {
        super(R.layout.item_buy_beans, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoucherCenterResponse.BeaninfoBean item) {
        helper.addOnClickListener(R.id.item_buy_beans_layout);
        if (item.isCheck) {
            helper.setBackgroundRes(R.id.item_buy_beans_layout, R.mipmap.buy_item_orange_bg);
        } else {
            helper.setBackgroundRes(R.id.item_buy_beans_layout, R.mipmap.buy_item_gray_bg);
        }
        helper.setText(R.id.beans_num_tv, item.getBeans() + mContext.getString(R.string.MineFragment_beans_tv));
        helper.setText(R.id.desc_tv, "hadiah " + item.getPresenter_beans() + " koin");
        helper.setText(R.id.money_tv, "$" + item.getPrice());

    }
}
