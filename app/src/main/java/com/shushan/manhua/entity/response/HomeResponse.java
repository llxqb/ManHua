package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeResponse {
    public String pic;
    public String bookName;
    public List<LabelBean> labelBeanList;

    public List<LabelBean> getLabelBeanList() {
        return labelBeanList;
    }

    public void setLabelBeanList(List<LabelBean> labelBeanList) {
        this.labelBeanList = labelBeanList;
    }

    public static class  LabelBean implements Parcelable {
        public LabelBean() {
        }

        public String name;

        protected LabelBean(Parcel in) {
            name = in.readString();
        }

        public final Creator<LabelBean> CREATOR = new Creator<LabelBean>() {
            @Override
            public LabelBean createFromParcel(Parcel in) {
                return new LabelBean(in);
            }

            @Override
            public LabelBean[] newArray(int size) {
                return new LabelBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }
    }

}
