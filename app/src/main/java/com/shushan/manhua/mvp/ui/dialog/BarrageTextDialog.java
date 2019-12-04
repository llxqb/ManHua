package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 编辑姓名dialog
 *
 * @author li.liu
 */
public class BarrageTextDialog extends BaseDialogFragment {
    public static final String TAG = BarrageTextDialog.class.getSimpleName();
    @BindView(R.id.text_tv)
    TextView mTextTv;
    Unbinder unbinder;
    private String name;

    public static BarrageTextDialog newInstance() {
        return new BarrageTextDialog();
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_barrage_text, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(200, 200, 0, 0);
        mTextTv.setLayoutParams(layoutParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface EditNameDialogListener {
        void editNameBtnOkListener(String nameValue);
    }

    public void closeEditNameDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
