package com.shushan.manhua.mvp.ui.base;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.HttpException;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.user.BuProcessor;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.activity.main.MainActivity;
import com.shushan.manhua.mvp.utils.SharePreferenceUtil;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.shushan.manhua.mvp.utils.ToastUtils;

import java.net.ConnectException;
import java.util.Objects;

import javax.inject.Inject;
import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * BaseFragment
 * 基类Fragement
 */
public abstract class BaseFragment extends Fragment {
    @Inject
    protected ImageLoaderHelper mImageLoaderHelper;
    @Inject
    protected SharePreferenceUtil mSharePreferenceUtil;
    @Inject
    protected BuProcessor mBuProcessor;

    private CompositeDisposable mDisposable;
    private Dialog mProgressDialog;
    protected final IntentFilter mFilter = new IntentFilter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFilter();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(mReceiver, mFilter);
    }


    public abstract void initView();

    public abstract void initData();

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceivePro(context, intent);
        }
    };

    public void onReceivePro(Context context, Intent intent) {
    }

    public void addFilter() {
    }

    /**
     * app_bg
     * 设置灰底黑字状态栏
     */
    public void setStatusAppBgBar() {
        StatusBarUtil.setColorNoTranslucent(getActivity(), getResources().getColor(R.color.app_bg_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View content = ((ViewGroup) Objects.requireNonNull(getActivity()).findViewById(android.R.id.content)).getChildAt(0);
            if (content != null && !SystemUtils.isUseFullScreenMode(getActivity())) {
                content.setFitsSystemWindows(true);
            }
        }
    }

    public void showErrMessage(Throwable e) {
        dismissLoading();
        String mErrMessage;
        if (e instanceof HttpException || e instanceof ConnectException || e instanceof SSLHandshakeException) {
            mErrMessage = getString(R.string.text_check_internet);
        } else {
            mErrMessage = getString(R.string.text_wait_try);
        }
        showToast(mErrMessage);
    }

    /**
     * 启动activity
     */
    public void startActivitys(Class<?> tClass) {
        Intent intent = new Intent(getActivity(), tClass);
        startActivity(intent);
    }

    public void showToast(String message) {
        ToastUtils.showShort(getActivity(), message);
    }

    public void showLoading(String msg) {
        dismissLoading();
        mProgressDialog = DialogFactory.showLoadingDialog(getActivity(), msg);
        mProgressDialog.show();
    }

    public void dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public Context getContext() {
        return getActivity();
    }

    public void judgeToken(Integer code) {
        if (code == 2) {
            mSharePreferenceUtil.clearData();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//表示 不创建新的实例activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//表示 移除该activity上面的activity
            intent.putExtra("exitLogin", true);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    /**
     * 清除登录数据
     */
    public void clearSwitchToLogin() {
//        mBuProcessor.clearLoginUser();
//        LoginActivity.start(getContext());
    }

    /**
     * @param subscription RxJava取消订阅
     */
    public void addSubscription(Disposable subscription) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(subscription);
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    private final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable observable) {
            return (
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }
}
