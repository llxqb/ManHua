package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.adapter.PublishCommentPhotoAdapter;
import com.shushan.manhua.mvp.utils.LogUtils;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;


/**
 * 评论自定义软键盘PopupWindow
 */
public class CommentSoftKeyPopupWindow {
    private Activity mContext;
    private CommentSoftKeyPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private ArrayList<TImage> publishCommentPhotoResponseList = new ArrayList<>();
    private PublishCommentPhotoAdapter publishCommentPhotoAdapter;

    public CommentSoftKeyPopupWindow(Activity context, CommentSoftKeyPopupWindowListener popupWindowListener, ArrayList<TImage> publishCommentPhotoResponseList) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        this.publishCommentPhotoResponseList = publishCommentPhotoResponseList;
    }

    public void setListData(ArrayList<TImage> publishCommentPhotoResponseList, LoadDataView loadDataView) {
        publishCommentPhotoAdapter.setNewData(publishCommentPhotoResponseList);
        loadDataView.dismissLoading();
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_comment_soft_key, null);
        //处理popWindow 显示内容
        handlePopListView(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }


    private void handlePopListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        ImageView sendMessageLeftIv = contentView.findViewById(R.id.send_message_left_iv);
        ImageView photoIv = contentView.findViewById(R.id.photo_iv);
        ImageView albumIv = contentView.findViewById(R.id.album_iv);
        TextView sendTv = contentView.findViewById(R.id.send_tv);
        EditText messageEt = contentView.findViewById(R.id.message_et);
        messageEt.requestFocus();//获取焦点

        publishCommentPhotoAdapter = new PublishCommentPhotoAdapter(publishCommentPhotoResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(publishCommentPhotoAdapter);
        publishCommentPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TImage tImage = (TImage) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.delete_iv:
                    LogUtils.e("position:"+position);
                    adapter.remove(position);
                    break;
                case R.id.item_publish_photo_layout:
                    if(position==0){

                    }
                    break;
            }
        });

        sendMessageLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        photoIv.setOnClickListener(v -> {
            if (mPopupWindowListener != null && publishCommentPhotoAdapter.getItemCount() < 9) {
                mPopupWindowListener.photoBtnListener();
            }
            //从相机获取图片(不裁剪)
//            getTakePhoto().onPickFromCapture(uri);
        });
        albumIv.setOnClickListener(v -> {
            //从相册中获取图片（不裁剪）
//            getTakePhoto().onPickFromGallery();
            if (mPopupWindowListener != null && publishCommentPhotoAdapter.getItemCount() < 9) {
                mPopupWindowListener.albumBtnListener(9 - publishCommentPhotoAdapter.getItemCount());
            }
        });

        sendTv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.CommentSendMessageBtnListener();
//                mCustomPopWindow.dissmiss();
            }
        });
    }


    public interface CommentSoftKeyPopupWindowListener {

        void switchFunctionByCommentSoftKeyBtnListener();

        void photoBtnListener();

        void albumBtnListener(int maxPicNum);

        void CommentSendMessageBtnListener();
    }
}
