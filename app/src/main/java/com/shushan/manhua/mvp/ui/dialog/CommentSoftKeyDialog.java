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
 * 评论自定义软键盘dialog
 *
 * @author li.liu
 */
public class CommentSoftKeyDialog extends BaseDialogFragment {
    public static final String TAG = CommentSoftKeyDialog.class.getSimpleName();
    private CommentSoftKeyDialogListener dialogBtnListener;
    private String name;

    public static CommentSoftKeyDialog newInstance() {
        return new CommentSoftKeyDialog();
    }

    public void setListener(CommentSoftKeyDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comment_soft_key, container, true);
        initView();
        return view;
    }

    private void initView() {
    }




    public interface CommentSoftKeyDialogListener {
//        void editNameBtnOkListener(String nameValue);
    }

    public void closeEditNameDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
