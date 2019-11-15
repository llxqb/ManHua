package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;


/**
 *选择频道（男频、女频）
 * @author li.liu
 */
public class SelectChannelDialog extends BaseDialogFragment {
    public static final String TAG = SelectChannelDialog.class.getSimpleName();

    public static SelectChannelDialog newInstance() {
        return new SelectChannelDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_channel, container, true);
        initView();
        return view;
    }

    private void initView() {

    }



    public interface SelectChannelDialogListener {
        void selectChannelBtnOkListener();
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
