package com.hanu.students.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hanu.students.R;
import com.hanu.students.model.Student;
import com.hanu.students.ui.component.StudentRowView;

import java.util.List;

public class ClassStudentListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_details_studentlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getParentFragment().getArguments();
        TableLayout table = (TableLayout) getView();
        List<Student> studentList = bundle.getParcelableArrayList("student");

        Context context = getContext();
        for (Student s : studentList) {
            StudentRowView row = new StudentRowView(context)
                                        .setStudent(s)
                                        .setRowId(1);

            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            table.addView(row);
        }
    }
}
