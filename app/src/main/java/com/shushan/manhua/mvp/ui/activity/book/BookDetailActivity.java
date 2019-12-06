package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
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
import com.shushan.manhua.entity.response.LabelResponse;
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
import io.reactivex.annotations.Nullable;

/**
 * 漫画书籍详情
 */
public class BookDetailActivity extends BaseActivity implements BookDetailControl.BookDetailView {

    @Inject
    BookDetailControl.PresenterBookDetail mPresenter;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<LabelResponse> labelResponseList = new ArrayList<>();
    String[] titles;
    String mBookId;
    String mBookCover;
    private BookDetailInfoResponse mBookDetailInfoResponse;

    public static void start(Context context, String bookId, String bookCover) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("bookCover", bookCover);
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
            mBookCover = getIntent().getStringExtra("bookCover");
            mImageLoaderHelper.displayImage(this, mBookCover, mCoverIv, R.mipmap.default_detail);
            titles = new String[]{getResources().getString(R.string.BookDetailActivity_detail_tv), getResources().getString(R.string.BookDetailActivity_selection_tv)};
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
            mXTabLayout.setupWithViewPager(mViewPager);
            LabelAdapter mLabelAdapter = new LabelAdapter(labelResponseList);
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
                    BookDetailInfoResponse.HistoryBean historyBean = mBookDetailInfoResponse.getHistory();
                    if (historyBean != null) {
                        Intent intent = new Intent(this,ReadActivity.class);
                        intent.putExtra("bookId",mBookId);
                        intent.putExtra("catalogueId",historyBean.getCatalogue_id());
                        intent.putExtra("bookCover",mBookCover);
                        startActivityForResult(intent,100);//继续阅读
//                        ReadActivity.start(this, mBookId, historyBean.getCatalogue_id(), mBookCover);//继续阅读
                    } else {
                        Intent intent = new Intent(this,ReadActivity.class);
                        intent.putExtra("bookId",mBookId);
                        intent.putExtra("catalogueId",1);
                        intent.putExtra("bookCover",mBookCover);
                        startActivityForResult(intent,100);//阅读页面 章节默认第一章节;
//                        ReadActivity.start(this, mBookId, 1, mBookCover);//阅读页面 章节默认第一章节;
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
        showToast("add success");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BOOKSHELF));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
//        if (requestCode == 100 && resultCode == 101) {
//        }
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            BookDetailFragment bookDetailFragment = BookDetailFragment.getInstance(mBookId);
            SelectionDetailFragment selectionDetailFragment = SelectionDetailFragment.getInstance(mBookId);
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
