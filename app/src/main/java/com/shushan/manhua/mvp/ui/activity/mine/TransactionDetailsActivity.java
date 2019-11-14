package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerTransactionDetailsComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.TransactionDetailsModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.ExpensesRecordFragment;
import com.shushan.manhua.mvp.ui.fragment.transactionDetails.RechargeRecordFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 交易明细
 */
public class TransactionDetailsActivity extends BaseActivity implements TransactionDetailsControl.TransactionDetailsView{

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    String[] titles = {"充值记录", "消费记录"};

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_transaction_details);
        initInjectData();
    }

    @Override
    public void initView() {
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {
        mCommonTitleTv.setText("交易明细");
    }

    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            RechargeRecordFragment rechargeRecordFragment = new RechargeRecordFragment();
            ExpensesRecordFragment expensesRecordFragment = new ExpensesRecordFragment();
            fragments.add(rechargeRecordFragment);
            fragments.add(expensesRecordFragment);
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
        DaggerTransactionDetailsComponent.builder().appComponent(getAppComponent())
                .transactionDetailsModule(new TransactionDetailsModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
