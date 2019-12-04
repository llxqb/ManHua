package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerLongDeleteComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.LongDeleteModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.DeleteBookShelfRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.mvp.ui.adapter.BookShelfDeleteAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 长按删除
 */
public class LongDeleteActivity extends BaseActivity implements LongDeleteControl.LongDeleteView {

    @Inject
    LongDeleteControl.PresenterLongDelete mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_right_tv)
    TextView mCommonRightTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.select_all_tv)
    TextView mSelectAllTv;
    private BookShelfDeleteAdapter mBookShelfDeleteAdapter;
    /**
     * 是否全选
     * false: 全选
     * true:全不选
     */
    private boolean isSelectAll = false;
    ArrayList<BookShelfResponse.BookrackBean> mBookrackBeanList;

    public static void start(Context context, ArrayList<BookShelfResponse.BookrackBean> bookrackBeanList) {
        Intent intent = new Intent(context, LongDeleteActivity.class);
        intent.putParcelableArrayListExtra("bookrackBeanList", bookrackBeanList);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_long_delete);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_title_tv));
        if (getIntent() != null) {
            mBookrackBeanList = getIntent().getParcelableArrayListExtra("bookrackBeanList");
            if (mBookrackBeanList.size() > 1) {
                mBookrackBeanList.remove(mBookrackBeanList.size() - 1);
            }
            mBookShelfDeleteAdapter = new BookShelfDeleteAdapter(mBookrackBeanList, mImageLoaderHelper);
            mRecyclerView.setAdapter(mBookShelfDeleteAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mBookShelfDeleteAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                BookShelfResponse.BookrackBean bookShelfResponse = (BookShelfResponse.BookrackBean) adapter.getItem(position);
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
            });
        }

    }

    @Override
    public void initData() {
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
                    for (BookShelfResponse.BookrackBean bookrackBean : mBookShelfDeleteAdapter.getData()) {
                        bookrackBean.isCheck = false;
                    }
                    mSelectAllTv.setText(getResources().getString(R.string.LongDeleteActivity_select_all));
                } else {
                    //全选
                    isSelectAll = true;
                    for (BookShelfResponse.BookrackBean bookrackBean : mBookShelfDeleteAdapter.getData()) {
                        bookrackBean.isCheck = true;
                    }
                    mSelectAllTv.setText(getResources().getString(R.string.LongDeleteActivity_select_all_no));
                }
                mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_tv) + "(" + selectDeletePosNum() + ")");
                mBookShelfDeleteAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_tv:       //删除
                List<Integer> deletePosList = new ArrayList<>();//要删除的位置id
                for (BookShelfResponse.BookrackBean bookrackBean : mBookShelfDeleteAdapter.getData()) {
                    if (bookrackBean.isCheck) {
                        deletePosList.add(bookrackBean.getBook_id());
                    }
                }
                onRequestDeleteBook(new Gson().toJson(deletePosList));
                break;
        }
    }

    /**
     * 删除书架漫画
     */
    private void onRequestDeleteBook(String booksId) {
        DeleteBookShelfRequest deleteBookShelfRequest = new DeleteBookShelfRequest();
        deleteBookShelfRequest.token = mBuProcessor.getToken();
        deleteBookShelfRequest.book_ids = booksId;
        mPresenter.onRequestDeleteBook(deleteBookShelfRequest);
    }

    @Override
    public void getDeleteBookShelfSuccess() {
        //走接口删除后 走查询接口
        onRequestBookShelfInfo();
    }


    /**
     * 请求书架数据
     */
    private void onRequestBookShelfInfo() {
        BookShelfInfoRequest bookShelfInfoRequest = new BookShelfInfoRequest();
        bookShelfInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBookShelfInfo(bookShelfInfoRequest);
    }


    @Override
    public void getBookShelfInfoSuccess(BookShelfResponse bookShelfResponse) {
        mCommonTitleTv.setText(getResources().getString(R.string.LongDeleteActivity_select_title_tv));
        mBookShelfDeleteAdapter.setNewData(bookShelfResponse.getBookrack());
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BOOKSHELF));
    }


    /**
     * 要删除的数量
     */
    private int selectDeletePosNum() {
        List<Integer> deletePosList = new ArrayList<>();//要删除的位置id
        for (int i = 0; i < mBookShelfDeleteAdapter.getData().size(); i++) {
            BookShelfResponse.BookrackBean bookrackBean = mBookShelfDeleteAdapter.getData().get(i);
            if (bookrackBean.isCheck) {
                deletePosList.add(i);
            }
        }
        return deletePosList.size();
    }

    private void initInjectData() {
        DaggerLongDeleteComponent.builder().appComponent(getAppComponent())
                .longDeleteModule(new LongDeleteModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
