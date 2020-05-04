package com.hanu.students.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentTimetableRowBinding;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.util.push.NotificationsUtils;

public class TimetableRowView extends LinearLayout {

    private ComponentTimetableRowBinding binder;

    public TimetableRowView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_timetable_row, this, true);
        addButtonEventListener();
    }

    public TimetableRowView(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        setOrientation(VERTICAL);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_timetable_row, this, true);
        addButtonEventListener();
    }

    private void addButtonEventListener() {
        Button button = findViewById(R.id.class_set_alarm);
        button.setOnClickListener((view) -> {
            NotificationsUtils.create(view.getContext());
        });
    }

    public TimetableRowView setTimetable(TimetableUnit timetableUnit) {
        binder.setTimetable(timetableUnit);
        return this;
    }
}
