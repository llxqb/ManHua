package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.utils.LogUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;


/**
 * 评论自定义软键盘PopupWindow
 */
public class CommentSoftKeyPopupWindow extends TakePhotoActivity implements TakePhoto.TakeResultListener,InvokeListener {
    private Activity mContext;
    private CommentSoftKeyPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //成功取得照片
    Bitmap bitmap;


    public CommentSoftKeyPopupWindow(Activity context, CommentSoftKeyPopupWindowListener popupWindowListener) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        File file = new File(context.getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
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
        ImageView photoIv = contentView.findViewById(R.id.photo_iv);
        ImageView albumIv = contentView.findViewById(R.id.album_iv);
        TextView sendTv = contentView.findViewById(R.id.send_tv);
        EditText messageEt = contentView.findViewById(R.id.message_et);
        messageEt.requestFocus();//获取焦点
//        BarrageStyleAdapter barrageStyleAdapter = new BarrageStyleAdapter(barrageStyleResponseList);
//        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        recyclerView.setAdapter(barrageStyleAdapter);
//        barrageStyleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            BarrageStyleResponse barrageStyleResponse = (BarrageStyleResponse) adapter.getItem(position);
//            for (BarrageStyleResponse barrageStyleResponse1 : barrageStyleResponseList) {
//                if (barrageStyleResponse1.isCheck) {
//                    barrageStyleResponse1.isCheck = false;
//                }
//            }
//            barrageStyleResponse.isCheck = true;
//            adapter.notifyDataSetChanged();
//        });
//
        photoIv.setOnClickListener(v -> {
            //从相机获取图片(不裁剪)
            getTakePhoto().onPickFromCapture(uri);
        });
        albumIv.setOnClickListener(v -> {
            //从相册中获取图片（不裁剪）
            getTakePhoto().onPickFromGallery();
        });

        sendTv.setOnClickListener(v -> {
            if (mPopupWindowListener != null) {
                mPopupWindowListener.CommentSendMessageBtnListener();
                mCustomPopWindow.dissmiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeSuccess(TResult result) {
//        bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
//        String path = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
//        mAvatarIv.setImageBitmap(bitmap);
        LogUtils.d("path:" + result.getImage().getCompressPath());
//        上传图片
//        uploadImage(path);
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), false);
        return takePhoto;
    }


    public interface CommentSoftKeyPopupWindowListener {

        void CommentSendMessageBtnListener();
    }
}
