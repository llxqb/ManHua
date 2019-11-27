package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.response.BarrageStyleResponse;
import com.shushan.manhua.entity.response.ChapterResponse;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.listener.SoftKeyBoardListener;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.activity.setting.SettingActivity;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.adapter.ReadingPicAdapter;
import com.shushan.manhua.mvp.ui.adapter.RecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.AddBarrageDialog;
import com.shushan.manhua.mvp.ui.dialog.BarrageSoftKeyPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.BarrageStylePopupWindow;
import com.shushan.manhua.mvp.ui.dialog.CommentSoftKeyPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadBeansExchangeDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadContentsPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadOpenVipDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadSettingPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadUseCoinDialog;
import com.shushan.manhua.mvp.utils.SoftKeyboardUtil;
import com.zhouwei.mzbanner.MZBannerView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读页面父类
 */
public abstract class ReadBaseActivity extends BaseActivity implements ReadControl.ReadView, ReadUseCoinDialog.ReadUseCoinDialogListener, ReadBeansExchangeDialog.ReadBeansExchangeDialogListener,
        ReadOpenVipDialog.ReadOpenVipDialogListener, ReadSettingPopupWindow.ReadSettingPopupWindowListener, BarrageStylePopupWindow.BarrageStylePopupWindowListener,
        BarrageSoftKeyPopupWindow.BarrageSoftKeyPopupWindowListener, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener, TakePhoto.TakeResultListener,
        InvokeListener, AddBarrageDialog.AddBarrageDialogListener {
    @Inject
    ReadControl.PresenterRead mPresenter;
    @BindView(R.id.read_layout)
    RelativeLayout mReadLayout;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_right_tv)
    TextView mCommonRightTv;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.barrage_iv)
    ImageView mBarrageIv;//弹幕开关
    @BindView(R.id.send_message_ll)
    LinearLayout mSendMessageLl;
    @BindView(R.id.send_message_left_iv)
    ImageView mSendMessageLeftIv;
    @BindView(R.id.send_message_right_iv)
    ImageView mSendMessageRightIv;
    @BindView(R.id.message_et)
    EditText mMessageEt;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.pic_recycler_view)
    RecyclerView mPicRecyclerView;
    //    @BindView(R.id.resizableImageView)
