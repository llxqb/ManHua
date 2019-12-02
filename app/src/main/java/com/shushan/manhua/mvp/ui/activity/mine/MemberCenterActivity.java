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
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.request.ReceiovedBeanByVipRequest;
import com.shushan.manhua.entity.response.MemberCenterResponse;
import com.shushan.manhua.entity.response.ProfitResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.BuyAdapter;
import com.shushan.manhua.mvp.ui.adapter.ProfitAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity implements MemberCenterControl.MemberCenterView {

    @Inject
    MemberCenterControl.PresenterMemberCenter mPresenter;
    @BindView(R.id.money_tv)
    TextView mMoneyTv;
    @BindView(R.id.no_vip_layout)
    LinearLayout mNoVipLayout;//非VIP
    @BindView(R.id.vip_layout)
    LinearLayout mVipLayout;//VIP
    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.get_beans_tv)
    TextView mGetBeansTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.vip_profit_recycler_view)
    RecyclerView mVipProfitRecyclerView;//VIP权益
    @BindView(R.id.profit_text_tv)
    TextView mProfitTextTv;
    @BindView(R.id.profit_img_iv)
    ImageView mProfitImgIv;
    private List<MemberCenterResponse.VipinfoBean> buyResponseList = new ArrayList<>();
    private List<ProfitResponse> profitResponseList = new ArrayList<>();
    private BuyAdapter mBuyAdapter;
    private MemberCenterResponse mMemberCenterResponse;
    private User mUser;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_member_center);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initView() {
        if (mUser.vip == 0) {
            mVipLayout.setVisibility(View.GONE);
            mNoVipLayout.setVisibility(View.VISIBLE);
        } else {
            mVipLayout.setVisibility(View.VISIBLE);
            mNoVipLayout.setVisibility(View.GONE);
        }
        initRecyclerView();
        onRequestMemberCenter();
    }

    private void initRecyclerView() {
        mBuyAdapter = new BuyAdapter(buyResponseList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mBuyAdapter);
        mBuyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MemberCenterResponse.VipinfoBean vipinfoBean = (MemberCenterResponse.VipinfoBean) adapter.getItem(position);
            for (MemberCenterResponse.VipinfoBean vipinfoBean1 : buyResponseList) {
                if (vipinfoBean1.isCheck) {
                    vipinfoBean1.isCheck = false;
                }
            }
            if (vipinfoBean != null) {
                vipinfoBean.isCheck = true;
                String money = "$ " + vipinfoBean.getPrice();
                mMoneyTv.setText(money);
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
                finish();
                break;
            case R.id.get_beans_tv: //领取漫豆
                if (mUser.vip == 0) {
                    //提示开通会员领取
                    showToast("Gratis untuk anggota");
                } else {
                    //会员领取漫豆
                    onRequestReceivedBeanByVip();
                }
                break;
        }
    }


    /**
     * 请求会员中心数据
     */
    private void onRequestMemberCenter() {
        MemberCenterRequest memberCenterRequest = new MemberCenterRequest();
        memberCenterRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMemberCenter(memberCenterRequest);
    }

    @Override
    public void getMemberCenterResponse(MemberCenterResponse memberCenterResponse) {
        mMemberCenterResponse = memberCenterResponse;
        buyResponseList = memberCenterResponse.getVipinfo();
        MemberCenterResponse.UserinfoBean userinfoBean = memberCenterResponse.getUserinfo();
        setUserDate(userinfoBean);
        for (int i = 0; i < memberCenterResponse.getVipinfo().size(); i++) {
            MemberCenterResponse.VipinfoBean vipinfoBean = memberCenterResponse.getVipinfo().get(i);
            if (i == 0) {
                vipinfoBean.isCheck = true;
            }
        }
        mBuyAdapter.setNewData(memberCenterResponse.getVipinfo());
        if (memberCenterResponse.getVipinfo().size() > 0) {
            String money = "$ " + memberCenterResponse.getVipinfo().get(0).getPrice();
            mMoneyTv.setText(money);
        }
        if (memberCenterResponse.getVipget_state() == 0) {//0未领取1已领取
            mGetBeansTv.setText(getString(R.string.MemberCenterActivity_get_beans));
            mGetBeansTv.setBackgroundResource(R.drawable.gradient_get_beans_bg);
        } else {
            mGetBeansTv.setText(getString(R.string.MemberCenterActivity_get_beans_ed));
            mGetBeansTv.setBackgroundResource(R.drawable.bg_gray_round_solid_20);
        }
    }


    private void setUserDate(MemberCenterResponse.UserinfoBean userinfoBean) {
        if (userinfoBean != null) {
            mImageLoaderHelper.displayImage(this, userinfoBean.getHead_portrait(), mAvatarIv, Constant.LOADING_AVATOR);
//            if (userinfoBean.getVip() == 0) {//是否是VIP
//                mVipIcon.setImageResource(R.mipmap.vip_gray);
//                mNotVipLayout.setVisibility(View.VISIBLE);
//                mTopIv.setImageResource(R.mipmap.my_background);
//            } else {
//                mVipIcon.setImageResource(R.mipmap.recharge_vip);
//                mNotVipLayout.setVisibility(View.GONE);
//                mTopIv.setImageResource(R.mipmap.my_background2);
//            }
        }
    }

    /**
     * VIP每日领取漫豆
     */
    private void onRequestReceivedBeanByVip() {
        ReceiovedBeanByVipRequest receiovedBeanByVipRequest = new ReceiovedBeanByVipRequest();
        receiovedBeanByVipRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestReceivedBeanByVip(receiovedBeanByVipRequest);
    }


    @Override
    public void getReceivedBeanByVipSuccess() {

    }


    private void initInjectData() {
        DaggerMemberCenterComponent.builder().appComponent(getAppComponent())
                .memberCenterModule(new MemberCenterModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
