package com.shushan.manhua.mvp.ui.activity.user;

import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.nick_tv)
    TextView mNickTv;
    @BindView(R.id.sex_tv)
    TextView mSexTv;
    @BindView(R.id.birthday_tv)
    TextView mBirthdayTv;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_personal_info);
        StatusBarUtil.setTransparentForImageView(this, null);
    }

    @Override
    public void initView() {
//        BuyAdapter
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_back_iv, R.id.common_title_tv, R.id.nick_tv, R.id.birthday_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.common_title_tv://保存

                break;
            case R.id.nick_tv:
                break;
            case R.id.birthday_tv:
                break;
        }
    }
}
