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
 * 选择漫画类型（都市、恋爱、古风）
 *
 * @author li.liu
 */
public class SelectManHuaTypeDialog extends BaseDialogFragment {
    public static final String TAG = SelectManHuaTypeDialog.class.getSimpleName();

    public static SelectManHuaTypeDialog newInstance() {
        return new SelectManHuaTypeDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_type, container, true);
        initView();
        return view;
    }

    private void initView() {

    }


    public interface SelectManHuaTypeDialogListener {
        void selectManHuaTypeBtnOkListener();
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
