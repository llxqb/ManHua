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
 * 漫豆快要过期提示
 *
 * @author li.liu
 */
public class BeansExpiredDialog extends BaseDialogFragment {
    public static final String TAG = BeansExpiredDialog.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.expired_date_tv)
    TextView mExpiredDateTv;
    @BindView(R.id.beans_num_tv)
    TextView mBeansNumTv;
    private String mDate;
    private String mExpiredBeansNum;

    public static BeansExpiredDialog newInstance() {
        return new BeansExpiredDialog();
    }

    public void setData(String date, String expiredBeansNum) {
        mDate = date;
        mExpiredBeansNum = expiredBeansNum;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_beans_expired, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //失效  Gagal
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.close_iv)
    public void onViewClicked() {
        closeDialog();
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
