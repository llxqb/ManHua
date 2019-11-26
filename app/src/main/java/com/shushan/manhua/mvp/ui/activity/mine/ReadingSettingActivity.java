package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.mvp.ui.adapter.SelectBookTypeAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读偏好
 */
public class ReadingSettingActivity extends BaseActivity {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.channel_female_iv)
    ImageView mChannelFemaleIv;
    @BindView(R.id.channel_male_iv)
    ImageView mChannelMaleIv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.select_num_tv)
    TextView mSelectNumTv;
    private List<BookTypeResponse> bookTypeResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_reading_setting);
        setStatusBar();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getString(R.string.ReadingSettingActivity_title));
        List<Integer> chooseList = new ArrayList<>();//已选择的类型id
        SelectBookTypeAdapter mSelectBookTypeAdapter = new SelectBookTypeAdapter(bookTypeResponseList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mSelectBookTypeAdapter);
        mSelectBookTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookTypeResponse bookTypeResponse = (BookTypeResponse) adapter.getItem(position);
            if (bookTypeResponse != null) {
                if (bookTypeResponse.isCheck) {
                    bookTypeResponse.isCheck = false;
                    for (int i = 1; i <= chooseList.size(); i++) {
                        if (i == position) {
                            chooseList.remove(i);
                        }
                    }
                } else {
                    if (chooseList.size() < 3) {
                        bookTypeResponse.isCheck = true;
                        chooseList.add(position);
                    }
                }
            }
            LogUtils.e("" + chooseList.size());
            mSelectNumTv.setText(chooseList.size() + "/3");
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < 6; i++) {
            BookTypeResponse bookTypeResponse = new BookTypeResponse();
            bookTypeResponse.isCheck = false;
            bookTypeResponseList.add(bookTypeResponse);
        }
    }


    @OnClick({R.id.common_left_iv, R.id.channel_female_ll, R.id.channel_male_ll, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.channel_female_ll:
                break;
            case R.id.channel_male_ll:
                break;
            case R.id.sure_tv:
                break;
        }
    }

}
