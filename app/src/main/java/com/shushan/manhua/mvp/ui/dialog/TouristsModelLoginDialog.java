package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 游客模式登录提示dialog
 *
 * @author li.liu
 */
public class TouristsModelLoginDialog extends BaseDialogFragment {
    public static final String TAG = TouristsModelLoginDialog.class.getSimpleName();
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.desc_tv)
    TextView mDescTv;
    @BindView(R.id.login_in_tv)
    TextView mLoginInTv;
    @BindView(R.id.purchase_tv)
    TextView mPurchaseTv;
    Unbinder unbinder;
    private TouristsModelLoginListener dialogBtnListener;
    private String mDesc;

    public static TouristsModelLoginDialog newInstance() {
        return new TouristsModelLoginDialog();
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public void setListener(TouristsModelLoginListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tourists_model_login, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mDescTv.setText(mDesc);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.close_iv, R.id.login_in_tv, R.id.purchase_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                closeDialog();
                break;
            case R.id.login_in_tv:
                if (dialogBtnListener != null) {
                    dialogBtnListener.touristsModelLoginInBtnOkListener();
                    closeDialog();
                }
                break;
            case R.id.purchase_tv:
                if (dialogBtnListener != null) {
                    dialogBtnListener.touristsModelPurchaseBtnOkListener();
                    closeDialog();
                }
                break;
        }
    }


    public interface TouristsModelLoginListener {
        void touristsModelLoginInBtnOkListener();

        void touristsModelPurchaseBtnOkListener();
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
