package com.shushan.manhua.mvp.ui.activity.main;

import android.os.Environment;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.ui.HwTxtPlayActivity;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {
    private String FilePath = Environment.getExternalStorageDirectory() + "/all.txt";

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_test);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void initView() {
//        TxtConfig.saveIsOnVerticalPageMode(this, false);
        HwTxtPlayActivity.loadTxtFile(this, FilePath);
//        HwTxtPlayActivity.loadStr(this,"");
    }

    @Override
    public void initData() {
    }

}
