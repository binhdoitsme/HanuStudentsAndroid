package com.hanu.students.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hanu.students.R;
import com.hanu.students.databinding.FragmentClassInfoBinding;
import com.hanu.students.model.ClassInformation;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.ui.component.TimetableRowView;

public class ClassInformationFragment extends Fragment {

    private FragmentClassInfoBinding binder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_class_info, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        ClassInformation classInfo = arguments.getParcelable("classInfo");
        setClassInfo(classInfo);
    }

    private void setClassInfo(ClassInformation classInfo) {
        binder.setClassInfo(classInfo);
        binder.setTimetableLabel(getContext().getString(R.string.fragment_timetable));
        ViewGroup root = binder.getRoot().findViewById(R.id.class_inf_container);
        Context context = getContext();
        // for-each here soon
        for (TimetableUnit timetableUnit : classInfo.getTimes())
            root.addView(new TimetableRowView(context).setTimetable(timetableUnit));
    }

}
