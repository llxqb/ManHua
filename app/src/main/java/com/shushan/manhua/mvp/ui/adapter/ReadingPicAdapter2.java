package com.shushan.manhua.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.R;
import com.shushan.manhua.help.ImageLoaderHelper;

import java.util.List;

/**
 * 漫画章节图片adapter
 */
public class ReadingPicAdapter2 extends RecyclerView.Adapter {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;
    private List<String> mPicList;
    private View containView;

    public ReadingPicAdapter2(ImageLoaderHelper mImageLoaderHelper, List<String> picList, Context context) {
        this.mImageLoaderHelper = mImageLoaderHelper;
        mPicList = picList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_reading_pic, null, false);
        containView  =view;
//        addView();
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return mPicList.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 增加弹幕view
     */
//    public void addView() {
//        for (int i = 0; i < 3; i++) {
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            View view = inflater.inflate(R.layout.text_view, null);
//            TextView textView = view.findViewById(R.id.text_tv);
//            textView.setText("我是弹幕我是弹幕");
//            layoutParams.setMargins(200, 200 + 50 * i, 0, 0);
//            textView.setLayoutParams(layoutParams);
//            containView.addView(view);  // 调用一个参数的addView方法
//        }
//    }
}