//    ResizableImageView mResizableImageView;
    @BindView(R.id.add_bookshelf_iv)
    ImageView mAddBookshelfIv;
    @BindView(R.id.back_top_iv)
    ImageView mBackTopIv;
    @BindView(R.id.read_bottom_ll)
    LinearLayout mReadBottomLl;//底部布局
    @BindView(R.id.barrage_ll)
    LinearLayout mBarrageLl;//弹幕布局
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    boolean mBarrage;//是否弹幕
    private List<RecommendBean> readingRecommendResponseList = new ArrayList<>();//推荐list
    private List<CommentBean> readingCommendResponseList = new ArrayList<>();//评论
    private List<ChapterResponse> chapterResponseList = new ArrayList<>();//章节list
    private List<BarrageStyleResponse> barrageStyleResponseList = new ArrayList<>();//弹幕样式list
    private List<String> bookPicList = new ArrayList<>();//漫画章节图片
    private Integer[] barrageStyleIcon = {R.mipmap.barrage0, R.mipmap.barrage1, R.mipmap.barrage2, R.mipmap.barrage3, R.mipmap.barrage4, R.mipmap.barrage5};
    //是否是弹幕状态否则是评论状态 false
    private boolean isBarrageState = true;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //成功取得照片
    Bitmap bitmap;
    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    private CommentSoftKeyPopupWindow mCommentSoftKeyPopupWindow;//评论弹幕
    /**
     * 当前选择的弹幕样式
     */
    private int mBarrageStyle = 0;
    private boolean isShowBackTopIv = false;//是否显示返回顶部图片按钮
    public String mBookId;
    public int mCatalogueId;//章节id
    public ReadingPicAdapter mReadingPicAdapter;//章节图片adapter
    public ReadingCommentAdapter mReadingCommentAdapter;//评价adapter
    public RecommendAdapter mRecommendAdapter;//推荐adapter

    public static void start(Context context, String bookId, int catalogueId) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("catalogueId", catalogueId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        setStatusBar();
        initInjectData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            mCatalogueId = getIntent().getIntExtra("catalogueId", 1);
        }
        mMessageEt.clearFocus();//让编辑框失去焦点 配合布局一起使用
        mBarrage = mSharePreferenceUtil.getBooleanData("barrage");
        if (mBarrage) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
        initScrollView();
        onKeyBoardListener();
        initAdapter();
    }

    private void initAdapter() {
        mRecommendAdapter = new RecommendAdapter(readingRecommendResponseList,mImageLoaderHelper);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecommendRecyclerView.setLayoutManager(linearLayoutManager);
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.comment_ll:
                    startActivitys(CommentDetailsActivity.class);
                    break;
            }
        });
        //图片adapter
        mReadingPicAdapter = new ReadingPicAdapter(bookPicList, mImageLoaderHelper);
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPicRecyclerView.setAdapter(mReadingPicAdapter);
        mReadingPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SoftKeyboardUtil.hideSoftKeyboard(ReadBaseActivity.this);
            showFunction();
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {
        mNestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            isShowBackTopIv = scrollY > mPicRecyclerView.getHeight() * 4 / 5;//大于图片的4/5 显示返回顶部按钮
            //mNestedScrollView.getChildAt(0).getMeasuredHeight()- mNestedScrollView.getMeasuredHeight()
            if (scrollY >= mPicRecyclerView.getHeight()) {//设置隐藏功能键
                isBarrageState = false;
                if (mReadBottomLl.getVisibility() == View.VISIBLE) {
                    showFunction();
                }
            } else {
                isBarrageState = true;
            }
        });
    }

    @Override
    public void initData() {
        mCommonRightTv.setVisibility(View.VISIBLE);
        mCommonRightTv.setText(getResources().getString(R.string.ReadActivity_right_title));
        for (int i = 0; i < 20; i++) {
            ChapterResponse chapterResponse = new ChapterResponse();
            chapterResponse.title = "title" + i;
            chapterResponseList.add(chapterResponse);
        }
        for (int i = 0; i < barrageStyleIcon.length; i++) {
            BarrageStyleResponse barrageStyleResponse = new BarrageStyleResponse();
            if (i == 0) {
                barrageStyleResponse.isCheck = true;
            } else {
                barrageStyleResponse.isCheck = false;
            }
            barrageStyleResponse.styleIcon = barrageStyleIcon[i];
            barrageStyleResponseList.add(barrageStyleResponse);
        }
//        showRechargeDialog();
    }


    //监听软件盘是否弹起
    private void onKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                hideLayout();
                if (isBarrageState) {//弹幕状态
                    showBarragePopupWindow();
                } else {//评论状态
                    showCommentPopupWindow();
                }
            }

            @Override
            public void keyBoardHide(int height) {//键盘隐藏 高度
                showLayout();
            }
        });
    }


    /**
     * 显示popupWindow时 隐藏的布局
     */
    private void hideLayout() {
        mSendMessageLl.setVisibility(View.GONE);
        mReadBottomLl.setVisibility(View.GONE);
        mBarrageLl.setVisibility(View.GONE);
        mBackTopIv.setVisibility(View.GONE);
        mAddBookshelfIv.setVisibility(View.GONE);
    }

    /**
     * 显示popupWindow时 显示的布局
     */
    private void showLayout() {
        mSendMessageLl.setVisibility(View.GONE);
        mReadBottomLl.setVisibility(View.GONE);
        mBarrageLl.setVisibility(View.GONE);
        mBackTopIv.setVisibility(View.GONE);
        mAddBookshelfIv.setVisibility(View.GONE);
    }

    /**
     * 显示弹幕弹框PopupWindow
     */
    private void showBarragePopupWindow() {
        new BarrageSoftKeyPopupWindow(this, this).initPopWindow(mReadLayout);
    }

    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow() {
        mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(this, this, photoList);
        mCommentSoftKeyPopupWindow.initPopWindow(mReadLayout);
    }

    /**
     * 默认进来阅读没漫豆
     * 显示去充值弹框
     */
    private void showRechargeDialog() {
        ReadUseCoinDialog readUseCoinDialog = ReadUseCoinDialog.newInstance();
        readUseCoinDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readUseCoinDialog, ReadUseCoinDialog.TAG);
    }

    /**
     * 显示开通会员弹框
     */
    private void showOpenVipDialog() {
        ReadOpenVipDialog readOpenVipDialog = ReadOpenVipDialog.newInstance();
        readOpenVipDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readOpenVipDialog, ReadOpenVipDialog.TAG);
    }

    /**
     * 显示漫豆兑换弹幕弹框
     */
    private void showBeansExchangeDialog() {
        ReadBeansExchangeDialog readBeansExchangeDialog = ReadBeansExchangeDialog.newInstance();
        readBeansExchangeDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readBeansExchangeDialog, ReadBeansExchangeDialog.TAG);
    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.send_tv, R.id.bottom_directory_ll, R.id.last_chapter_iv, R.id.next_chapter_iv, R.id.barrage_ll, R.id.send_message_left_iv, R.id.send_message_right_iv,
            R.id.support_tv, R.id.add_bookshelf_tv, R.id.share_tv, R.id.last_chapter_ll, R.id.next_chapter_ll, R.id.bottom_comment_ll, R.id.bottom_share_ll, R.id.bottom_setting_ll, R.id.back_top_iv, R.id.add_bookshelf_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv: //全集
                break;
