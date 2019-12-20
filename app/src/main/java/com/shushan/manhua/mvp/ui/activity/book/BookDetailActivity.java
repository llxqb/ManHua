package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookDetailComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
import com.shushan.manhua.mvp.ui.adapter.LabelAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragment;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionDetailFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 漫画书籍详情
 */
public class BookDetailActivity extends BaseActivity implements BookDetailControl.BookDetailView {

    @Inject
    BookDetailControl.PresenterBookDetail mPresenter;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.book_tv)
    TextView mBookTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.add_bookshelf_tv)
    TextView mAddBookshelfTv;
    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<String> labelResponseList = new ArrayList<>();
    String[] titles;
    String mBookId;
    private BookDetailInfoResponse mBookDetailInfoResponse;
    private LabelAdapter mLabelAdapter;
    private boolean mIsBook;//是否是小说  false 漫画  true 小说

    public static void start(Context context, String bookId) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("bookId", bookId);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_book_detail);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            titles = new String[]{getResources().getString(R.string.BookDetailActivity_detail_tv), getResources().getString(R.string.BookDetailActivity_selection_tv)};
            mLabelAdapter = new LabelAdapter(labelResponseList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mLabelRecyclerView.setLayoutManager(linearLayoutManager);
            mLabelRecyclerView.setAdapter(mLabelAdapter);
        }
    }

    @Override
    public void initData() {
        onRequestDetailInfo();
    }


    @OnClick({R.id.common_left_iv, R.id.add_bookshelf_tv, R.id.start_reading_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.add_bookshelf_tv:
                onAddBookShelfRequest();
                break;
            case R.id.start_reading_tv:
                if (mBookDetailInfoResponse != null) {
                    if (mBookDetailInfoResponse.getDetail().getGenre() == 1) {
                        Intent intent = new Intent(this, ReadActivity.class);
                        intent.putExtra("bookId", mBookId);
                        intent.putExtra("catalogueId", mBookDetailInfoResponse.getLast_catalogue_id());
                        intent.putExtra("is_book_detail_activity", true);
                        startActivity(intent);//阅读页面 章节默认第一章节;
                    } else if (mBookDetailInfoResponse.getDetail().getGenre() == 2) {
                        ReadBookActivity.start(this, mBookId, mBookDetailInfoResponse.getLast_catalogue_id());
                    }
                }
                break;
        }
    }

    /**
     * 请求漫画详情
     */
    private void onRequestDetailInfo() {
        BookDetailRequest bookDetailRequest = new BookDetailRequest();
        bookDetailRequest.token = mBuProcessor.getToken();
        bookDetailRequest.book_id = mBookId;
        mPresenter.onRequestBookDetailInfo(bookDetailRequest);
    }

    @Override
    public void getBookDetailInfoSuccess(BookDetailInfoResponse bookDetailInfoResponse) {
        mBookDetailInfoResponse = bookDetailInfoResponse;
        mLabelAdapter.setNewData(bookDetailInfoResponse.getDetail().getLabel());
        mBookTv.setText(mBookDetailInfoResponse.getDetail().getBook_name());
        mImageLoaderHelper.displayImage(this, mBookDetailInfoResponse.getDetail().getDetail_cover(), mCoverIv, R.mipmap.default_detail);
        if (bookDetailInfoResponse.getDetail().getState() == 0) {//0未加入书架1已加入书架
            mAddBookshelfTv.setText(getString(R.string.BookDetailActivity_add_bookshelf));//BookDetailActivity_add_bookshelf_ed
        } else {
            mAddBookshelfTv.setText(getString(R.string.BookDetailActivity_add_bookshelf_ed));
        }
        mIsBook = bookDetailInfoResponse.getDetail().getGenre() != 1; //1 漫画  2 小说
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mXTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 加入书架
     */
    private void onAddBookShelfRequest() {
        AddBookShelfRequest addBookShelfRequest = new AddBookShelfRequest();
        addBookShelfRequest.token = mBuProcessor.getToken();
        addBookShelfRequest.book_id = mBookId;
        mPresenter.onAddBookShelfRequest(addBookShelfRequest);
    }

    @Override
    public void getBookShelfSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BOOKSHELF));
        mAddBookshelfTv.setText(getString(R.string.BookDetailActivity_add_bookshelf_ed));
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            Bundle bundle = new Bundle();
            bundle.putString("bookId", mBookId);
            bundle.putBoolean("isBook", mIsBook);
            BookDetailFragment bookDetailFragment = new BookDetailFragment();
            SelectionDetailFragment selectionDetailFragment = new SelectionDetailFragment();
            bookDetailFragment.setArguments(bundle);//数据传递到fragment中
            selectionDetailFragment.setArguments(bundle);//数据传递到fragment中
            fragments.add(bookDetailFragment);
            fragments.add(selectionDetailFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    private void initInjectData() {
        DaggerBookDetailComponent.builder().appComponent(getAppComponent())
                .bookDetailModule(new BookDetailModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
