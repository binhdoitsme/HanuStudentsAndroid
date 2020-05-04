package com.hanu.students.util.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.hanu.students.util.push.schedule.ScheduleService;

import java.util.Calendar;

public class NotificationsUtils {
    private static int INDEX = 4;
    private static final int TIME_VIBRATE = 1000;

    public static void create(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduleService.class);
        intent.putExtra("label", "Show this label");

        for (int i = 0; i < 5; i++) {

            PendingIntent pendingIntent =
                    PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            INDEX++;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, INDEX);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager
                        .setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager
                        .set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}
