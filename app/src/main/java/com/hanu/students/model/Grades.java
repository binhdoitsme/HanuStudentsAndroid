package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Grades implements Parcelable {
    private final String courseCode;
    private final String courseName;
    private final Integer creditCount;
    private final Double attendance;
    private final Double midterm;
    private final Double exam;
    private final Double aggregate;
    private final Integer semesterId;

    public Grades(@JsonProperty("courseCode") String courseCode,
                  @JsonProperty("courseName") String courseName,
                  @JsonProperty("creditCount") Integer creditCount,
                  @JsonProperty("attendance") Double attendance,
                  @JsonProperty("midterm") Double midterm,
                  @JsonProperty("exam") Double exam,
                  @JsonProperty("aggregate") Double aggregate,
                  @JsonProperty("semesterId") Integer semesterId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditCount = creditCount;
        this.attendance = attendance;
        this.midterm = midterm;
        this.exam = exam;
        this.aggregate = aggregate;
        this.semesterId = semesterId;
    }

    protected Grades(Parcel in) {
        courseCode = in.readString();
        courseName = in.readString();
        if (in.readByte() == 0) {
            creditCount = null;
        } else {
            creditCount = in.readInt();
        }
        if (in.readByte() == 0) {
            attendance = null;
        } else {
            attendance = in.readDouble();
        }
        if (in.readByte() == 0) {
            midterm = null;
        } else {
            midterm = in.readDouble();
        }
        if (in.readByte() == 0) {
            exam = null;
        } else {
            exam = in.readDouble();
        }
        if (in.readByte() == 0) {
            aggregate = null;
        } else {
            aggregate = in.readDouble();
        }
        if (in.readByte() == 0) {
            semesterId = null;
        } else {
            semesterId = in.readInt();
        }
    }

    public static final Creator<Grades> CREATOR = new Creator<Grades>() {
        @Override
        public Grades createFromParcel(Parcel in) {
            return new Grades(in);
        }

        @Override
        public Grades[] newArray(int size) {
            return new Grades[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseCode);
        dest.writeString(courseName);
        if (creditCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditCount);
        }
        if (attendance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(attendance);
        }
        if (midterm == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(midterm);
        }
        if (exam == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(exam);
        }
        if (aggregate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(aggregate);
        }
        if (semesterId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(semesterId);
        }
    }
}
