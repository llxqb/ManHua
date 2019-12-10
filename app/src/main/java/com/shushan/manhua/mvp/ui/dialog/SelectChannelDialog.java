package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 选择频道（男频、女频）
 *
 * @author li.liu
 */
public class SelectChannelDialog extends BaseDialogFragment {
    public static final String TAG = SelectChannelDialog.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.choose_write_male_iv)
    ImageView mChooseWriteMaleIv;
    @BindView(R.id.choose_write_female_iv)
    ImageView mChooseWriteFemaleIv;
    private SelectChannelDialogListener mSelectChannelDialogListener;
    /**
     * 1:男
     * 2：女
     */
    private int chooseSex = 0;

    public void setListener(SelectChannelDialogListener selectChannelDialogListener) {
        this.mSelectChannelDialogListener = selectChannelDialogListener;
    }


    public static SelectChannelDialog newInstance() {
        return new SelectChannelDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_channel, container, true);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //设置点击返回键不消失
        getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
    }


    @OnClick({R.id.channel_male_tv, R.id.channel_female_tv, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.channel_male_tv:
                initChooseState();
                chooseSex = 1;
                mChooseWriteMaleIv.setVisibility(View.VISIBLE);
                if (mSelectChannelDialogListener != null) {
                    mSelectChannelDialogListener.selectChannelBtnOkListener(chooseSex);
                }
                closeDialog();
                break;
            case R.id.channel_female_tv:
                initChooseState();
                chooseSex = 2;
                mChooseWriteFemaleIv.setVisibility(View.VISIBLE);
                if (mSelectChannelDialogListener != null) {
                    mSelectChannelDialogListener.selectChannelBtnOkListener(chooseSex);
                }
                closeDialog();
                break;
            case R.id.sure_tv:
                if (chooseSex != 0) {
                    if (mSelectChannelDialogListener != null) {
                        mSelectChannelDialogListener.selectChannelBtnOkListener(chooseSex);
                    }
                    closeDialog();
                } else {
                    showToast("Silakan pilih saluran");
                }
                break;
        }
    }

    private void initChooseState() {
        mChooseWriteMaleIv.setVisibility(View.INVISIBLE);
        mChooseWriteFemaleIv.setVisibility(View.INVISIBLE);
    }

    public interface SelectChannelDialogListener {
        void selectChannelBtnOkListener(int sex);
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
