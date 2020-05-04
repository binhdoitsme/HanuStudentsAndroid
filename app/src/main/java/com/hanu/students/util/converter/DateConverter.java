package com.hanu.students.util.converter;

public class DateConverter {
    private static final String[] dayOfWeek = {
            "Thứ hai",
            "Thứ ba",
            "Thứ tư",
            "Thứ năm",
            "Thứ sáu",
            "Thứ bảy",
            "Chủ nhật"
    };

    public static String from(int dayIndex) {
        return dayOfWeek[dayIndex];
    }
}
