package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.utils.ValueUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 编辑姓名dialog
 *
 * @author li.liu
 */
public class EditNameDialog extends BaseDialogFragment {
    public static final String TAG = EditNameDialog.class.getSimpleName();
    @BindView(R.id.name_et)
    EditText mNameEt;
    Unbinder unbinder;
    private EditNameDialogListener dialogBtnListener;
    private String name;

    public static EditNameDialog newInstance() {
        return new EditNameDialog();
    }

    public void setListener(EditNameDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_name, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if (!TextUtils.isEmpty(name)) {
            mNameEt.setText(name);
        }
    }


    @OnClick({R.id.cancel, R.id.sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                closeEditNameDialog();
                break;
            case R.id.sure:
                String nameValue = mNameEt.getText().toString();
                if (!TextUtils.isEmpty(nameValue)) {
                    if (ValueUtil.containsEmoji(nameValue)) {
                        showToast("不支持输入Emoji表情符号");
                        return;
                    }
                    if (dialogBtnListener != null) {
                        dialogBtnListener.editNameBtnOkListener(nameValue);
                    }
                    closeEditNameDialog();
                }
                break;
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
