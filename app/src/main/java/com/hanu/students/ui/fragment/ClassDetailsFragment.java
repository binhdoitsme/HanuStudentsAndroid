package com.hanu.students.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hanu.students.R;
import com.hanu.students.model.Student;
import com.hanu.students.ui.fragment.adapter.ClassDetailsFragmentAdapter;
import com.hanu.students.viewmodel.ClassInformationViewModel;

import java.util.ArrayList;

public class ClassDetailsFragment extends Fragment {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.class_details_tab_1, R.string.class_details_tab_2};
    private ClassDetailsFragmentAdapter adapter;
    private ViewPager2 viewPager;
    private ClassInformationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ClassInformationViewModel(getArguments().getString("classCode"));
        return inflater.inflate(R.layout.fragment_class_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registerLiveDataListener();
    }

    private void registerLiveDataListener() {

        ArrayList<Student> students = new ArrayList<>();

        viewModel.getData().observe(getViewLifecycleOwner(), classInformation -> {
            if (classInformation == null) return;
            ViewGroup view = (ViewGroup) getView();
            adapter = new ClassDetailsFragmentAdapter(this);

            // stub data
            students.addAll(classInformation.getStudents());

            // put data
            Bundle bundle = getArguments();
            bundle.putParcelableArrayList("student", students);
            bundle.putParcelable("classInfo", classInformation);
            setArguments(bundle);

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
                    (tab, position) -> tab.setText(TAB_TITLES[position])
            ).attach();

        });
    }
}
