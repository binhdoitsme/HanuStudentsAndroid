package com.hanu.students.ui.component;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentTimetableGridBinding;

public class TimetableGridView extends LinearLayout {

    private ComponentTimetableGridBinding binder;

    public TimetableGridView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_timetable_grid, this, true);
        setOrientation(VERTICAL);
    }

    public TimetableGridView setDateToday(String dateToday) {
        binder.setDateToday(dateToday);
        return this;
    }
}
