package com.hanu.students.model;

import java.util.Objects;

public class DatePeriod implements Comparable {
    private final int day;
    private final int timeStart;
    private final int timeEnd;

    public DatePeriod(int day, int timeStart, int timeEnd) {
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public DatePeriod(int day, String timeStartString, String timeEndString) {
        int timeStart = Integer.parseInt(timeStartString.replace(":", ""));
        int timeEnd = Integer.parseInt(timeEndString.replace(":", ""));
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getDay() {
        return day;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public boolean overlapWith(DatePeriod anotherDatePeriod) {
        if (day != anotherDatePeriod.day) {
            return false;
        } return anotherDatePeriod.timeStart >= timeStart && anotherDatePeriod.timeStart <= timeEnd;
    }

    @Override
    public String toString() {
        return "DatePeriod{" +
                "day=" + day +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatePeriod that = (DatePeriod) o;
        return day == that.day &&
                timeStart == that.timeStart &&
                timeEnd == that.timeEnd;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != getClass()) {
            throw new ClassCastException();
        }
        DatePeriod  dp = (DatePeriod) o;
        if (dp.day != day) {
            return Integer.compare(day, dp.day);
        }
        if (dp.timeStart != timeStart) {
            return Integer.compare(timeStart, dp.timeStart);
        }
        return Integer.compare(timeEnd, dp.timeEnd);
    }
}
