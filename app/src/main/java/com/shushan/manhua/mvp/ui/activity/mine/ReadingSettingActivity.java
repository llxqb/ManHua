package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadingSettingComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadingSettingModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.mvp.ui.adapter.SelectBookTypeAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读偏好
 */
public class ReadingSettingActivity extends BaseActivity implements ReadingSettingControl.ReadingSettingView {

    @Inject
    ReadingSettingControl.PresenterReadingSetting mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.channel_female_iv)
    ImageView mChannelFemaleIv;
    @BindView(R.id.channel_male_iv)
    ImageView mChannelMaleIv;
    @BindView(R.id.mask_female_iv)
    ImageView mMaskFemaleIv;
    @BindView(R.id.mask_male_iv)
    ImageView mMaskMaleIv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.select_num_tv)
    TextView mSelectNumTv;
    private List<BookTypeResponse.DataBean> bookTypeResponseList = new ArrayList<>();
    private int sex;//1男频2女频
    private SelectBookTypeAdapter mSelectBookTypeAdapter;
    private List<Integer> mChooseList;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_reading_setting);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getString(R.string.ReadingSettingActivity_title));
        List<Integer> chooseList = new ArrayList<>();//已选择的类型id
        mSelectBookTypeAdapter = new SelectBookTypeAdapter(bookTypeResponseList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mSelectBookTypeAdapter);
        mSelectBookTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookTypeResponse.DataBean bookTypeResponse = (BookTypeResponse.DataBean) adapter.getItem(position);
            if (bookTypeResponse != null) {
                if (bookTypeResponse.isCheck) {
                    bookTypeResponse.isCheck = false;
                    for (int i = 1; i <= chooseList.size(); i++) {
                        if (i == position) {
                            chooseList.remove(i);
                        }
                    }
                    mSelectNumTv.setText(chooseList.size() + "/3");
                    adapter.notifyItemChanged(position, false);//局部刷新
                } else {
                    if (chooseList.size() < 3) {
                        bookTypeResponse.isCheck = true;
                        chooseList.add(position);
                        mSelectNumTv.setText(chooseList.size() + "/3");
                        adapter.notifyItemChanged(position, true);//局部刷新
                    } else {
                        showToast("Pilih hingga tiga jenis");
                    }
                }
            }

        });
    }

    @Override
    public void initData() {
        mPresenter.onRequestManHuaType();
    }


    @OnClick({R.id.common_left_iv, R.id.channel_female_rl, R.id.channel_male_rl, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.channel_female_rl:
                sex = 2;
                mMaskFemaleIv.setVisibility(View.VISIBLE);
                mMaskMaleIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.channel_male_rl:
                mMaskMaleIv.setVisibility(View.VISIBLE);
                mMaskFemaleIv.setVisibility(View.INVISIBLE);
                sex = 1;
                break;
            case R.id.sure_tv:
                mChooseList = new ArrayList<>();
                for (BookTypeResponse.DataBean bookTypeResponse : mSelectBookTypeAdapter.getData()) {
                    if (bookTypeResponse.isCheck) {
                        mChooseList.add(bookTypeResponse.getType_id());
                    }
                }
                if (mChooseList.size() > 0) {
                    onReadingSettingRequest();
                }
                break;
        }
    }


    @Override
    public void getManHuaTypeSuccess(BookTypeResponse bookTypeResponse) {
        mSelectBookTypeAdapter.setNewData(bookTypeResponse.getData());
    }

    /**
     * 设置阅读偏好
     */
    private void onReadingSettingRequest() {
        ReadingSettingRequest readingSettingRequest = new ReadingSettingRequest();
        readingSettingRequest.token = mBuProcessor.getToken();
        readingSettingRequest.channel = String.valueOf(sex);
        readingSettingRequest.book_type = mChooseList.toString();
        mPresenter.onReadingSettingRequest(readingSettingRequest);
    }

    @Override
    public void getReadingSettingSuccess() {
        showToast("success");
        finish();
        mSharePreferenceUtil.setData(Constant.CHANNEL, String.valueOf(sex));
        mSharePreferenceUtil.setData(Constant.BOOK_TYPE, mChooseList.toString());//[1,2,3]喜欢的类型
        //更新首页数据 更新书架推荐数据
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_RECOMMEND_BOOK));
    }

    private void initInjectData() {
        DaggerReadingSettingComponent.builder().appComponent(getAppComponent())
                .readingSettingModule(new ReadingSettingModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
