package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 去登陆dialog
 * TODO
 */
public class ToLoginDialog extends BaseDialogFragment {
    public static final String TAG = ToLoginDialog.class.getSimpleName();
    Unbinder unbinder;
    private ToLoginDialogListener dialogBtnListener;

    public static ToLoginDialog newInstance() {
        return new ToLoginDialog();
    }

    public void setListener(ToLoginDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_login_app, container, true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.close_iv, R.id.go_login_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                closeDialog();
                break;
            case R.id.go_login_tv:
                if (dialogBtnListener != null) {
                    dialogBtnListener.goLoginListener();
                    closeDialog();
                }
                break;
        }
    }


    public interface ToLoginDialogListener {
        void goLoginListener();
    }

    public void closeDialog() {
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
