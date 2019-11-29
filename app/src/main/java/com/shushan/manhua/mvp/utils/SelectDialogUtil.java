package com.shushan.manhua.mvp.utils;

import android.content.Context;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.shushan.manhua.R;

import java.util.Date;
import java.util.List;

/**
 * 选择框util
 */
public class SelectDialogUtil {

    private Context mContext;
    private SelectPickerListener mSelectPickerListener;

    public SelectDialogUtil(Context context, SelectPickerListener selectPickerListener) {
        mContext = context;
        mSelectPickerListener = selectPickerListener;
    }

    /**
     * 条件选择器,选择文字
     * 选择科目
     */
    public void selectText(String title, List<String> stringList) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = stringList.get(options1);
            if (mSelectPickerListener != null) {
                mSelectPickerListener.getSelectText(tx);
            }
        })
                .setTitleText(title)//标题文字
                .setTitleColor(mContext.getResources().getColor(R.color.color999))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.color_blue_btn))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.first_text_color))//取消按钮文字颜色
                .setCancelText(mContext.getString(R.string.SelectDialogUtil_cancel))
                .setSubmitText(mContext.getString(R.string.SelectDialogUtil_sure))
                .build();
        pvOptions.setPicker(stringList);
        pvOptions.show();
    }


    /**
     * 日期选择器
     * 选择生日弹框
     */
    public void showBirthdayDialog() {
        TimePickerView pvTime = new TimePickerBuilder(mContext, (date, v) -> {//选中事件回调
            if (mSelectPickerListener != null) {
                mSelectPickerListener.getSelectDate(date);
            }
        }).setTitleText(mContext.getResources().getString(R.string.SelectDialogUtil_date_title))//标题文字
                .setTitleColor(mContext.getResources().getColor(R.color.color999))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.color_blue_btn))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.first_text_color))//取消按钮文字颜色
                .setCancelText(mContext.getString(R.string.SelectDialogUtil_cancel))
                .setSubmitText(mContext.getString(R.string.SelectDialogUtil_sure))
                .build();
        pvTime.show();
    }


    public interface SelectPickerListener {
        void getSelectText(String text);

        void getSelectDate(Date date);
    }
}
