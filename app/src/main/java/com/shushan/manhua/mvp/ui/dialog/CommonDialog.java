package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 公用提示的dialog
 *
 * @author li.liu
 */
public class CommonDialog extends BaseDialogFragment {
    public static final String TAG = CommonDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.common_dialog_title)
    TextView mCommonDialogTitle;
    @BindView(R.id.common_dialog_subtitle)
    TextView mCommonDialogSubtitle;
    @BindView(R.id.style1_left_btn)
    Button mStyle1LeftBtn;
    @BindView(R.id.style1_right_btn)
    Button mStyle1RightBtn;
    @BindView(R.id.sure_tv)
    TextView mSureTv;
    @BindView(R.id.dialog_style1_ll)
    LinearLayout mDialogStyle1Ll;
    Unbinder unbinder;
    private CommonDialogListener dialogBtnListener;
    private int mStyle;
    /**
     * 标题
     */
    private String mTitle;
    /**
     * 副标题
     */
    private String mSubtitle;
    private String mLeftBtnText;
    private String mRightBtnText;

    public static CommonDialog newInstance() {
        return new CommonDialog();
    }

    public void setValue(String title, String subtitle, String leftBtnText, String rightBtnText) {
        mTitle = title;
        mSubtitle = subtitle;
        mLeftBtnText = leftBtnText;
        mRightBtnText = rightBtnText;
    }

    public void setStyle(int style) {
        this.mStyle = style;
    }

    public void setListener(CommonDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_common, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mCommonDialogTitle.setText(mTitle);
        if (!TextUtils.isEmpty(mSubtitle)) {
            mCommonDialogSubtitle.setText(mSubtitle);
        } else {
            mCommonDialogSubtitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mLeftBtnText)) {
            mStyle1LeftBtn.setText(mLeftBtnText);
        }
        if (!TextUtils.isEmpty(mRightBtnText)) {
            mStyle1RightBtn.setText(mRightBtnText);
        }
        if (mStyle == Constant.COMMON_DIALOG_STYLE_1) {
            mDialogStyle1Ll.setVisibility(View.VISIBLE);
            mSureTv.setVisibility(View.GONE);
        } else if (mStyle == Constant.COMMON_DIALOG_STYLE_2) {
            mDialogStyle1Ll.setVisibility(View.GONE);
            mSureTv.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.iv_close, R.id.style1_left_btn, R.id.style1_right_btn, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.style1_left_btn:
                closeCommonDialog();
                break;
            case R.id.style1_right_btn:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.sure_tv:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
        }
    }


    public interface CommonDialogListener {
        void commonDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
