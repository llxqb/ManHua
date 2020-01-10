package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.request.BuyBarrageStyleRequest;
import com.shushan.manhua.entity.request.ExchangeBarrageStyleRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SendBarrageRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.BarrageStyleResponse;
import com.shushan.manhua.entity.response.BuyBarrageStyleResponse;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.listener.MyUMShareListener;
import com.shushan.manhua.listener.SoftKeyBoardListener;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.activity.mine.MemberCenterActivity;
import com.shushan.manhua.mvp.ui.adapter.BannerReadingViewHolder;
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
import com.shushan.manhua.mvp.ui.dialog.SharePopupWindow;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.PicUtils;
import com.shushan.manhua.mvp.utils.SoftKeyboardUtil;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

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
 * 阅读漫画页面父类
 */
public abstract class ReadBaseActivity extends BaseActivity implements ReadControl.ReadView, ReadUseCoinDialog.ReadUseCoinDialogListener, ReadBeansExchangeDialog.ReadBeansExchangeDialogListener,
        ReadOpenVipDialog.ReadOpenVipDialogListener, ReadSettingPopupWindow.ReadSettingPopupWindowListener, BarrageStylePopupWindow.BarrageStylePopupWindowListener,
        BarrageSoftKeyPopupWindow.BarrageSoftKeyPopupWindowListener, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener, TakePhoto.TakeResultListener, InvokeListener,
        AddBarrageDialog.AddBarrageDialogListener, SharePopupWindow.PopupWindowShareListener, ReadContentsPopupWindow.ReadContentsPopupWindowListener, MyUMShareListener.ShareResultListener {
    @Inject
    ReadControl.PresenterRead mPresenter;
    @BindView(R.id.read_layout)
    RelativeLayout mReadLayout;
    @BindView(R.id.common_toolbar_layout)
    RelativeLayout mCommonToolbarLayout;
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
    RelativeLayout mSendMessageLl;
    @BindView(R.id.send_message_left_iv)
    ImageView mSendMessageLeftIv;
    @BindView(R.id.send_message_right_iv)
    ImageView mSendMessageRightIv;
    @BindView(R.id.message_et)
    TextView mMessageEt;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.pic_recycler_view)
    RecyclerView mPicRecyclerView;
    @BindView(R.id.support_tv)
    TextView mSupportTv;
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
    @BindView(R.id.top_btn)
    Button mTopBtn;
    @BindView(R.id.bottom_btn)
    Button mBottomBtn;
    //    boolean mBarrage;//是否弹幕
    List<BannerBean> bannerList = new ArrayList<>();
    private List<RecommendBean> readingRecommendResponseList = new ArrayList<>();//推荐list
    private List<CommentBean> readingCommendResponseList = new ArrayList<>();//评论
    private List<BarrageStyleResponse> barrageStyleResponseList = new ArrayList<>();//弹幕样式list
    private List<ReadingInfoResponse.CatalogueBean.CatalogueContentBean> bookPicList = new ArrayList<>();//漫画章节图片
    private Integer[] barrageStyleIcon = {R.mipmap.barrage0, R.mipmap.barrage1, R.mipmap.barrage2, R.mipmap.barrage3, R.mipmap.barrage4, R.mipmap.barrage5};
    private Integer[] barrageStyleType = {0, 1, 1, 2, 1, 2};
    //是否是弹幕状态否则是评论状态 false
    private boolean isBarrageState = true;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    private CommentSoftKeyPopupWindow mCommentSoftKeyPopupWindow;//评论弹幕
    /**
     * 当前选择的弹幕样式
     */
    private int mBarrageStyle = 0;
    public String mBookId;
    public int mCatalogueId;// 当前章节id
    private boolean isBookDetailActivityTo = false;//是否是从书籍详情页跳转过来
    public ReadingPicAdapter mReadingPicAdapter;//章节图片adapter
    public ReadingCommentAdapter mReadingCommentAdapter;//评价adapter
    public RecommendAdapter mRecommendAdapter;//推荐adapter
    public ReadingInfoResponse mReadingInfoResponse;
    public SelectionResponse mSelectionResponse;
    public int page = 1;
    public int picRvHeight;//图片recyclerView总高度
    public int mCurrentHeight = 0;//当前高度
    public User mUser;
    public BarrageListResponse mBarrageListResponse;//弹幕集合
    public BuyBarrageStyleResponse mBuyBarrageStyleResponse;//用户购买弹幕样式列表
    public ReadUseCoinDialog mReadUseCoinDialog;//非免费章节 弹出购买弹框
    /**
     * 上传成功后图片集合
     */
    private List<String> mPicList = new ArrayList<>();
    boolean mBarrageFlag;//弹幕开关
    boolean mTurnPageFlag;//上下翻页
    boolean mNightModelFlag;//夜间模式
    int mTransparency;
    int mPlaySpeed;
    public int mLoginModel;//1 是游客模式 2 是登录模式
    private CommentBean mCommentBean;
    private int clickPos;
    //    private boolean isClickTopOrBottomLayout = false;
    public ReadSettingPopupWindow mReadSettingPopupWindow;//弹幕设置弹框
