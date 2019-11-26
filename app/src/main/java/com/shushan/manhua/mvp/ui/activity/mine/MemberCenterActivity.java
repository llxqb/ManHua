package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMemberCenterComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MemberCenterModule;
import com.shushan.manhua.entity.response.BuyResponse;
import com.shushan.manhua.entity.response.ProfitResponse;
import com.shushan.manhua.mvp.ui.adapter.BuyAdapter;
import com.shushan.manhua.mvp.ui.adapter.ProfitAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity implements MemberCenterControl.MemberCenterView {

    @BindView(R.id.money_tv)
    TextView mMoneyTv;
    @BindView(R.id.no_vip_layout)
    LinearLayout mNoVipLayout;//非VIP
    @BindView(R.id.vip_layout)
    LinearLayout mVipLayout;//VIP
    @BindView(R.id.avatar_iv)
    ImageView mAvatarIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.vip_profit_recycler_view)
    RecyclerView mVipProfitRecyclerView;
    @BindView(R.id.profit_text_tv)
    TextView mProfitTextTv;
    @BindView(R.id.profit_img_iv)
    ImageView mProfitImgIv;
    private List<BuyResponse> buyResponseList = new ArrayList<>();
    private List<ProfitResponse> profitResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_member_center);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        BuyAdapter mBuyAdapter = new BuyAdapter(buyResponseList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mBuyAdapter);
        mBuyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BuyResponse buyResponse = (BuyResponse) adapter.getItem(position);
            for (BuyResponse buyResponse1 : buyResponseList) {
                if (buyResponse1.isCheck) {
                    buyResponse1.isCheck = false;
                }
            }
            if (buyResponse != null) {
                buyResponse.isCheck = true;
            }
            adapter.notifyDataSetChanged();
        });
        ProfitAdapter mProfitAdapter = new ProfitAdapter(profitResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVipProfitRecyclerView.setLayoutManager(linearLayoutManager);
        mVipProfitRecyclerView.setAdapter(mProfitAdapter);
        mProfitAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_01));
                    mProfitImgIv.setImageResource(R.mipmap.koin_setiap_hari);
                    break;
                case 1:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_02));
                    mProfitImgIv.setImageResource(R.mipmap.batasan_gratis);
                    break;
                case 2:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_03));
                    mProfitImgIv.setImageResource(R.mipmap.potongan_untuk_anggota);
                    break;
                case 3:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_04));
                    mProfitImgIv.setImageResource(R.mipmap.komentar_langsung_anggota);
                    break;
                case 4:
                    mProfitTextTv.setText(getResources().getString(R.string.MemberCenterActivity_profit_05));
                    mProfitImgIv.setImageResource(R.mipmap.my_identitas);
                    break;
            }
        });
    }


    @Override
    public void initData() {
        for (int i = 0; i < 4; i++) {
            BuyResponse buyResponse = new BuyResponse();
            buyResponseList.add(buyResponse);
        }
        String[] profitName = {getResources().getString(R.string.MemberCenterActivity_beans_everyday), getResources().getString(R.string.MemberCenterActivity_restriction), getResources().getString(R.string.MemberCenterActivity_discount), getResources().getString(R.string.MemberCenterActivity_barrage), getResources().getString(R.string.MemberCenterActivity_Identity)};
        int[] profitIcon = {R.mipmap.beans2, R.mipmap.vip_free_works, R.mipmap.vip_discount, R.mipmap.vip_barrage, R.mipmap.vip_honorable_status};
        for (int i = 0; i < profitName.length; i++) {
            ProfitResponse profitResponse = new ProfitResponse();
            profitResponse.cover = profitIcon[i];
            profitResponse.name = profitName[i];
            profitResponseList.add(profitResponse);
        }
    }


    @OnClick({R.id.pay_tv, R.id.common_back_iv, R.id.get_beans_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_tv:
                break;
            case R.id.common_back_iv:
                break;
            case R.id.get_beans_tv:
                break;
        }
    }

    private void initInjectData() {
        DaggerMemberCenterComponent.builder().appComponent(getAppComponent())
                .memberCenterModule(new MemberCenterModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
