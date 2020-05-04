package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class ClassInformation implements Parcelable {
    private final String classCode;
    private final String courseName;
    private final Integer creditCount;
    private final List<TimetableUnit> times; // TODO: change back to Iterable!
    private final List<Student> students; // TODO: change back to Iterable!

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ClassInformation(@JsonProperty("classCode") String classCode,
                            @JsonProperty("courseName") String courseName,
                            @JsonProperty("creditCount") Integer creditCount,
                            @JsonProperty("times") List<TimetableUnit> times,
                            @JsonProperty("students") List<Student> students) {
        this.classCode = classCode;
        this.courseName = courseName;
        this.creditCount = creditCount;
        this.times = times;
        this.students = students;
    }

    protected ClassInformation(Parcel in) {
        classCode = in.readString();
        courseName = in.readString();
        if (in.readByte() == 0) {
            creditCount = null;
        } else {
            creditCount = in.readInt();
        }
        times = in.createTypedArrayList(TimetableUnit.CREATOR);
        students = in.createTypedArrayList(Student.CREATOR);
    }

    public static final Creator<ClassInformation> CREATOR = new Creator<ClassInformation>() {
        @Override
        public ClassInformation createFromParcel(Parcel in) {
            return new ClassInformation(in);
        }

        @Override
        public ClassInformation[] newArray(int size) {
            return new ClassInformation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(classCode);
        dest.writeString(courseName);
        if (creditCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(creditCount);
        }
        dest.writeTypedList(times);
        dest.writeTypedList(students);
    }


}
