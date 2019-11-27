package com.shushan.manhua.mvp.ui.activity.book;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadingHistoryComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadingHistoryModule;
import com.shushan.manhua.entity.response.ReadingHistoryResponse;
import com.shushan.manhua.mvp.ui.adapter.ReadingHistoryAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 浏览历史
 */
public class ReadingHistoryActivity extends BaseActivity implements ReadingHistoryControl.ReadingHistoryView {

    @Inject
    ReadingHistoryControl.PresenterReadingHistory mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_right_tv)
    TextView mCommonRightTv;
    @BindView(R.id.history_record_recycler_view)
    RecyclerView mHistoryRecordRecyclerView;
    @BindView(R.id.history_bottom_layout)
    LinearLayout mHistoryBottomLayout;
    @BindView(R.id.select_all_tv)
    TextView mSelectAllTv;

    private ReadingHistoryAdapter mReadingHistoryAdapter;
    private List<ReadingHistoryResponse> readingHistoryResponseList = new ArrayList<>();
    /**
     * 是否是编辑状态
     */
    boolean isEditState = false;
    boolean isSelectState = false;//是否全选
    private View mEmptyView;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_history_read);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        initEmptyView();
        mCommonRightTv.setVisibility(View.VISIBLE);
        mCommonTitleTv.setText(getResources().getString(R.string.ReadingHistoryActivity_title));
        mCommonRightTv.setText(getResources().getString(R.string.ReadingHistoryActivity_edit));
        mReadingHistoryAdapter = new ReadingHistoryAdapter(readingHistoryResponseList);
        mHistoryRecordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryRecordRecyclerView.setAdapter(mReadingHistoryAdapter);

    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            ReadingHistoryResponse readingHistoryResponse = new ReadingHistoryResponse();
            if (i == 0) {
                readingHistoryResponse.date = "今天";
            } else if (i == 1) {
                readingHistoryResponse.date = "昨天";
            } else {
                readingHistoryResponse.date = "前天";
            }
            List<ReadingHistoryResponse.ReadingHistoryChildBean> readingHistoryChildBeanList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean = new ReadingHistoryResponse.ReadingHistoryChildBean();
                historyChildBean.isEditState = false;
                historyChildBean.isCheck = false;
                readingHistoryChildBeanList.add(historyChildBean);
                readingHistoryResponse.readingHistoryChildBeanList = readingHistoryChildBeanList;
            }
            readingHistoryResponseList.add(readingHistoryResponse);
        }

        //  mReadingHistoryAdapter.setEmptyView(mEmptyView);
    }

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mHistoryRecordRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_history);
        emptyTv.setText(getResources().getString(R.string.ReadingHistoryActivity_empty_tv));
    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.select_all_tv, R.id.delete_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv:
                if (isEditState) {//取消编辑  默认状态
                    isEditState = false;
                    mCommonRightTv.setText(getResources().getString(R.string.ReadingHistoryActivity_edit));
                    mCommonRightTv.setTextColor(getResources().getColor(R.color.first_text_color));
                    mHistoryBottomLayout.setVisibility(View.GONE);
                    for (ReadingHistoryResponse readingHistoryResponse : mReadingHistoryAdapter.getData()) {
                        for (ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean : readingHistoryResponse.readingHistoryChildBeanList) {
                            historyChildBean.isEditState = false;
                        }
                    }
                    mReadingHistoryAdapter.notifyDataSetChanged();
                } else {//编辑状态
                    isEditState = true;
                    mCommonRightTv.setText(getResources().getString(R.string.ReadingHistoryActivity_edit_cancel));
                    mCommonRightTv.setTextColor(Color.parseColor("#FF9100"));
                    mHistoryBottomLayout.setVisibility(View.VISIBLE);
                    for (ReadingHistoryResponse readingHistoryResponse : mReadingHistoryAdapter.getData()) {
                        for (ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean : readingHistoryResponse.readingHistoryChildBeanList) {
                            historyChildBean.isEditState = true;
                        }
                    }
                    mReadingHistoryAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.select_all_tv://全选
                if (isSelectState) {//设置不全选
                    isSelectState = false;
                    mSelectAllTv.setText(getResources().getString(R.string.ReadingHistoryActivity_select_all));
                    for (ReadingHistoryResponse readingHistoryResponse : mReadingHistoryAdapter.getData()) {
                        for (ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean : readingHistoryResponse.readingHistoryChildBeanList) {
                            historyChildBean.isCheck = false;
                        }
                    }
                    mReadingHistoryAdapter.notifyDataSetChanged();
                } else {
                    isSelectState = true;
                    mSelectAllTv.setText(getResources().getString(R.string.ReadingHistoryActivity_select_all_no));
                    for (ReadingHistoryResponse readingHistoryResponse : mReadingHistoryAdapter.getData()) {
                        for (ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean : readingHistoryResponse.readingHistoryChildBeanList) {
                            historyChildBean.isCheck = true;
                        }
                    }
                    mReadingHistoryAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.delete_tv://删除
                List<Integer> integerList = new ArrayList<>();
                for (ReadingHistoryResponse readingHistoryResponse : mReadingHistoryAdapter.getData()) {
                    for (int i = 0; i < readingHistoryResponse.readingHistoryChildBeanList.size(); i++) {
                        ReadingHistoryResponse.ReadingHistoryChildBean historyChildBean = readingHistoryResponse.readingHistoryChildBeanList.get(i);
                        if (historyChildBean.isCheck) {
                            integerList.add(i);
                        }
                    }
                }
                //TODO 这里要对应删除ReadingHistoryResponse下面id
                showToast(""+integerList.toString());
                break;
        }
    }

    private void onRequestReadingHistory(){

    }


    private void initInjectData() {
        DaggerReadingHistoryComponent.builder().appComponent(getAppComponent())
                .readingHistoryModule(new ReadingHistoryModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