//            case R.id.resizableImageView: //点击图片
//                SoftKeyboardUtil.hideSoftKeyboard(this);
//                showFunction();
//                break;
            case R.id.send_tv: //发送

                break;
            case R.id.barrage_ll://设置弹幕
                mBarrage = mSharePreferenceUtil.getBooleanData("barrage");
                if (mBarrage) {
                    mSharePreferenceUtil.setData("barrage", false);
                    mBarrageIv.setImageResource(R.mipmap.barrage_close);
                } else {
                    mSharePreferenceUtil.setData("barrage", true);
                    mBarrageIv.setImageResource(R.mipmap.barrage_open);
                }
                break;
            case R.id.send_message_left_iv:  //切换弹幕和评论
                switchFunction();
                break;
            case R.id.send_message_right_iv://打开选择弹幕样式  隐藏输入法 打开样式选择
                showBarrageStyle();
                break;
            case R.id.support_tv://点赞
                break;
            case R.id.add_bookshelf_tv://加入书架
                break;
            case R.id.share_tv://分享
                break;
            case R.id.last_chapter_ll://上一篇
                break;
            case R.id.next_chapter_ll://下一篇
                break;
            case R.id.last_chapter_iv: //上一话

                break;
            case R.id.next_chapter_iv: //下一话

                break;
            case R.id.bottom_directory_ll: //目录
                new ReadContentsPopupWindow(this, chapterResponseList).initPopWindow(mReadLayout);
                break;
            case R.id.bottom_comment_ll://评论

                break;
            case R.id.bottom_share_ll://分享
                break;
            case R.id.bottom_setting_ll://设置
                new ReadSettingPopupWindow(this, this, mSharePreferenceUtil).initPopWindow(mReadLayout);
                break;
            case R.id.back_top_iv:// 让页面返回顶部
                mNestedScrollView.post(() -> mNestedScrollView.post(new Runnable() {
                    public void run() {
                        // 滚动至顶部
                        mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                        // 滚动到底部
                        //sc.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }));
                break;
            case R.id.add_bookshelf_iv://加入书架
                showToast("加入书架");
                break;
        }
    }

    /**
     * 去充值弹框  去充值
     */
    @Override
    public void readUseCoinDialogBtnOkListener() {

    }

    /**
     * 漫豆兑换弹幕弹框  去兑换
     * 去充漫豆
     */
    @Override
    public void readBeansExchangeDialogBtnOkListener() {
        startActivitys(BuyActivity.class);
    }

    /**
     * 显示开通会员弹框  开通会员
     */
    @Override
    public void readOpenVipDialogBtnOkListener() {

    }

    /**
     * 点击翻页开关
     */
    @Override
    public void pageTurningBtnListener() {

    }

    /**
     * 夜间模式开关
     */
    @Override
    public void nightModelBtnListener(boolean nightModel) {
        if (nightModel) {
            //设置白天模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 120);//屏幕亮度值范围必须位于：0～255
        } else {
            //设置夜间模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
        }
    }

    /**
     * 关闭弹幕开关
     */
    @Override
    public void barrageSwitchBtnListener() {

    }

    /**
     * 更多设置
     */
    @Override
    public void clickMoreBtnListener() {
        startActivitys(SettingActivity.class);
    }


    /**
     * 弹幕模式：显示自定义软键盘弹幕布局
     * 切换弹幕或者评论
     */
    @Override
    public void switchStyleLayoutBtnListenerByBarrageSoftKey() {
        switchFunction();
    }

    /**
     * 弹幕模式：显示弹幕样式
     */
    @Override
    public void showStyleBtnListenerByBarrageSoftKey() {
        showBarrageStyle();
    }

    /**
     * 弹幕模式：发送消息
     */
    @Override
    public void sendMessageBtnListenerByBarrageSoftKey(String message) {
        showAddBarrageDialog(message);
    }

    /**
     * 显示发送弹幕弹框
     */
    private void showAddBarrageDialog(String message) {
        AddBarrageDialog addBarrageDialog = AddBarrageDialog.newInstance();
        addBarrageDialog.setListener(this);
        addBarrageDialog.setMessage(message);
        addBarrageDialog.setBarrageStyle(mBarrageStyle);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), addBarrageDialog, AddBarrageDialog.TAG);
    }

    /**
     * 发送添加弹幕
     */
    @Override
    public void addBarrageBtnOkListener(String moveTvValue) {
        showToast("弹幕值：" + moveTvValue);
    }

    /**
     * 评论弹框 切换功能
     * 切换功能
     */
    @Override
    public void switchFunctionByCommentSoftKeyBtnListener() {
        switchFunction();
    }

    @Override
    public void photoBtnListener() {
        //从相机获取图片(不裁剪)
        getTakePhoto().onPickFromCapture(uri);
    }

    @Override
    public void albumBtnListener(int maxPicNum) {
        //从相册中获取图片（不裁剪）
        getTakePhoto().onPickMultiple(maxPicNum);
    }

    /**
     * 发送评论
     */
    @Override
    public void CommentSendMessageBtnListener(List<TImage> tImageList, String content) {

    }

    /**
     * 切换弹幕或者评论
     */
    private void switchFunction() {
        if (isBarrageState) {
            isBarrageState = false;
            //切换成评论状态
            mSendMessageLeftIv.setImageResource(R.mipmap.comment_icon);
            mSendMessageRightIv.setVisibility(View.GONE);
            mMessageEt.setHint(getResources().getString(R.string.BarrageStylePopupWindow_comment_hint));
        } else {//切换成弹幕状态
            isBarrageState = true;
            mSendMessageLeftIv.setImageResource(R.mipmap.barrage_icon);
            mSendMessageRightIv.setVisibility(View.VISIBLE);
            mMessageEt.setHint(getResources().getString(R.string.BarrageStylePopupWindow_barrage_hint));
        }
    }

    /**
     * 显示弹幕样式
     */
    private void showBarrageStyle() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
        showFunction();
        new BarrageStylePopupWindow(this, barrageStyleResponseList, this).initPopWindow(mReadLayout);
    }

    @Override
    public void hintOpenVipBtnListener() {
        showOpenVipDialog();
    }

    @Override
    public void hintBeansExchangeBtnListener() {
        showBeansExchangeDialog();
    }

    /**
     * from:弹幕样式PopupWindow
     * 显示漫豆兑换弹幕弹框
     */
    @Override
    public void showBeansExchangeBtnListener() {
        showBeansExchangeDialog();
    }

    /**
     * from:弹幕样式PopupWindow
     * 显示弹幕样式:确定样式按钮
     */
    @Override
    public void switchStyleLayoutBtnListener(int style) {
        mBarrageStyle = style;
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    /**
     * 点击图片显示功能按钮
     */
    private void showFunction() {
        if (mReadBottomLl.getVisibility() == View.VISIBLE) {
            mSendMessageLl.setVisibility(View.INVISIBLE);
            mReadBottomLl.setVisibility(View.INVISIBLE);
            mBarrageLl.setVisibility(View.INVISIBLE);
            mAddBookshelfIv.setVisibility(View.INVISIBLE);
            mBackTopIv.setVisibility(View.INVISIBLE);
        } else {
            mSendMessageLl.setVisibility(View.VISIBLE);
            mReadBottomLl.setVisibility(View.VISIBLE);
            mBarrageLl.setVisibility(View.VISIBLE);
            mAddBookshelfIv.setVisibility(View.VISIBLE);
            if (isShowBackTopIv) {
                mBackTopIv.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), true);
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        showLoading();
//        photoList.add(null);
        photoList.addAll(result.getImages());
        //传到CommentSoftKeyPopupWindow
//        new CommentSoftKeyPopupWindow(ReadActivity.this, ReadActivity.this, photoList).initPopWindow(mReadLayout);
        mCommentSoftKeyPopupWindow.setListData(photoList, this);

    }

    private void initInjectData() {
        DaggerReadComponent.builder().appComponent(getAppComponent())
                .readModule(new ReadModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
