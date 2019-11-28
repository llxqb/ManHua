package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.views.ResizableImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看大图
 */
public class LookPhotoActivity extends BaseActivity {

    @BindView(R.id.resizableImageView)
    ResizableImageView mResizableImageView;

    public static void start(Context context, String picPath) {
        Intent intent = new Intent(context, LookPhotoActivity.class);
        intent.putExtra("picPath", picPath);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_look_photo);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            String picPath = getIntent().getStringExtra("picPath");
            mImageLoaderHelper.displayImage(this, picPath, mResizableImageView, Constant.LOADING_DEFAULT_4);
        }
    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.resizableImageView)
    public void onViewClicked() {
        finish();
    }
}
