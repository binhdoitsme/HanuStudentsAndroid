package com.hanu.students.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentGradesRowBinding;
import com.hanu.students.model.Grades;
import com.hanu.students.ui.component.GradesRowView;

import java.util.List;

public class GradesBySemesterFragment extends Fragment {

    public GradesBySemesterFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grades_by_semester, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TableLayout container = (TableLayout)getView();
        Context context = getContext();

        Bundle bundle = getArguments();
        List<Grades> gradesList = bundle.getParcelableArrayList("gradesList");
        int rowId = 0;
        for (Grades grades : gradesList) {
            TableRow row = new GradesRowView(context).setRowId(++rowId).setGradesReport(grades);
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            container.addView(row);
        }
    }
}
