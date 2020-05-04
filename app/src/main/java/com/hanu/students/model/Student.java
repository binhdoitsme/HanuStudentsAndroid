package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
public class Student implements Parcelable {
    private final Integer id;
    private final String displayName;
    private final String className;
    private final String faculty;

    public Student(@JsonProperty("id") Integer id,
                   @JsonProperty("displayName") String displayName,
                   @JsonProperty("className") String classname,
                   @JsonProperty("faculty") String faculty) {
        this.id = id;
        this.displayName = displayName;
        this.className = classname;
        this.faculty = faculty;
    }

    protected Student(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        displayName = in.readString();
        className = in.readString();
        faculty = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(displayName);
        dest.writeString(className);
        dest.writeString(faculty);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
