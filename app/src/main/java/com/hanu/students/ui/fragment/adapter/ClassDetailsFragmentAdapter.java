package com.hanu.students.ui.fragment.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hanu.students.ui.fragment.ClassInformationFragment;
import com.hanu.students.ui.fragment.ClassStudentListFragment;

import lombok.SneakyThrows;

public class ClassDetailsFragmentAdapter extends FragmentStateAdapter {

    private Bundle parentBundle;

    public ClassDetailsFragmentAdapter(Fragment fragment) {
        super(fragment);
        parentBundle = fragment.getArguments();
    }

    @SneakyThrows
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = null;

        switch (position) {
            case 0:
                f = new ClassInformationFragment();
                break;
            case 1:
                f = new ClassStudentListFragment();
                break;
        }

        if (f != null) {
            f.setArguments(parentBundle);
        }
        return f;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
