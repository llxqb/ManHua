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
 * 漫豆兑换弹幕
 *
 * @author li.liu
 */
public class ReadBeansExchangeDialog extends BaseDialogFragment {
    public static final String TAG = ReadBeansExchangeDialog.class.getSimpleName();
    Unbinder unbinder;
    private ReadBeansExchangeDialogListener dialogBtnListener;

    public static ReadBeansExchangeDialog newInstance() {
        return new ReadBeansExchangeDialog();
    }


    public void setListener(ReadBeansExchangeDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_read_beans_exchange, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.close_iv, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                closeDialog();
                break;
            case R.id.sure_tv://去充漫豆
                if (dialogBtnListener != null) {
                    dialogBtnListener.readBeansExchangeDialogBtnOkListener();
                }
                closeDialog();
                break;
        }
    }


    public interface ReadBeansExchangeDialogListener {
        void readBeansExchangeDialogBtnOkListener();
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
