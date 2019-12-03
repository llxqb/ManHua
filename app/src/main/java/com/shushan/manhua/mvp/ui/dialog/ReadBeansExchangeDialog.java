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
 * 漫豆兑换弹幕
 *
 * @author li.liu
 */
public class ReadBeansExchangeDialog extends BaseDialogFragment {
    public static final String TAG = ReadBeansExchangeDialog.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.beans_hint_tv)
    TextView mBeansHintTv;
    @BindView(R.id.sure_tv)
    TextView mSureTv;
    private ReadBeansExchangeDialogListener dialogBtnListener;
    private int mBeans;

    public static ReadBeansExchangeDialog newInstance() {
        return new ReadBeansExchangeDialog();
    }


    public void setListener(ReadBeansExchangeDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setDate(int bean) {
        mBeans = bean;
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
        if (mBeans < 100) {
            mSureTv.setText(getActivity().getString(R.string.ReadBeansExchangeDialog_sure));
            mBeansHintTv.setText(getActivity().getString(R.string.ReadBeansExchangeDialog_sure_hint));
        } else {
            mSureTv.setText(getActivity().getString(R.string.ReadBeansExchangeDialog_sure1));
            mBeansHintTv.setText(mBeans + getActivity().getString(R.string.MineFragment_beans_tv));
        }
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
                    if (mBeans < 100) {
                        dialogBtnListener.readBeansExchangeDialogBtnOkListener(1);
                    }else {
                        dialogBtnListener.readBeansExchangeDialogBtnOkListener(2);
                    }
                }
                closeDialog();
                break;
        }
    }


    public interface ReadBeansExchangeDialogListener {
        void readBeansExchangeDialogBtnOkListener(int type);//1 去冲漫豆  2 去兑换
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
