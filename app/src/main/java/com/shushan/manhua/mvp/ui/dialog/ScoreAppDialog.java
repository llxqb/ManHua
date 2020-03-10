package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * app好评dialog
 *
 * @author li.liu
 */
public class ScoreAppDialog extends BaseDialogFragment {
    public static final String TAG = ScoreAppDialog.class.getSimpleName();
    Unbinder unbinder;
    private ScoreAppDialogListener dialogBtnListener;

    public static ScoreAppDialog newInstance() {
        return new ScoreAppDialog();
    }

    public void setListener(ScoreAppDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_score_app, container, true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.go_score_tv, R.id.cancel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_score_tv:
                if (dialogBtnListener != null) {
                    dialogBtnListener.goScoreListener();
                    closeDialog();
                }
                break;
            case R.id.cancel_tv:
                closeDialog();
                break;
        }
    }


    public interface ScoreAppDialogListener {
        void goScoreListener();
    }

    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

}
