package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReadingHistoryResponse {
    public String date;
    public List<ReadingHistoryChildBean> readingHistoryChildBeanList ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ReadingHistoryChildBean> getReadingHistoryChildBeanList() {
        return readingHistoryChildBeanList;
    }

    public void setReadingHistoryChildBeanList(List<ReadingHistoryChildBean> readingHistoryChildBeanList) {
        this.readingHistoryChildBeanList = readingHistoryChildBeanList;
    }

    public static class ReadingHistoryChildBean implements Parcelable {

        public String name;
        public String cover;
        public boolean isEditState;
        public boolean isCheck;


        public ReadingHistoryChildBean() {
        }

        protected ReadingHistoryChildBean(Parcel in) {
            name = in.readString();
            cover = in.readString();
        }

        public final Creator<ReadingHistoryChildBean> CREATOR = new Creator<ReadingHistoryChildBean>() {
            @Override
            public ReadingHistoryChildBean createFromParcel(Parcel in) {
                return new ReadingHistoryChildBean(in);
            }

            @Override
            public ReadingHistoryChildBean[] newArray(int size) {
                return new ReadingHistoryChildBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(cover);
        }
    }
}
