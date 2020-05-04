package com.hanu.students.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentClassStudentRowBinding;
import com.hanu.students.model.Student;

public class StudentRowView extends TableRow {

    private ComponentClassStudentRowBinding binder;

    public StudentRowView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.component_class_student_row, this, true);

    }

    public StudentRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_class_student_row, this, true);
    }

    public StudentRowView setRowId(int id) {
        binder.setRowId(id);
        return this;
    }

    public StudentRowView setStudent(Student student) {
        binder.setStudent(student);
        return this;
    }
}
