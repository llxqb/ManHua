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
 * 阅读使用金币
 *
 * @author li.liu
 */
public class ReadUseCoinDialog extends BaseDialogFragment {
    public static final String TAG = ReadUseCoinDialog.class.getSimpleName();
    @BindView(R.id.pay_tv)
    TextView mPayTv;
    @BindView(R.id.vip_pay_tv)
    TextView mVipPayTv;
    @BindView(R.id.available_coin_tv)
    TextView mAvailableCoinTv;
    Unbinder unbinder;
    private ReadUseCoinDialogListener dialogBtnListener;
    private int mBeans;//我的漫豆

    public static ReadUseCoinDialog newInstance() {
        return new ReadUseCoinDialog();
    }


    public void setListener(ReadUseCoinDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setData(int beans) {
        mBeans = beans;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_read_use_coin, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
//"Harga"+3+getString(R.string.ReadUseCoinDialog_pay_hint);//会员价
//        getString(R.string.ReadUseCoinDialog_use_coin)+0//可用金币
        mAvailableCoinTv.setText(getString(R.string.ReadUseCoinDialog_use_coin) + " " + mBeans);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.close_iv, R.id.recharge_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                closeDialog();
                break;
            case R.id.recharge_tv://去充值
                if (dialogBtnListener != null) {
                    dialogBtnListener.readUseCoinDialogBtnOkListener();
                }
                closeDialog();
                break;
        }
    }


    public interface ReadUseCoinDialogListener {
        void readUseCoinDialogBtnOkListener();//1 去充值  2 去使用
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
