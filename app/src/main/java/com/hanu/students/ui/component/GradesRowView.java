package com.hanu.students.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentGradesRowBinding;
import com.hanu.students.model.Grades;

public class GradesRowView extends TableRow {

    private ComponentGradesRowBinding binder;

    public GradesRowView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_grades_row, this, true);
    }

    public GradesRowView setGradesReport(Grades grades) {
        binder.setGradesReport(grades);
        return this;
    }

    public GradesRowView setRowId(int rowId) {
        binder.setRowId(rowId);
        return this;
    }
}
