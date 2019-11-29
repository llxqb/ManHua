package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.LogUtils;

import java.util.List;

/**
 * 选择漫画类型adapter
 */
public class SelectBookTypeAdapter extends BaseQuickAdapter<BookTypeResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public SelectBookTypeAdapter(@Nullable List<BookTypeResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_select_book_type, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean payload = (boolean) payloads.get(0);
            LogUtils.e("payload:" + payload);
            ImageView checkIv = holder.getView(R.id.check_iv);
            if (payload) {
                checkIv.setImageResource(R.mipmap.history_delete_choose);
            } else {
                checkIv.setImageResource(R.mipmap.gou_2);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, BookTypeResponse.DataBean item) {
        helper.addOnClickListener(R.id.item_select_book_layout);
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.gou_2);
        }
        ImageView imageView = helper.getView(R.id.cover_iv);
        mImageLoaderHelper.displayImage(mContext, item.getType_cover(), imageView, Constant.LOADING_DEFAULT_1);
        helper.setText(R.id.type_name_tv, item.getType_name());
    }
}
