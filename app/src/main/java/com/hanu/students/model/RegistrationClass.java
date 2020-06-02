package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegistrationClass implements Parcelable, Comparable {
    private final String courseCode;
    private final String courseName;
    private final String classCode;
    private final String instructor;
    private final int remainingSlots;
    private final int maxSlots;
    private final int semester;
    private final List<TimetableUnit> timetables;

    public RegistrationClass(@JsonProperty("courseCode") String courseCode,
                             @JsonProperty("courseName") String courseName,
                             @JsonProperty("classCode") String classCode,
                             @JsonProperty("instructor") String instructor,
                             @JsonProperty("remainingSlots") int remainingSlots,
                             @JsonProperty("maxSlots") int maxSlots,
                             @JsonProperty("semester") int semester,
                             @JsonProperty("timetables") List<TimetableUnit> timetables) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classCode = classCode;
        this.instructor = instructor;
        this.remainingSlots = remainingSlots;
        this.maxSlots = maxSlots;
        this.semester = semester;
        this.timetables = timetables;
    }

    protected RegistrationClass(Parcel in) {
        courseCode = in.readString();
        courseName = in.readString();
        classCode = in.readString();
        instructor = in.readString();
        remainingSlots = in.readInt();
        maxSlots = in.readInt();
        semester = in.readInt();
        timetables = in.createTypedArrayList(TimetableUnit.CREATOR);
    }

    public static final Creator<RegistrationClass> CREATOR = new Creator<RegistrationClass>() {
        @Override
        public RegistrationClass createFromParcel(Parcel in) {
            return new RegistrationClass(in);
        }

        @Override
        public RegistrationClass[] newArray(int size) {
            return new RegistrationClass[size];
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
        dest.writeString(classCode);
        dest.writeString(instructor);
        dest.writeInt(remainingSlots);
        dest.writeInt(maxSlots);
        dest.writeInt(semester);
        dest.writeTypedList(timetables);
    }

    @Override
    public int compareTo(Object o) {
        RegistrationClass other = ((RegistrationClass) o);
        if (courseCode.equals(other.courseCode))
            return classCode.compareTo(other.classCode);
        return courseCode.compareTo(other.courseCode);
    }
}
