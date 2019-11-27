package com.shushan.manhua.mvp.ui.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMainComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.LoginTouristModeRequest;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.entity.response.LoginTouristModeResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.adapter.MyFragmentAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.SelectChannelDialog;
import com.shushan.manhua.mvp.ui.dialog.SelectManHuaTypeDialog;
import com.shushan.manhua.mvp.ui.fragment.bookshelf.BookShelfFragment;
import com.shushan.manhua.mvp.ui.fragment.home.HomeFragment;
import com.shushan.manhua.mvp.ui.fragment.mine.MineFragment;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.shushan.manhua.mvp.views.MyNoScrollViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainControl.MainView, SelectChannelDialog.SelectChannelDialogListener, SelectManHuaTypeDialog.SelectManHuaTypeDialogListener {

    @Inject
    MainControl.PresenterMain mPresenter;
    @BindView(R.id.main_bottom_navigation)
    BottomNavigationView mMainBottomNavigation;
    @BindView(R.id.main_viewpager)
    MyNoScrollViewPager mMainViewpager;
    public static final int SWITCH_HOME_PAGE = 0;
    public static final int SWITCH_TEACHER_PAGE = 1;
    public static final int SWITCH_MINE_PAGE = 2;

    private User mUser;
    private BookTypeResponse mBookTypeResponse;//选择漫画类型

    public static void start(Context context, int switchPage) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("switchPage", switchPage);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.SWITCH_TO_HOME_PAGE)) {
                mMainViewpager.setCurrentItem(SWITCH_HOME_PAGE);
                mMainBottomNavigation.setSelectedItemId(R.id.action_home);
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.SWITCH_TO_HOME_PAGE);
    }

    @Override
    public void initView() {
        mMainBottomNavigation.setItemIconTintList(null);
        LogUtils.e("mUser:" + new Gson().toJson(mUser));
        if (!mBuProcessor.isSetChannel()) {
            showSelectChannelDialog();
            onRequestManHuaType();
//            startActivitys(LoginActivity.class);
//            finish();
        } else {
            initMainView();
        }
    }

    private void initMainView() {
        List<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        BookShelfFragment bookShelfFragment = new BookShelfFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(bookShelfFragment);
        fragments.add(mineFragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mMainViewpager.setOffscreenPageLimit(fragments.size());
        mMainViewpager.setAdapter(adapter);
        mMainBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            int switchPage = getIntent().getIntExtra("switchPage", 0);
            if (switchPage == SWITCH_TEACHER_PAGE) {
                mMainViewpager.setCurrentItem(SWITCH_TEACHER_PAGE);
                mMainBottomNavigation.setSelectedItemId(R.id.action_teacher);
            }
        }
        checkPermissions();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("exitLogin", false)) {
            mSharePreferenceUtil.setData("first_guide", true);//设置引导页为true
            startActivitys(LoginActivity.class);
            finish();
        }
    }


    /**
     * 请求漫画类型
     */
    private void onRequestManHuaType() {
        mPresenter.onRequestManHuaType();
    }

    @Override
    public void getManHuaTypeSuccess(BookTypeResponse bookTypeResponse) {
        mBookTypeResponse = bookTypeResponse;
    }


    /**
     * 第一次选择频道
     */
    private void showSelectChannelDialog() {
        SelectChannelDialog selectChannelDialog = SelectChannelDialog.newInstance();
        selectChannelDialog.setListener(this);
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), selectChannelDialog, SelectChannelDialog.TAG);
    }

    @Override
    public void selectChannelBtnOkListener(int sex) {
        mSharePreferenceUtil.setData(Constant.CHANNEL, String.valueOf(sex));
        showSelectManHuaTypeDialog();
    }


    /**
     * 第一次选择漫画类型
     */
    private void showSelectManHuaTypeDialog() {
        SelectManHuaTypeDialog selectManHuaTypeDialog = SelectManHuaTypeDialog.newInstance();
        selectManHuaTypeDialog.setData(mBookTypeResponse.getData());
        selectManHuaTypeDialog.setListener(this, mImageLoaderHelper);
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), selectManHuaTypeDialog, SelectManHuaTypeDialog.TAG);
    }

    @Override
    public void selectManHuaTypeBtnOkListener(String chooseListStr) {
        LogUtils.e("chooseListStr:" + chooseListStr);
        mSharePreferenceUtil.setData(Constant.BOOK_TYPE, chooseListStr);//[1,2,3]喜欢的类型
        loginTouristMode();
    }

    /**
     * 进行游客模式登陆
     */
    private void loginTouristMode() {
        LoginTouristModeRequest request = new LoginTouristModeRequest();
        request.deviceId = SystemUtils.getUUID(this, mSharePreferenceUtil);
        mPresenter.onLoginTouristModeRequest(request);
    }

    @Override
    public void getLoginTouristModeSuccess(LoginTouristModeResponse loginTouristModeResponse) {
        LoginTouristModeResponse.UserinfoBean userinfoBean = loginTouristModeResponse.getUserinfo();
        User user = new User(userinfoBean.getToken(), userinfoBean.getName(), userinfoBean.getHead_portrait(), userinfoBean.getVip(), userinfoBean.getVip_end_time(), userinfoBean.getChannel(), new Gson().toJson(userinfoBean.getBook_type()));
        mBuProcessor.setLoginUser(user);
        initMainView();
    }

    /**
     * 检查app 权限
     */
    @SuppressLint("CheckResult")
    private void checkPermissions() {
        RxPermissions mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(permission -> {
            if (permission) {
//                reqLoginInfo();
            } else {
                showToast("请允许权限");
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        resetToDefaultIcon();//重置到默认不选中图片
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                //在这里替换图标
                menuItem.setIcon(R.mipmap.bottom_bar_bookcity_choose);
                mMainViewpager.setCurrentItem(SWITCH_HOME_PAGE, false);
                break;
            case R.id.action_teacher:
                menuItem.setIcon(R.mipmap.bottom_bar_bookshelf_choose);
                mMainViewpager.setCurrentItem(SWITCH_TEACHER_PAGE, false);
                break;
            case R.id.action_mine:
                menuItem.setIcon(R.mipmap.bottom_bar_my_choose);
                mMainViewpager.setCurrentItem(SWITCH_MINE_PAGE, false);
                break;
        }
        return true;
    }

    private void resetToDefaultIcon() {
        MenuItem home = mMainBottomNavigation.getMenu().findItem(R.id.action_home);
        MenuItem mine = mMainBottomNavigation.getMenu().findItem(R.id.action_teacher);
        MenuItem more = mMainBottomNavigation.getMenu().findItem(R.id.action_mine);
        home.setIcon(R.mipmap.bottom_bar_bookcity);
        mine.setIcon(R.mipmap.bottom_bar_bookshelf);
        more.setIcon(R.mipmap.bottom_bar_my);
    }


    /**
     * 捕捉返回事件按钮
     * <p>
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private long exitTime = 0;

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getResources().getString(R.string.main_exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    private void initInjectData() {
        DaggerMainComponent.builder().appComponent(getAppComponent())
                .mainModule(new MainModule(MainActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
