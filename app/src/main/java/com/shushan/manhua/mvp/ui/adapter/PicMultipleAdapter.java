package com.shushan.manhua.mvp.ui.adapter;

/**
 * 图片九宫格adapter
 */
//public class PicMultipleAdapter extends BaseMultiItemQuickAdapter<PicBean, BaseViewHolder> {
//
//    private ImageLoaderHelper mImageLoaderHelper;
//
//    public PicMultipleAdapter(List data, ImageLoaderHelper imageLoaderHelper) {
//        super(data);
//        mImageLoaderHelper = imageLoaderHelper;
//        addItemType(PicBean.SINGLE_IMG, R.layout.item_single_pic);
//        addItemType(PicBean.DOUBLE_IMG, R.layout.item_double_pic);
//        addItemType(PicBean.MULT_IMG, R.layout.item_mult_pic);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, PicBean item) {
//        helper.addOnClickListener(R.id.item_pic_layout);
//        switch (helper.getItemViewType()) {
//            case PicBean.SINGLE_IMG:
//                ImageView picIv = helper.getView(R.id.img_iv);
//                if (!TextUtils.isEmpty(item.pic)) {
//                    mImageLoaderHelper.displayImage(mContext, item.pic, picIv, Constant.LOADING_DEFAULT_1);
//                }
//                break;
//            case PicBean.DOUBLE_IMG:
//                ImageView picIv1 = helper.getView(R.id.img1_iv);
//                ImageView picIv2 = helper.getView(R.id.img2_iv);
//                mImageLoaderHelper.displayImage(mContext, item.pic, picIv1, Constant.LOADING_DEFAULT_1);
//                mImageLoaderHelper.displayImage(mContext, item.pic, picIv2, Constant.LOADING_DEFAULT_1);
//                break;
//            case PicBean.MULT_IMG:
//                ImageView multPicIv = helper.getView(R.id.img_iv);
//                mImageLoaderHelper.displayImage(mContext, item.pic, multPicIv, Constant.LOADING_DEFAULT_1);
//                break;
//        }
//    }
//
//}
