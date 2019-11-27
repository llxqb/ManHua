package com.shushan.manhua.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.utils.DateUtil;

import java.util.List;

/**
 * 书架adapter
 */
public class BookShelfDeleteAdapter extends BaseQuickAdapter<BookShelfResponse.BookrackBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    public BookShelfDeleteAdapter(@Nullable List<BookShelfResponse.BookrackBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_bookshelf_delete, data);
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookShelfResponse.BookrackBean item) {
        helper.addOnClickListener(R.id.item_bookshelf_layout);
        if (item.isCheck) {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_choose);
        } else {
            helper.setImageResource(R.id.check_iv, R.mipmap.history_delete_unchoose);
        }

        ImageView imageView = helper.getView(R.id.book_iv);
        mImageLoaderHelper.displayImage(mContext, item.getDetail_cover(), imageView, Constant.LOADING_DEFAULT_1);
        helper.setText(R.id.date_tv, DateUtil.getTimeChinString(item.getCreate_time(), DateUtil.TIME_YYMMDD));
        helper.setText(R.id.book_name_tv, item.getBook_name());
        helper.setText(R.id.book_desc_tv, item.getCatalogue_name());
        helper.setText(R.id.support_tv, String.valueOf(item.getLike()));
        helper.setText(R.id.comment_tv, String.valueOf(item.getComment_count()));
    }
}
