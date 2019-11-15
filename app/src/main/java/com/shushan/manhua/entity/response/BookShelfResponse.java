package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

public class BookShelfResponse implements Parcelable {
    public int cover;
    public String name;
    public String desc;
    /**
     *点我显示更多
     */
    public boolean isMore;
    /**
     * 长按删除，是否选择
     */
    public boolean isCheck;

    public BookShelfResponse() {
    }

    protected BookShelfResponse(Parcel in) {
        cover = in.readInt();
        name = in.readString();
        desc = in.readString();
        isMore = in.readByte() != 0;
        isCheck = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cover);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeByte((byte) (isMore ? 1 : 0));
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookShelfResponse> CREATOR = new Creator<BookShelfResponse>() {
        @Override
        public BookShelfResponse createFromParcel(Parcel in) {
            return new BookShelfResponse(in);
        }

        @Override
        public BookShelfResponse[] newArray(int size) {
            return new BookShelfResponse[size];
        }
    };
}
