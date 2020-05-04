package com.hanu.students.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @EqualsAndHashCode @ToString
public class TimetableUnit implements Parcelable {

    private final String classCode, courseName, venue,
            instructor, timeStart, timeEnd, gmeetUrl;
    private final Integer dayOfWeek;


    public TimetableUnit(@JsonProperty("classCode") String classCode,
                         @JsonProperty("courseName") String courseName,
                         @JsonProperty("venue") String venue,
                         @JsonProperty("instructor") String instructor,
                         @JsonProperty("timeStart") String timeStart,
                         @JsonProperty("timeEnd") String timeEnd,
                         @JsonProperty("gmeetUrl") String gmeetUrl,
                         @JsonProperty("dayOfWeek") Integer dayOfWeek) {
        this.classCode = classCode;
        this.courseName = courseName;
        this.venue = venue;
        this.instructor = instructor;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.gmeetUrl = gmeetUrl;
        this.dayOfWeek = dayOfWeek;
    }

    protected TimetableUnit(Parcel in) {
        classCode = in.readString();
        courseName = in.readString();
        venue = in.readString();
        instructor = in.readString();
        timeStart = in.readString();
        timeEnd = in.readString();
        gmeetUrl = in.readString();
        if (in.readByte() == 0) {
            dayOfWeek = null;
        } else {
            dayOfWeek = in.readInt();
        }
    }

    public static final Creator<TimetableUnit> CREATOR = new Creator<TimetableUnit>() {
        @Override
        public TimetableUnit createFromParcel(Parcel in) {
            return new TimetableUnit(in);
        }

        @Override
        public TimetableUnit[] newArray(int size) {
            return new TimetableUnit[size];
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
        dest.writeString(venue);
        dest.writeString(instructor);
        dest.writeString(timeStart);
        dest.writeString(timeEnd);
        dest.writeString(gmeetUrl);
        dest.writeInt(dayOfWeek);
    }
}
