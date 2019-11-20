package com.shushan.manhua.mvp.ui.activity.mine;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerFeedbackComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.FeedbackModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements FeedbackControl.FeedbackView {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.content_et)
    EditText mContentEt;
    @BindView(R.id.limit_num_tv)
    TextView mLimitNumTv;
    @BindView(R.id.contact_way_tv)
    EditText mContactWayTv;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_feedback);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.FeedbackActivity_title));
        mContentEt.addTextChangedListener(search_text_OnChange);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_left_iv, R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.submit_tv:
                if (verification()) {
                    //提交
                }
                break;
        }
    }

    private boolean verification() {
        if (TextUtils.isEmpty(mContentEt.getText())) {
            showToast(getResources().getString(R.string.FeedbackActivity_content_is_empty));
            return false;
        }
        return true;
    }



    public TextWatcher search_text_OnChange = new TextWatcher() {
        private int selectionStart;
        private int selectionEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            selectionStart = mContentEt.getSelectionStart();
            selectionEnd = mContentEt.getSelectionEnd();
            String worldTextNumValue = s.length() + "/100";
            if (s.length() > 80) {
                showToast(getResources().getString(R.string.FeedbackActivity_only_100_word));
                s.delete(selectionStart - 1, selectionEnd);
                int tempSelection = selectionStart;
                mLimitNumTv.setText(worldTextNumValue);
                mContentEt.setSelection(tempSelection);
            } else {
                mLimitNumTv.setText(worldTextNumValue);
            }
        }
    };


    private void initInjectData() {
        DaggerFeedbackComponent.builder().appComponent(getAppComponent())
                .feedbackModule(new FeedbackModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
