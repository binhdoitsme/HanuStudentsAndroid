package com.hanu.students.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentTimetableUnitBinding;
import com.hanu.students.model.TimetableUnit;

public class TimetableUnitView extends LinearLayout {
    private ComponentTimetableUnitBinding binder;

    public TimetableUnitView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.component_timetable_unit, this, true);
    }

    public TimetableUnitView setTimetable(TimetableUnit timetable) {
        binder.setTimetable(timetable);
        return this;
    }
}
