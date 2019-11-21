package com.shushan.manhua.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.BookTypeResponse;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.adapter.SelectBookTypeAdapter;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 选择漫画类型（都市、恋爱、古风）
 *
 * @author li.liu
 */
public class SelectManHuaTypeDialog extends BaseDialogFragment {
    public static final String TAG = SelectManHuaTypeDialog.class.getSimpleName();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.select_num_tv)
    TextView mSelectNumTv;
    Unbinder unbinder;
    private SelectManHuaTypeDialogListener mSelectManHuaTypeDialogListener;
    private SelectBookTypeAdapter mSelectBookTypeAdapter;
    private List<BookTypeResponse> bookTypeResponseList = new ArrayList<>();

    public void setListener(SelectManHuaTypeDialogListener selectManHuaTypeDialogListener) {
        this.mSelectManHuaTypeDialogListener = selectManHuaTypeDialogListener;
    }

    public static SelectManHuaTypeDialog newInstance() {
        return new SelectManHuaTypeDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_type, container, true);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    private void initView() {
        //设置点击返回键不消失
        getDialog().setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        List<Integer> chooseList = new ArrayList<>();//已选择的类型id
        mSelectBookTypeAdapter = new SelectBookTypeAdapter(bookTypeResponseList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(mSelectBookTypeAdapter);
        mSelectBookTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BookTypeResponse bookTypeResponse = (BookTypeResponse) adapter.getItem(position);
            if (bookTypeResponse != null) {
                if (bookTypeResponse.isCheck) {
                    bookTypeResponse.isCheck = false;
                    for (int i = 1; i <= chooseList.size(); i++) {
                        if (i == position) {
                            chooseList.remove(i);
                        }
                    }
                } else {
                    if (chooseList.size() < 3) {
                        bookTypeResponse.isCheck = true;
                        chooseList.add(position);
                    }
                }
            }
            LogUtils.e("" + chooseList.size());
            mSelectNumTv.setText(chooseList.size() + "/3");
            adapter.notifyDataSetChanged();
        });
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            BookTypeResponse bookTypeResponse = new BookTypeResponse();
            bookTypeResponse.isCheck = false;
            bookTypeResponseList.add(bookTypeResponse);
        }
    }

    @OnClick(R.id.sure_tv)
    public void onViewClicked() {
        List<Integer> chooseList = new ArrayList<>();
        for (int i = 0; i < mSelectBookTypeAdapter.getData().size(); i++) {
            BookTypeResponse bookTypeResponse = mSelectBookTypeAdapter.getData().get(i);
            if (bookTypeResponse.isCheck) {
                chooseList.add(i);
            }
        }
        showToast(chooseList.toString());
        closeDialog();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
