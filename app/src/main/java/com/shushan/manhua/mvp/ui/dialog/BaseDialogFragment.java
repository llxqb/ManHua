
package com.shushan.manhua.mvp.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.utils.ToastUtils;


/**
 * @author helei
 */
public class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = BaseDialogFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    public void closeDialog(String Tag) {
        try {
            dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), Tag);
        }
    }

    public void showToast(String des) {
        ToastUtils.showShort(getContext(),des);
    }

    @Override
    public void onClick(View v) {

    }


}
