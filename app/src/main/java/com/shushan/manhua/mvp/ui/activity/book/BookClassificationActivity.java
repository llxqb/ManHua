package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookClassificationComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BookClassificationModule;
import com.shushan.manhua.entity.request.BookClassificationRequest;
import com.shushan.manhua.entity.response.BookClassificationResponse;
import com.shushan.manhua.mvp.ui.adapter.BookClassificationAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分类
 */
public class BookClassificationActivity extends BaseActivity implements BookClassificationControl.BookClassificationView {
    @Inject
    BookClassificationControl.PresenterBookClassification mPresenter;
    @BindView(R.id.common_title_tv)
    TextView commonTitleTv;
    @BindView(R.id.komik_tv)
    TextView mKomikTv;
    @BindView(R.id.novel_tv)
    TextView mNovelTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private BookClassificationAdapter mBookClassificationAdapter;
    private List<BookClassificationResponse.ListBean> mDataBeanList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_book_classification);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        commonTitleTv.setText("Jenis");
        mBookClassificationAdapter = new BookClassificationAdapter(mDataBeanList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mBookClassificationAdapter);
        mBookClassificationAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookClassificationResponse.ListBean listBean = (BookClassificationResponse.ListBean) adapter.getItem(position);
            BookDetailActivity.start(BookClassificationActivity.this, String.valueOf(listBean.getBook_id()));
        });
//        titles = new String[]{getResources().getString(R.string.HomeFragment_title_comic), getResources().getString(R.string.HomeFragment_title_novel)};
//        mViewPager.setOffscreenPageLimit(2);
//        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));//子fragment用getChildFragmentManager()
//        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {
        onRequestClassification("1");
    }


    @OnClick({R.id.common_left_iv, R.id.komik_tv, R.id.novel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.komik_tv://漫画
                mKomikTv.setTextColor(getResources().getColor(R.color.buy_check_color));
                mKomikTv.setBackgroundResource(R.drawable.tab_background_selected);
                mNovelTv.setTextColor(getResources().getColor(R.color.color_80));
                mNovelTv.setBackgroundResource(R.drawable.tab_background_unselected);
                onRequestClassification("1");
                break;
            case R.id.novel_tv:
                mNovelTv.setTextColor(getResources().getColor(R.color.buy_check_color));
                mNovelTv.setBackgroundResource(R.drawable.tab_background_selected);
                mKomikTv.setTextColor(getResources().getColor(R.color.color_80));
                mKomikTv.setBackgroundResource(R.drawable.tab_background_unselected);
                onRequestClassification("2");
                break;
        }
    }

    /**
     * type : 1漫画2小说
     */
    private void onRequestClassification(String type) {
        BookClassificationRequest request = new BookClassificationRequest();
        request.token = mBuProcessor.getToken();
        request.type = type;
        mPresenter.onRequestClassification(request);
    }

    @Override
    public void getClassificationSuccess(BookClassificationResponse bookClassificationResponse) {
        mBookClassificationAdapter.setNewData(bookClassificationResponse.getList());
    }


//    private class MyPageAdapter extends FragmentPagerAdapter {
//        private List<Fragment> fragments = new ArrayList<Fragment>();
//
//        MyPageAdapter(FragmentManager fm) {
//            super(fm);
//            HomeComicFragment homeComicFragment = new HomeComicFragment();//漫画
//            HomeNovelFragment homeNovelFragment = new HomeNovelFragment();//小说
//            fragments.add(homeComicFragment);
//            fragments.add(homeNovelFragment);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return titles.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
//    }

    private void initInjectData() {
        DaggerBookClassificationComponent.builder().appComponent(getAppComponent())
                .bookClassificationModule(new BookClassificationModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
