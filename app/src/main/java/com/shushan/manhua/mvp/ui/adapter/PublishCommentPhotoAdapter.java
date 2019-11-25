package com.shushan.manhua.mvp.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.manhua.R;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;

/**
 * 发布评论adapter
 */
public class PublishCommentPhotoAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {

    private ArrayList<TImage> tImageArrayList;

    public PublishCommentPhotoAdapter(@Nullable ArrayList<TImage> data) {
        super(R.layout.item_publish_photo, data);
        tImageArrayList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, TImage item) {
        helper.addOnClickListener(R.id.delete_iv).addOnClickListener(R.id.item_publish_photo_layout);
        if (item != null) {
//            if (helper.getAdapterPosition() == tImageArrayList.size()-1) {
//                helper.setVisible(R.id.delete_iv, false);
//            } else {
//                helper.setVisible(R.id.delete_iv, true);
//            }
            helper.setVisible(R.id.delete_iv, true);
            ImageView imageView = helper.getView(R.id.pic_iv);
            Bitmap bitmap = BitmapFactory.decodeFile(item.getOriginalPath());
            imageView.setImageBitmap(bitmap);
        }else {
            helper.setVisible(R.id.delete_iv, false);
        }

    }
}
