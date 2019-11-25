package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.shushan.manhua.mvp.views.MoveTextView;

import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 添加弹幕dialog
 *
 * @author li.liu
 */
public class AddBarrageDialog extends BaseDialogFragment {
    public static final String TAG = AddBarrageDialog.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.move_tv)
    MoveTextView mMoveTv;
    private AddBarrageDialogListener dialogBtnListener;
    private String message;
    private int mBarrageStyle;//弹幕样式

    public static AddBarrageDialog newInstance() {
        return new AddBarrageDialog();
    }

    public void setListener(AddBarrageDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBarrageStyle(int barrageStyle) {
        this.mBarrageStyle = barrageStyle;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_barrage, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        //动态设置textView位置
        int screenWidth = SystemUtils.getScreenWidth(getActivity());
        int screenHeight = SystemUtils.getScreenHeight(getActivity());
        int randomIntLeft = new Random().nextInt(screenWidth);
        int randomIntTop = new Random().nextInt(screenHeight);
        int randomIntRight = new Random().nextInt(screenWidth);
        int randomIntBottom = new Random().nextInt(screenHeight);
        TextView mTextView = new TextView(getActivity());
        mTextView.setPadding(randomIntLeft, randomIntTop, randomIntRight, randomIntBottom);// 通过自定义坐标来放置你的控件 4个参数按顺序分别是左上右下
        RelativeLayout.LayoutParams flp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flp.setMargins(randomIntLeft, randomIntTop, randomIntRight, randomIntBottom);
        mMoveTv.setLayoutParams(flp);
        mMoveTv.setText(message);
        switch (mBarrageStyle) {
            case 0:
                mMoveTv.setBackgroundResource(R.drawable.bg_black);
                break;
            case 1:
                mMoveTv.setBackgroundResource(R.mipmap.barrage1_bg);
                break;
            case 2:
                mMoveTv.setBackgroundResource(R.mipmap.barrage2_bg);
                break;
            case 3:
                mMoveTv.setBackgroundResource(R.mipmap.barrage3_bg);
                break;
            case 4:
                mMoveTv.setBackgroundResource(R.mipmap.barrage4_bg);
                break;
            case 5:
                mMoveTv.setBackgroundResource(R.mipmap.barrage5_bg);
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.sure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                closeEditNameDialog();
                break;
            case R.id.sure_tv:
                String moveTvValue = mMoveTv.getText().toString();
                if (dialogBtnListener != null) {
                    dialogBtnListener.addBarrageBtnOkListener(moveTvValue);
                }
                closeEditNameDialog();
                break;
        }
    }


    public interface AddBarrageDialogListener {
        void addBarrageBtnOkListener(String moveTvValue);
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