//    public Intent mIntent;

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
        logViewContentEvent();
    }

    /**
     * 查看内容
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logViewContentEvent() {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "阅读漫画页面");
        logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, params);
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        setStatusBar();
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        mLoginModel = mBuProcessor.getLoginModel();
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            mCatalogueId = getIntent().getIntExtra("catalogueId", 1);
            isBookDetailActivityTo = getIntent().getBooleanExtra("is_book_detail_activity", false);
            if (!TextUtils.isEmpty(mBookId)) {
                onRequestReadingInfo();
            }
        }
        mMessageEt.clearFocus();//让编辑框失去焦点 配合布局一起使用
        onKeyBoardListener();
        initAdapter();
        initScrollView();
    }

    private void initAdapter() {
        mRecommendAdapter = new RecommendAdapter(readingRecommendResponseList, mImageLoaderHelper);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecommendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RecommendBean dataBean = (RecommendBean) adapter.getItem(position);
            if (dataBean != null) {
                BookDetailActivity.start(ReadBaseActivity.this, String.valueOf(dataBean.getBook_id()));
            }
        });
        mRecommendRecyclerView.setLayoutManager(linearLayoutManager);
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCommentBean = (CommentBean) adapter.getItem(position);
            clickPos = position;
            switch (view.getId()) {
                case R.id.comment_ll:
                    if (mCommentBean != null) {
                        CommentDetailsActivity.start(this, String.valueOf(mCommentBean.getComment_id()));
                    }
                    break;
                case R.id.suggest_num_tv:
                    onCommentSuggestRequest();
                    break;
                case R.id.content_tv:
                    showCommentPopupWindow("@" + mCommentBean.getName());
                    break;
            }
        });
        //图片adapter
        mPicRecyclerView.setNestedScrollingEnabled(false);//解决ScrollView+RecyclerView的滑动冲突问题
        mReadingPicAdapter = new ReadingPicAdapter(bookPicList, mImageLoaderHelper);
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
                picRvHeight = mPicRecyclerView.getHeight();
            }
        });
        mPicRecyclerView.setAdapter(mReadingPicAdapter);
        mReadingPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SoftKeyboardUtil.hideSoftKeyboard(this);
            if (mReadBottomLl.getVisibility() == View.VISIBLE) {
                sheClickHiddenLayout(true);
            } else {
                sheClickHiddenLayout(false);
            }
            showFunction();
        });
    }

    /**
     * 设置隐藏布局不可点击
     * * @param click   false: 点击隐藏上下区域 失去焦点  true :有焦点
     */
    public void sheClickHiddenLayout(boolean click) {
        mTurnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE, true);
        if (click) {
            if (mTurnPageFlag) {
                mTopBtn.setClickable(true);
                mBottomBtn.setClickable(true);
            }
        } else {
            mTopBtn.setClickable(false);
            mBottomBtn.setClickable(false);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {
        mNestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            LogUtils.e("scrollY:" + scrollY + " oldScrollY:" + oldScrollY);
            mCurrentHeight = scrollY;
            hideFunction();//滑动时隐藏功能键
            if (scrollY < oldScrollY) {//往上滑显示返回顶部按钮
                mBackTopIv.setVisibility(View.VISIBLE);
            } else {
                mBackTopIv.setVisibility(View.INVISIBLE);
            }
            if (scrollY >= mPicRecyclerView.getHeight()) {//设置隐藏功能键
                isBarrageState = false;
                if (mReadBottomLl.getVisibility() == View.VISIBLE) {
                    showFunction();
                }
                sheClickHiddenLayout(false);
            } else {
                isBarrageState = true;
                //显示弹幕
                if (mBarrageListResponse != null) {
                    for (BarrageListResponse.DataBean dataBean : mBarrageListResponse.getData()) {
                        if (scrollY > Double.parseDouble(dataBean.getYcoord()) - 10 && scrollY < Double.parseDouble(dataBean.getYcoord()) + 10) {
                            showTvView(dataBean);
                        }
                    }
                }
            }
        });
    }

    /**
     * 增加弹幕view
     */
    public void showTvView(BarrageListResponse.DataBean dataBean) {
        if (dataBean != null && mBarrageFlag) {
            mTransparency = mSharePreferenceUtil.getIntData(Constant.TRANSPARENCY, 80);
            int screenHeight = SystemUtils.getScreenHeight(this);
            Toast toast2 = new Toast(this);
            View view = LayoutInflater.from(this).inflate(R.layout.text_view, null);
            TextView textTv = view.findViewById(R.id.text_tv);
//            textTv.set
            switch (dataBean.getStyle_id()) {
                case 0:
                    textTv.setBackgroundResource(R.mipmap.black_bg);
                    break;
                case 1:
                    textTv.setBackgroundResource(R.mipmap.barrage1_bg);
                    break;
                case 2:
                    textTv.setBackgroundResource(R.mipmap.barrage2_bg);
                    break;
                case 3:
                    textTv.setBackgroundResource(R.mipmap.barrage3_bg);
                    break;
                case 4:
                    textTv.setBackgroundResource(R.mipmap.barrage4_bg);
                    break;
                case 5:
                    textTv.setBackgroundResource(R.mipmap.barrage5_bg);
                    break;
            }
            textTv.setText(dataBean.getBarrage_content());
            textTv.getBackground().setAlpha(255 * mTransparency / 100);//0~255透明度值
            toast2.setView(view);
//            LogUtils.e("height:" + (int) Double.parseDouble(dataBean.getYcoord()) % screenHeight);
            toast2.setGravity(Gravity.TOP | Gravity.LEFT, (int) Double.parseDouble(dataBean.getXcoord()), (int) Double.parseDouble(dataBean.getYcoord()) % screenHeight);
            toast2.show();
        }
    }

    @Override
    public void initData() {
        mCommonRightTv.setVisibility(View.VISIBLE);
        mCommonRightTv.setText(getResources().getString(R.string.ReadActivity_right_title));
    }


    //监听软件盘是否弹起
    private void onKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                hideLayout();
                if (mBarrageSoftKeyPopupWindow == null && mCommentSoftKeyPopupWindow == null) {//弹幕ppw不为空  评论ppw 不为空
                    if (isBarrageState) {//弹幕状态
                        showBarragePopupWindow();
                    } else {//评论状态
                        showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
                    }
                }
            }

            @Override
            public void keyBoardHide(int height) {//键盘隐藏 高度
                showLayout();
            }
        });
    }

    /**
     * 评论点赞
     */
    private void onCommentSuggestRequest() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = String.valueOf(mCommentBean.getComment_id());
        commentSuggestRequest.type = "4";
        mPresenter.onCommentSuggestRequest(commentSuggestRequest);
    }

    @Override
    public void getSuggestSuccess() {
        mReadingCommentAdapter.notifyItemChanged(clickPos, mCommentBean.getLike());//局部刷新
    }


    /**
     * 章节详情
     */
    public void onRequestReadingInfo() {
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.token = mBuProcessor.getToken();
        readingRequest.book_id = mBookId;
        readingRequest.catalogue_id = String.valueOf(mCatalogueId);
        mPresenter.onRequestReadingInfo(readingRequest);
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

    BarrageSoftKeyPopupWindow mBarrageSoftKeyPopupWindow;

    /**
     * 显示弹幕弹框PopupWindow
     */
    private void showBarragePopupWindow() {
        if (mBarrageSoftKeyPopupWindow == null) {
            mBarrageSoftKeyPopupWindow = new BarrageSoftKeyPopupWindow(this, this);
        }
        mBarrageSoftKeyPopupWindow.initPopWindow(mReadLayout);
    }

    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow(String editHintContent) {
        if (mCommentSoftKeyPopupWindow == null) {
            mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(this, this, photoList, editHintContent);
        }
        mCommentSoftKeyPopupWindow.initPopWindow(mReadLayout);
    }

    /**
     * 非免费章节 显示使用漫豆弹框
     */
    public void showRechargeDialog() {
        mReadUseCoinDialog = ReadUseCoinDialog.newInstance();
        mReadUseCoinDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), mReadUseCoinDialog, ReadUseCoinDialog.TAG);
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
     * 显示开通会员弹框  开通会员
     */
    @Override
    public void readOpenVipDialogBtnOkListener() {
        startActivitys(MemberCenterActivity.class);
    }

    /**
     * 显示漫豆兑换弹幕弹框
     */
    private void showBeansExchangeDialog() {
        ReadBeansExchangeDialog readBeansExchangeDialog = ReadBeansExchangeDialog.newInstance();
        readBeansExchangeDialog.setListener(this);
        readBeansExchangeDialog.setDate(mUser.bean);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readBeansExchangeDialog, ReadBeansExchangeDialog.TAG);
    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.bottom_directory_ll, R.id.last_chapter_iv, R.id.next_chapter_iv, R.id.barrage_ll, R.id.send_message_left_iv, R.id.send_message_right_iv,
            R.id.support_tv, R.id.add_bookshelf_tv, R.id.share_tv, R.id.last_chapter_ll, R.id.next_chapter_ll, R.id.bottom_comment_ll, R.id.bottom_share_ll, R.id.bottom_setting_ll,
            R.id.back_top_iv, R.id.add_bookshelf_iv, R.id.top_btn, R.id.bottom_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv: //全集    跳到详情
                if (!isBookDetailActivityTo) {
                    BookDetailActivity.start(this, mBookId);
                }
                finish();
                break;
            case R.id.barrage_ll://设置弹幕
                mBarrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE);
                if (mBarrageFlag) {
                    mSharePreferenceUtil.setData(Constant.IS_BARRAGE, false);
                    mBarrageIv.setImageResource(R.mipmap.barrage_close);
                } else {
                    mSharePreferenceUtil.setData(Constant.IS_BARRAGE, true);
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
                onRequestSupport();
                break;
            case R.id.add_bookshelf_tv://加入书架
            case R.id.add_bookshelf_iv:
                if (mLoginModel != 2) {
                    toLogin();
                } else {
                    if (mReadingInfoResponse.getCatalogue().getState() == 1) {
                        showToast("telah masuk rak buku");//已加入书架
                    } else {
                        onAddBookShelfRequest();
                    }
                }
                break;
            case R.id.share_tv://分享
            case R.id.bottom_share_ll:
                new SharePopupWindow(this, this).initPopWindow(mReadLayout);
                break;
            case R.id.last_chapter_ll://上一篇
            case R.id.last_chapter_iv: //上一话
                if (mReadingInfoResponse != null) {
                    mCatalogueId = mReadingInfoResponse.getCatalogue().getPre_catalogue_id();
                    onRequestReadingInfo();
                    onRequestBarrageList();
                }
                break;
            case R.id.next_chapter_ll://下一篇
            case R.id.next_chapter_iv: //下一话
                if (mReadingInfoResponse != null) {
                    mCatalogueId = mReadingInfoResponse.getCatalogue().getNext_catalogue_id();
                    onRequestReadingInfo();
                    onRequestBarrageList();
                }
                break;
            case R.id.bottom_directory_ll: //目录
                onRequestSelectionInfo();
                break;
            case R.id.bottom_comment_ll://评论
                MoreCommentActivity.start(this, mBookId);
                break;
            case R.id.bottom_setting_ll://设置
                mReadSettingPopupWindow = new ReadSettingPopupWindow(this, this, mSharePreferenceUtil);
                mReadSettingPopupWindow.initPopWindow(mReadLayout);
                break;
            case R.id.back_top_iv:// 让页面返回顶部
                mNestedScrollView.post(() -> mNestedScrollView.post(() -> {
                    // 滚动至顶部
                    mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                    // 滚动到底部
                    //sc.fullScroll(ScrollView.FOCUS_DOWN);
                }));
                break;
            case R.id.top_btn:
                upPageBtn();
                break;
            case R.id.bottom_btn:
                downPageBtn();
                break;
        }
    }

    /**
     * 请求弹幕列表
     */
    public void onRequestBarrageList() {
        BarrageListRequest barrageListRequest = new BarrageListRequest();
        barrageListRequest.token = mBuProcessor.getToken();
        barrageListRequest.book_id = mBookId;
        barrageListRequest.catalogue_id = String.valueOf(mCatalogueId);
        mPresenter.getBarrageListRequest(barrageListRequest);
    }


    /**
     * 点赞
     */
    private void onRequestSupport() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = String.valueOf(mCatalogueId);
        commentSuggestRequest.type = "2";
        mPresenter.onSupportRequest(commentSuggestRequest);
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


    /**
     * 去充值弹框  去充值
     */
    @Override
    public void readUseCoinDialogBtnOkListener() {
        //这里 只会跳到购买
        //去购买漫豆
        startActivitys(BuyActivity.class);
    }

    /**
     * 漫豆兑换弹幕弹框  去兑换
     * 1 去冲漫豆  2 去兑换
     */
    @Override
    public void readBeansExchangeDialogBtnOkListener(int type) {
        if (type == 1) {
            startActivitys(BuyActivity.class);
        } else {
            //兑换
            exchangeBarrageStyleRequest();
        }
    }

    /**
     * 漫豆兑换弹幕样式请求
     */
    private void exchangeBarrageStyleRequest() {
        ExchangeBarrageStyleRequest request = new ExchangeBarrageStyleRequest();
        request.token = mBuProcessor.getToken();
        request.style_id = String.valueOf(mBarrageStyle);
        mPresenter.exchangeBarrageStyleRequest(request);
    }

    private boolean beanToBarrage = false;//漫豆兑换弹幕

    /**
     * 漫豆兑换弹幕样式成功
     */
    @Override
    public void getExchangeBarrageStyleSuccess() {
//        showBarrageStyle();
        beanToBarrage = true;
        //更新 购买的弹幕样式
        onRequestBuyBarrageStyle();
        mUser.bean = mUser.bean - 100;//100漫豆兑换一次
        mBuProcessor.setLoginUser(mUser);
        mUser = mBuProcessor.getUser();
    }

    /**
     * 请求购买的弹幕样式
     */
    public void onRequestBuyBarrageStyle() {
        BuyBarrageStyleRequest buyBarrageStyleRequest = new BuyBarrageStyleRequest();
        buyBarrageStyleRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBuyBarrageStyle(buyBarrageStyleRequest);
    }

    /**
     * 请求购买的弹幕样式 成功
     */
    BarrageStylePopupWindow mBarrageStylePopupWindow;//弹幕样式 PopupWindow

    @Override
    public void getBuyBarrageStyleSuccess(BuyBarrageStyleResponse buyBarrageStyleResponse) {
        barrageStyleResponseList.clear();
        mBuyBarrageStyleResponse = buyBarrageStyleResponse;
        for (int i = 0; i < barrageStyleIcon.length; i++) {
            BarrageStyleResponse barrageStyleResponse = new BarrageStyleResponse();
            barrageStyleResponse.isCheck = i == 0;//默认选择第一项
            barrageStyleResponse.styleIcon = barrageStyleIcon[i];
            barrageStyleResponse.styleType = barrageStyleType[i];
            for (BuyBarrageStyleResponse.StyleBean styleBean : buyBarrageStyleResponse.getStyle()) {
                if (styleBean.getStyle_id() == i) {
                    barrageStyleResponse.isBuy = true;
                }
            }
            barrageStyleResponseList.add(barrageStyleResponse);
        }
        if (beanToBarrage) {//更新弹幕
            beanToBarrage = false;
            if (mBarrageStylePopupWindow != null) {
                mBarrageStylePopupWindow.updateData(barrageStyleResponseList, mBarrageStyle);
            }
        }
    }

    /**
     * 请求漫画选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        selectionRequest.orderby = "asc";
        selectionRequest.page = String.valueOf(page);
        selectionRequest.pagesize = "500";//
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }

    /**
     * 请求漫画选集信息成功
     */
    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        mSelectionResponse = selectionResponse;
        if (mSelectionResponse != null) {
            new ReadContentsPopupWindow(this, mSelectionResponse, this, mUser.vip, mImageLoaderHelper).initPopWindow(mReadLayout);
        }
    }

    /**
     * 上传阅读记录  和  使用漫豆  购买阅读非免费章节
     */
    public int useBean;//使bean数量

    public void onRequestReadRecording(int beans) {
        useBean = beans;
        ReadRecordingRequest readRecordingRequest = new ReadRecordingRequest();
        readRecordingRequest.token = mBuProcessor.getToken();
        readRecordingRequest.book_id = mBookId;
        readRecordingRequest.catalogue_id = String.valueOf(mCatalogueId);
        readRecordingRequest.type = String.valueOf(mReadingInfoResponse.getCatalogue().getType() + 1);
        readRecordingRequest.bean = String.valueOf(beans);
        mPresenter.onRequestReadRecording(readRecordingRequest);
    }

    /**
     * 上传阅读记录  和  使用漫豆 成功  减去使用的豆子
     */
    @Override
    public void getReadRecordingSuccess() {
        mUser.bean = mUser.bean - useBean;
        mBuProcessor.setLoginUser(mUser);
        mUser = mBuProcessor.getUser();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
    }


    /**
     * 屏幕向上点击
     */
    private void upPageBtn() {
        if (mCurrentHeight > 0) {
            mCurrentHeight = mCurrentHeight - SystemUtils.getScreenHeight(this) * 3 / 4;
            LogUtils.e("mCurrentHeight:" + mCurrentHeight + " picRvHeight:" + picRvHeight);
        }
        if (mCurrentHeight <= 0) {
            // 滚动至顶部
            mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
        } else if (mCurrentHeight < picRvHeight) {
            mNestedScrollView.smoothScrollTo(0, mCurrentHeight);
        }
    }

    /**
     * 屏幕向下点击
     */
    private void downPageBtn() {
        mCurrentHeight = mCurrentHeight + SystemUtils.getScreenHeight(this) * 3 / 4;
//        LogUtils.e("mCurrentHeight:" + mCurrentHeight + " picRvHeight:" + picRvHeight);
        if (mCurrentHeight < picRvHeight) {
            mNestedScrollView.smoothScrollTo(0, mCurrentHeight);
        }
    }


    /**
     * 点击翻页开关
     */
    @Override
    public void pageTurningBtnListener(boolean pageTurning) {
        sheClickHiddenLayout(pageTurning);//可以点击上下翻页
    }

    /**
     * 夜间模式开关
     */
    @Override
    public void nightModelBtnListener(boolean nightModel) {
        if (nightModel) {
            //设置白天模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 110);//屏幕亮度值范围必须位于：0～255
        } else {
            //设置夜间模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
        }
    }

    /**
     * 关闭弹幕开关
     */
    @Override
    public void barrageSwitchBtnListener(boolean barrageSwitch) {
        mBarrageFlag = barrageSwitch;
        if (barrageSwitch) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
    }

    /**
     * 更多设置
     */
    @Override
    public void clickMoreBtnListener() {
        startActivitys(BarrageSettingActivity.class);
    }


    /**
     * 弹幕模式：显示自定义软键盘弹幕布局
     * 切换弹幕或者评论
     */
    @Override
    public void switchStyleLayoutBtnListenerByBarrageSoftKey() {
        isBarrageState = false;
        switchFunction();
        //显示评论功能
        showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
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
        if (mLoginModel != 2) {
            toLogin();
        } else {
            showAddBarrageDialog(message);
        }
    }

    /**
     * 游客提示登录
     */
    private void toLogin() {
        showToast(getString(R.string.please_login_hint));
        startActivitys(LoginActivity.class);
    }

    /**
     * 弹幕模式：popupWindow窗口关闭
     */
    @Override
    public void dismissBtnListenerByBarrageSoftKey() {
        mBarrageSoftKeyPopupWindow = null;
    }

    /**
     * 评论模式：popupWindow窗口关闭
     */
    @Override
    public void dismissBtnListenerByCommentSoftKey() {
        mCommentSoftKeyPopupWindow = null;
    }

    /**
     * 显示发送弹幕弹框
     * 记录当前X Y轴坐标
     */
    private void showAddBarrageDialog(String message) {
        mCommonToolbarLayout.setVisibility(View.GONE);
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
    public void addBarrageBtnOkListener(String moveTvValue, int width, int height) {
        LogUtils.e("X:" + width + "  Y:" + height);
        sendBarrageRequest(moveTvValue, width, height);
    }

    /**
     * 发送弹幕
     */
    SendBarrageRequest sendBarrageRequest;

    private void sendBarrageRequest(String barrageValue, int width, int height) {
        sendBarrageRequest = new SendBarrageRequest();
        sendBarrageRequest.token = mBuProcessor.getToken();
        sendBarrageRequest.book_id = mBookId;
        sendBarrageRequest.catalogue_id = String.valueOf(mCatalogueId);
        sendBarrageRequest.barrage_content = barrageValue;
        sendBarrageRequest.style_id = String.valueOf(mBarrageStyle);
        sendBarrageRequest.xcoord = String.valueOf(width);
        sendBarrageRequest.ycoord = String.valueOf(mCurrentHeight + height);
        mPresenter.sendBarrageRequest(sendBarrageRequest);
    }

    /**
     * 发送弹幕成功
     */
    @Override
    public void getSendBarrageSuccess() {
        addTvView();
    }

    /**
     * 增加弹幕view
     */
    public void addTvView() {
        Toast toast2 = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.text_view, null);
        TextView textTv = view.findViewById(R.id.text_tv);
        switch (mBarrageStyle) {
            case 0:
                textTv.setBackgroundResource(R.mipmap.black_bg);
                break;
            case 1:
                textTv.setBackgroundResource(R.mipmap.barrage1_bg);
                break;
            case 2:
                textTv.setBackgroundResource(R.mipmap.barrage2_bg);
                break;
            case 3:
                textTv.setBackgroundResource(R.mipmap.barrage3_bg);
                break;
            case 4:
                textTv.setBackgroundResource(R.mipmap.barrage4_bg);
                break;
            case 5:
                textTv.setBackgroundResource(R.mipmap.barrage5_bg);
                break;
        }
        if (sendBarrageRequest != null) {
            textTv.setText(sendBarrageRequest.barrage_content);
            toast2.setView(view);
//            LogUtils.e("textTv.getWidth():" + (int) Double.parseDouble(sendBarrageRequest.xcoord) + " textTv.getHeight():" + (int) Double.parseDouble(sendBarrageRequest.ycoord));
            toast2.setGravity(Gravity.TOP | Gravity.LEFT, (int) Double.parseDouble(sendBarrageRequest.xcoord), (int) Double.parseDouble(sendBarrageRequest.ycoord) - mCurrentHeight);
            toast2.show();
        }
    }

    /**
     * 切换功能 成弹幕模式
     */
    @Override
    public void switchFunctionByCommentSoftKeyBtnListener() {
        isBarrageState = true;
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
    String mContent;

    @Override
    public void CommentSendMessageBtnListener(List<TImage> tImageList, String content) {
//        LogUtils.e("content:" + content);
        if (mLoginModel != 2) {
            toLogin();
        } else {
            mContent = content;
            if (tImageList.size() > 0) {
                for (TImage tImage : tImageList) {
                    Bitmap bitmap = BitmapFactory.decodeFile(tImage.getCompressPath());
                    String path = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
                    uploadImage(path);
                }
            } else {
                publishComment();
            }
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.pic = filename;
        mPresenter.uploadImageRequest(uploadImage);
    }

    /**
     * 上传图片成功
     */
    @Override
    public void getUploadPicSuccess(String picPath) {
        mPicList.add(picPath);
        if (mPicList != null && mPicList.size() == photoList.size()) {
            //上传完最后一张图片 发布评论
            publishComment();
        }
    }

    /**
     * 发布评论
     */
    private void publishComment() {
        PublishCommentRequest publishCommentRequest = new PublishCommentRequest();
        publishCommentRequest.token = mBuProcessor.getToken();
        publishCommentRequest.book_id = mBookId;
        publishCommentRequest.catalogue_id = String.valueOf(mCatalogueId);
        publishCommentRequest.comment = mContent;
        publishCommentRequest.pics = new Gson().toJson(mPicList);
        mPresenter.onRequestPublishComment(publishCommentRequest);
    }

    /**
     * 发布评论成功
     */
    @Override
    public void getPublishCommentSuccess() {
        showToast("publish success");
        SoftKeyboardUtil.hideSoftKeyboard(this);
        if (mCommentSoftKeyPopupWindow != null) {
            mCommentSoftKeyPopupWindow.dismissPopupWindow();
        }
//        onRequestReadingInfo();// 更新章节详情  数据太多
    }

    /**
     * 回复评论
     */
    @Override
    public void ReplyCommentBtnListener(String content) {
        if (mLoginModel != 2) {
            toLogin();
        } else {
            mContent = content;
            publishComment();
        }
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
    public void showBarrageStyle() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
        showFunction();
        if (mBarrageStylePopupWindow != null) {
            mBarrageStylePopupWindow.setDismiss();
            mBarrageStylePopupWindow = null;
        }
        mBarrageStylePopupWindow = new BarrageStylePopupWindow(this, barrageStyleResponseList, mBuProcessor, this);
        mBarrageStylePopupWindow.initPopWindow(mReadLayout);
    }

    @Override
    public void hintOpenVipBtnListener(int barrageStyle) {
        showOpenVipDialog();
    }

//    @Override
//    public void hintBeansExchangeBtnListener() {
//        showBeansExchangeDialog();
//    }

    /**
     * from:弹幕样式PopupWindow
     * 显示漫豆兑换弹幕弹框
     */
    @Override
    public void showBeansExchangeBtnListener(int barrageStyle) {
        mBarrageStyle = barrageStyle;
        showBeansExchangeDialog();
    }

    /**
     * from:弹幕样式PopupWindow
     * 显示弹幕样式:确定样式按钮
     */
    @Override
    public void selectBarrageStyleBtnListener(int style) {
        mBarrageStyle = style;
        SoftKeyboardUtil.hideSoftKeyboard(this);
        //显示
    }

    @Override
    public void showPublishBarrageBtnListener() {
        showBarragePopupWindow();
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    /**
     * 点击图片自动显示或隐藏功能按钮
     */
    private void showFunction() {
        if (mReadBottomLl.getVisibility() == View.VISIBLE) {
            mCommonToolbarLayout.setVisibility(View.GONE);
            mSendMessageLl.setVisibility(View.INVISIBLE);
            mReadBottomLl.setVisibility(View.INVISIBLE);
            mBarrageLl.setVisibility(View.INVISIBLE);
            mAddBookshelfIv.setVisibility(View.INVISIBLE);
        } else {
            mCommonToolbarLayout.setVisibility(View.VISIBLE);
            mSendMessageLl.setVisibility(View.VISIBLE);
            mReadBottomLl.setVisibility(View.VISIBLE);
            mBarrageLl.setVisibility(View.VISIBLE);
            mAddBookshelfIv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 滑动时隐藏功能键、点击图片隐藏功能按钮
     */
    private void hideFunction() {
        mCommonToolbarLayout.setVisibility(View.GONE);
        mSendMessageLl.setVisibility(View.INVISIBLE);
        mReadBottomLl.setVisibility(View.INVISIBLE);
        mBarrageLl.setVisibility(View.INVISIBLE);
        mAddBookshelfIv.setVisibility(View.INVISIBLE);
        mBackTopIv.setVisibility(View.INVISIBLE);
    }


    public void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        mBanner.setPages(bannerList, (MZHolderCreator<BannerReadingViewHolder>) () -> new BannerReadingViewHolder(mImageLoaderHelper));
    }


    /**
     * 设置未点赞状态
     */
    public void setNoSupportState() {
        mSupportTv.setTextColor(getResources().getColor(R.color.color_30));
        Drawable drawable = getResources().getDrawable(R.mipmap.cartoon_chapter_praise_black);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mSupportTv.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 设置点赞状态
     */
    public void setSupportState() {
        mSupportTv.setTextColor(getResources().getColor(R.color.buy_check_color));
        Drawable drawable = getResources().getDrawable(R.mipmap.cartoon_chapter_praise);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mSupportTv.setCompoundDrawables(null, drawable, null, null);
    }


    @Override
    public void shareFacebookBtnListener() {
        shareFacebook();
    }

    @Override
    public void shareWhatsAppBtnListener() {
        shareWhatsApp();
    }

    @Override
    public void shareSuccess() {
        onShareTaskRequest();
    }

    /**
     * 上传分享成功
     */
    private void onShareTaskRequest() {
        ShareTaskRequest shareTaskRequest = new ShareTaskRequest();
        shareTaskRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestShareTask(shareTaskRequest);
    }

    private void shareFacebook() {
        //分享到facebook
        SnsPlatform snsPlatform = SHARE_MEDIA.FACEBOOK.toSnsPlatform();
        //分享链接
        UMWeb web = new UMWeb(BuildConfig.MAN_HUA_BASE_URL + "/download.html ");
//        web.setTitle("download");
        web.setThumb(new UMImage(this, R.mipmap.logo));
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(null).share();
        onShareTaskRequest();
    }

    private void shareWhatsApp() {
        SnsPlatform snsPlatform = SHARE_MEDIA.WHATSAPP.toSnsPlatform();
        //分享链接
        UMWeb web = new UMWeb(BuildConfig.MAN_HUA_BASE_URL + "/download.html ");
//        web.setTitle("download");
        web.setThumb(new UMImage(this, R.mipmap.logo));
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(null).share();
        onShareTaskRequest();
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
