package com.shushan.manhua.mvp.ui.activity.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.mvp.ui.adapter.ReadingPicAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {
    @BindView(R.id.pic_recycler_view)
    RecyclerView mPicRecyclerView;
    ReadingPicAdapter mReadingPicAdapter;
    List<ReadingInfoResponse.CatalogueBean.CatalogueContentBean> catalogueContentBeanList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_test);
        ((ManHuaApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void initView() {
        String ss = "{\n" +
                "            \"catalogue_id\":141,\n" +
                "            \"catalogue_name\":\"Chapter 3 Dia akan kembali\",\n" +
                "            \"catalogue_cover\":\"https://img.pulaukomik.com/catalogue/15759503495469.png\",\n" +
                "            \"catalogue_content\":[\n" +
                "                {\n" +
                "                    \"url\":\"https://img.pulaukomik.com/catalogue/15759503102736.png?x-oss-process=image/auto-orient,1/quality,q_90/watermark,image_aWNvbi9Hcm91cCAyNS5wbmc_eC1vc3MtcHJvY2Vzcz1pbWFnZS9yZXNpemUsUF8xMDA,g_center,x_10,y_10\",\n" +
                "                    \"width\":800,\n" +
                "                    \"height\":10000\n" +
                "                },\n" +
                "                {\n" +
                "                    \"url\":\"https://img.pulaukomik.com/catalogue/15759503237004.png?x-oss-process=image/auto-orient,1/quality,q_90/watermark,image_aWNvbi9Hcm91cCAyNS5wbmc_eC1vc3MtcHJvY2Vzcz1pbWFnZS9yZXNpemUsUF8xMDA,g_center,x_10,y_10\",\n" +
                "                    \"width\":800,\n" +
                "                    \"height\":9550\n" +
                "                },\n" +
                "                {\n" +
                "                    \"url\":\"https://img.pulaukomik.com/catalogue/15759503373192.png?x-oss-process=image/auto-orient,1/quality,q_90/watermark,image_aWNvbi9Hcm91cCAyNS5wbmc_eC1vc3MtcHJvY2Vzcz1pbWFnZS9yZXNpemUsUF8xMDA,g_center,x_10,y_10\",\n" +
                "                    \"width\":800,\n" +
                "                    \"height\":11000\n" +
                "                }\n" +
                "            ],\n" +
                "            \"comment_count\":0,\n" +
                "            \"like\":102,\n" +
                "            \"change_like\":100,\n" +
                "            \"type\":0,\n" +
                "            \"sort\":3,\n" +
                "            \"cost\":5,\n" +
                "            \"vip_cost\":3,\n" +
                "            \"userbean\":217,\n" +
                "            \"is_like\":1,\n" +
                "            \"state\":0,\n" +
                "            \"count\":0,\n" +
                "            \"next_catalogue_id\":142,\n" +
                "            \"pre_catalogue_id\":333\n" +
                "        }";
        ReadingInfoResponse.CatalogueBean catalogueBean = new Gson().fromJson(ss, ReadingInfoResponse.CatalogueBean.class);

        LogUtils.e("catalogueBean:" + new Gson().toJson(catalogueBean));
        for (ReadingInfoResponse.CatalogueBean.CatalogueContentBean catalogueContentBean : catalogueBean.getCatalogue_content()) {
            catalogueContentBeanList.add(catalogueContentBean);
        }

        mReadingPicAdapter = new ReadingPicAdapter(catalogueContentBeanList, mImageLoaderHelper);
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPicRecyclerView.setAdapter(mReadingPicAdapter);
    }

    @Override
    public void initData() {

    }

}
