package com.shushan.manhua.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.model.ModelTransform;
import com.shushan.manhua.mvp.model.UserModel;
import com.shushan.manhua.mvp.ui.activity.user.MessaagePresenterImpl;
import com.shushan.manhua.mvp.ui.activity.user.MessageControl;
import com.shushan.manhua.mvp.ui.fragment.message.NoticeMessageFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.message.NoticeMessageFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.message.ReceivedMessageFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.message.ReceivedMessageFragmentPresenterImpl;
import com.shushan.manhua.mvp.ui.fragment.message.SentMessageFragmentControl;
import com.shushan.manhua.mvp.ui.fragment.message.SentMessageFragmentPresenterImpl;
import com.shushan.manhua.network.RetrofitUtil;
import com.shushan.manhua.network.networkapi.UserApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class MessageModule {
    private final AppCompatActivity activity;
    private MessageControl.MessageView view;

    public MessageModule(AppCompatActivity activity, MessageControl.MessageView view) {
        this.activity = activity;
        this.view = view;
    }

    public MessageModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MessageControl.MessageView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MessageControl.PresenterMessage providePresenterMessage(MessaagePresenterImpl messaagePresenter) {
        return messaagePresenter;
    }

    @Provides
    @PerActivity
    UserModel provideUserModel(Gson gson, ModelTransform modelTransform) {
        return new UserModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.WORK_STU_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(UserApi.class), gson, modelTransform);
    }


    /**
     * 我发出的评论
     */
    @Provides
    @PerActivity
    SentMessageFragmentControl.SentMessageFragmentPresenter providePresenterSentMessageFragment(SentMessageFragmentPresenterImpl sentMessageFragmentPresenter) {
        return sentMessageFragmentPresenter;
    }

    /**
     * 回复我的评论
     */
    @Provides
    @PerActivity
    ReceivedMessageFragmentControl.ReceivedMessageFragmentPresenter providePresenterReceivedMessageFragment(ReceivedMessageFragmentPresenterImpl receivedMessageFragmentPresenter) {
        return receivedMessageFragmentPresenter;
    }
    /**
     * 通知
     */
    @Provides
    @PerActivity
    NoticeMessageFragmentControl.NoticeMessageFragmentPresenter providePresenterNoticeMessageFragment(NoticeMessageFragmentPresenterImpl noticeMessageFragmentPresenter) {
        return noticeMessageFragmentPresenter;
    }
}
