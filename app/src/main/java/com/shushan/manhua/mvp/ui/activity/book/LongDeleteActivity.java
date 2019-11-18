package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLongDeleteComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LongDeleteModule;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.mvp.ui.adapter.BookShelfDeleteAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 长按删除
 */
public class LongDeleteActivity extends BaseActivity implements LongDeleteControl.LongDeleteView {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_right_tv)
    TextView mCommonRightTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.select_all_tv)
    TextView mSelectAllTv;
    private BookShelfDeleteAdapter mBookShelfDeleteAdapter;
    private List<BookShelfResponse> bookShelfResponseList = new ArrayList<>();
    /**
     * 是否全选
     * false: 全选
     * true:全不选
     */
    private boolean isSelectAll = false;
    @Inject
    LongDeleteControl.PresenterLongDelete mPresenter;

    public static void start(Context context, BookShelfResponse bookShelfResponse) {
        Intent intent = new Intent(context, LongDeleteActivity.class);
        intent.putExtra("bookShelfResponse", bookShelfResponse);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_long_delete);
        mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_title_tv));
        initInjectData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            BookShelfResponse bookShelfResponse = getIntent().getParcelableExtra("bookShelfResponse");
            mBookShelfDeleteAdapter = new BookShelfDeleteAdapter(bookShelfResponseList);
            mRecyclerView.setAdapter(mBookShelfDeleteAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mBookShelfDeleteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    BookShelfResponse bookShelfResponse = (BookShelfResponse) adapter.getItem(position);
                    ImageView checkIv = view.findViewById(R.id.check_iv);
                    if (bookShelfResponse != null) {
                        if (bookShelfResponse.isCheck) {
                            bookShelfResponse.isCheck = false;
                            checkIv.setImageResource(R.mipmap.history_delete_unchoose);
                        } else {
                            bookShelfResponse.isCheck = true;
                            checkIv.setImageResource(R.mipmap.history_delete_choose);
                        }
                        mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_tv) + "(" + selectDeletePosNum() + ")");
                    }
                }
            });
        }

    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            BookShelfResponse bookShelfResponse = new BookShelfResponse();
            bookShelfResponse.cover = R.mipmap.book_icon;
            bookShelfResponseList.add(bookShelfResponse);
        }
    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.select_all_tv, R.id.delete_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv:
                //取消
                break;
            case R.id.select_all_tv:
                if (isSelectAll) {
                    //全不选
                    isSelectAll = false;
                    for (BookShelfResponse bookShelfResponse : bookShelfResponseList) {
                        bookShelfResponse.isCheck = false;
                    }
                    mSelectAllTv.setText(getResources().getString(R.string.LongDeleteActivity_select_all));
                } else {
                    //全选
                    isSelectAll = true;
                    for (BookShelfResponse bookShelfResponse : bookShelfResponseList) {
                        bookShelfResponse.isCheck = true;
                    }
                    mSelectAllTv.setText(getResources().getString(R.string.LongDeleteActivity_select_all_no));
                }
                mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_tv) + "(" + selectDeletePosNum() + ")");
                mBookShelfDeleteAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_tv:
                //删除
                List<Integer> deletePosList = new ArrayList<>();//要删除的位置id
                for (int i = 0; i < mBookShelfDeleteAdapter.getData().size(); i++) {
                    BookShelfResponse bookShelfResponse = mBookShelfDeleteAdapter.getData().get(i);
                    if (bookShelfResponse.isCheck) {
                        deletePosList.add(i);
                    }
                }
                showToast(deletePosList.toString());
                LogUtils.e("deletePosList:" + deletePosList.toString());
                //走接口删除后 走查询接口
                break;
        }
    }

    private int selectDeletePosNum() {
        List<Integer> deletePosList = new ArrayList<>();//要删除的位置id
        for (int i = 0; i < mBookShelfDeleteAdapter.getData().size(); i++) {
            BookShelfResponse bookShelfResponse = mBookShelfDeleteAdapter.getData().get(i);
            if (bookShelfResponse.isCheck) {
                deletePosList.add(i);
            }
        }
        showToast(deletePosList.toString());
        LogUtils.e("deletePosList:" + deletePosList.toString());
        return deletePosList.size();
    }

    private void initInjectData() {
        DaggerLongDeleteComponent.builder().appComponent(getAppComponent())
                .longDeleteModule(new LongDeleteModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
