package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode @Getter @ToString
public class Registration implements Parcelable {
    private final String classCode;
    private final String courseCode;
    private final int semester;
    private final int studentId;

    public Registration(@JsonProperty("classCode") String classCode,
                        @JsonProperty("courseCode") String courseCode,
                        @JsonProperty("semester") int semester,
                        @JsonProperty("studentId") int studentId) {
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.semester = semester;
        this.studentId = studentId;
    }


    protected Registration(Parcel in) {
        classCode = in.readString();
        courseCode = in.readString();
        semester = in.readInt();
        studentId = in.readInt();
    }

    public static final Creator<Registration> CREATOR = new Creator<Registration>() {
        @Override
        public Registration createFromParcel(Parcel in) {
            return new Registration(in);
        }

        @Override
        public Registration[] newArray(int size) {
            return new Registration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(classCode);
        dest.writeString(courseCode);
        dest.writeInt(semester);
        dest.writeInt(studentId);
    }
}
