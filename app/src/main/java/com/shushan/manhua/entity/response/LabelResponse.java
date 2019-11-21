package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

public class LabelResponse implements Parcelable {
    public String text;

    public LabelResponse() {
    }

    protected LabelResponse(Parcel in) {
        text = in.readString();
    }

    public static final Creator<LabelResponse> CREATOR = new Creator<LabelResponse>() {
        @Override
        public LabelResponse createFromParcel(Parcel in) {
            return new LabelResponse(in);
        }

        @Override
        public LabelResponse[] newArray(int size) {
            return new LabelResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }
}
