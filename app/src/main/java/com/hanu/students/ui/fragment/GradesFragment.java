package com.hanu.students.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hanu.students.R;
import com.hanu.students.model.Grades;
import com.hanu.students.ui.adapter.GradesFragmentAdapter;
import com.hanu.students.viewmodel.GradesViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class GradesFragment extends Fragment {

    private GradesViewModel viewModel;
    private GradesFragmentAdapter adapter;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new GradesViewModel();
        return inflater.inflate(R.layout.fragment_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registerLiveDataListener();
    }

    private void registerLiveDataListener() {
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        viewModel.getData(authToken).observe(getViewLifecycleOwner(), grades -> {
            if (grades == null) return;
            if (grades.isEmpty()) {
                ViewGroup v = (ViewGroup) getView();
                v.removeView(v.findViewById(R.id.progressBar2));
                TextView textView = new TextView(getContext());
                textView.setText("Không có lớp để hiện");
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                v.addView(textView);
                return;
            }

            ViewGroup view = (ViewGroup) getView();
            adapter = new GradesFragmentAdapter(this);

            // group by semester
            Map<Integer, List<Grades>> gradesBySemester = new HashMap<>();
            for (Grades grade : grades) {
                int semesterId = grade.getSemesterId();
                if (!gradesBySemester.containsKey(grade)) {
                    gradesBySemester.put(semesterId, new ArrayList<>());
                }
                gradesBySemester.get(semesterId).add(grade);
            }

            // keySet = tab titles
            adapter = new GradesFragmentAdapter(this);
            List<Integer> semesterIds = new ArrayList<>(new TreeSet<>(gradesBySemester.keySet()));
            for (int semesterId : semesterIds) {
                // semesterId -> title
                Bundle gradesList = new Bundle();
                gradesList.putParcelableArrayList("gradesList",
                        new ArrayList<>(gradesBySemester.get(semesterId)));
                Fragment f = new GradesBySemesterFragment();
                f.setArguments(gradesList);
                adapter.addFragment(f, String.valueOf(semesterId));
            }

            // remove spinner
            view.removeAllViews();

            // create view children
            TabLayout tabLayout = new TabLayout(getContext());
            view.addView(tabLayout);

            viewPager = new ViewPager2(getContext());
            viewPager.setAdapter(adapter);
            viewPager.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.addView(viewPager);

            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(String.valueOf(semesterIds.get(position)))
            ).attach();

        });
    }
}
