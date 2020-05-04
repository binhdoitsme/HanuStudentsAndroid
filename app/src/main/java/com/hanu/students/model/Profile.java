package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Profile implements Parcelable {
    private final Integer id;
    private final String displayName;
    private final String dob;
    private final String gender;
    private final String nationality;
    private final String hometown;
    private final String phone;
    private final String email;
    private final String className;
    private final String faculty;
    private final String academicYear;
    private final Integer passedCreditCount;
    private final Integer requiredCreditCount;
    private final Double overallMark;

    public Profile(@JsonProperty("id") Integer id,
                   @JsonProperty("displayName") String displayName,
                   @JsonProperty("dob") String dob,
                   @JsonProperty("gender") String gender,
                   @JsonProperty("nationality") String nationality,
                   @JsonProperty("hometown") String hometown,
                   @JsonProperty("phone") String phone,
                   @JsonProperty("email") String email,
                   @JsonProperty("className") String className,
                   @JsonProperty("faculty") String faculty,
                   @JsonProperty("academicYear") String academicYear,
                   @JsonProperty("passedCreditCount") Integer passedCreditCount,
                   @JsonProperty("requiredCreditCount") Integer requiredCreditCount,
                   @JsonProperty("overallMark") Double overallMark) {
        this.id = id;
        this.displayName = displayName;
        this.dob = dob;
        this.gender = gender;
        this.nationality = nationality;
        this.hometown = hometown;
        this.phone = phone;
        this.email = email;
        this.className = className;
        this.faculty = faculty;
        this.academicYear = academicYear;
        this.passedCreditCount = passedCreditCount;
        this.requiredCreditCount = requiredCreditCount;
        this.overallMark = overallMark;
    }

    protected Profile(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        displayName = in.readString();
        dob = in.readString();
        gender = in.readString();
        nationality = in.readString();
        hometown = in.readString();
        phone = in.readString();
        email = in.readString();
        className = in.readString();
        faculty = in.readString();
        academicYear = in.readString();
        if (in.readByte() == 0) {
            passedCreditCount = null;
        } else {
            passedCreditCount = in.readInt();
        }
        if (in.readByte() == 0) {
            requiredCreditCount = null;
        } else {
            requiredCreditCount = in.readInt();
        }
        if (in.readByte() == 0) {
            overallMark = null;
        } else {
            overallMark = in.readDouble();
        }
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(displayName);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(nationality);
        dest.writeString(hometown);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(className);
        dest.writeString(faculty);
        dest.writeString(academicYear);
        if (passedCreditCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(passedCreditCount);
        }
        if (requiredCreditCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(requiredCreditCount);
        }
        if (overallMark == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(overallMark);
        }
    }
}
