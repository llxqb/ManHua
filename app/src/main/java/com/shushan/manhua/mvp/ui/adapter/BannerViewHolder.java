package com.shushan.manhua.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.activity.mine.MemberCenterActivity;
import com.zhouwei.mzbanner.holder.MZViewHolder;


/**
 * banner
 */
public class BannerViewHolder implements MZViewHolder<BannerBean> {
    private ImageView mImageView;
    private ImageLoaderHelper mImageLoaderHelper;

    public BannerViewHolder(ImageLoaderHelper imageLoaderHelper) {
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, BannerBean item) {
        // 数据绑定
        mImageLoaderHelper.displayAutoMatchImage(context, item.getBanner_pic(), mImageView, Constant.LOADING_DEFAULT_3);

        //page点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1内部链接2外部链接3自定义
                if (item.getBanner_cate() == 1) {

                } else if (item.getBanner_cate() == 2) {

                } else {
                    if (item.getCustom().equals("experience")) { //优惠购买
                        Intent intent = new Intent(context, MemberCenterActivity.class);
                        context.startActivity(intent);
                    } else if (item.getCustom().equals("invite")) {
                        //邀请好友
//                        CouponActivity.start(context, "邀请好友");
                    }
                }
            }
        });
    }
}
