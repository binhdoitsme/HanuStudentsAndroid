package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TuitionFee implements Parcelable {
    private final String courseName;
    private final int creditCount;
    private final long value;
    private final boolean isPaid;

    public TuitionFee(@JsonProperty("courseName") String courseName,
                      @JsonProperty("creditCount") int creditCount,
                      @JsonProperty("value") long value,
                      @JsonProperty("isPaid") boolean isPaid) {
        this.courseName = courseName;
        this.creditCount = creditCount;
        this.value = value;
        this.isPaid = isPaid;
    }

    protected TuitionFee(Parcel in) {
        courseName = in.readString();
        creditCount = in.readInt();
        value = in.readLong();
        isPaid = in.readByte() != 0;
    }

    public static final Creator<TuitionFee> CREATOR = new Creator<TuitionFee>() {
        @Override
        public TuitionFee createFromParcel(Parcel in) {
            return new TuitionFee(in);
        }

        @Override
        public TuitionFee[] newArray(int size) {
            return new TuitionFee[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeInt(creditCount);
        dest.writeLong(value);
        dest.writeByte((byte) (isPaid ? 1 : 0));
    }
}
