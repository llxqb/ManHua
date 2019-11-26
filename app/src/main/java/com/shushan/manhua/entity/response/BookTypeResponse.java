package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BookTypeResponse {
    /**
     * error : 0
     * msg : success
     * data : [{"type_id":1,"type_name":"Perkotaan","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_urban%403x.png"},{"type_id":2,"type_name":"Romantis","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_love%403x.png"},{"type_id":3,"type_name":"Sejarah kuno","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_antiquities%403x.png"},{"type_id":4,"type_name":"Pahlawan","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_ability%403x.png"},{"type_id":5,"type_name":"Fantasi","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_fantasy%403x.png"},{"type_id":6,"type_name":"Sekolah","type_cover":"https://img.pulaukomik.com/booktype/reading_preference_campus%403x.png"}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * type_id : 1
         * type_name : Perkotaan
         * type_cover : https://img.pulaukomik.com/booktype/reading_preference_urban%403x.png
         */

        private int type_id;
        private String type_name;
        private String type_cover;
        public boolean isCheck;

        protected DataBean(Parcel in) {
            type_id = in.readInt();
            type_name = in.readString();
            type_cover = in.readString();
            isCheck = in.readByte() != 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_cover() {
            return type_cover;
        }

        public void setType_cover(String type_cover) {
            this.type_cover = type_cover;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(type_id);
            dest.writeString(type_name);
            dest.writeString(type_cover);
            dest.writeByte((byte) (isCheck ? 1 : 0));
        }
    }
}
