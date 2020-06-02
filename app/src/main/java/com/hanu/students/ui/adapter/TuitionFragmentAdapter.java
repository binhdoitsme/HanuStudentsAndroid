package com.hanu.students.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hanu.students.ui.fragment.TuitionDetailsFragment;
import com.hanu.students.ui.fragment.TuitionHistoryFragment;

public class TuitionFragmentAdapter extends FragmentStateAdapter {

    public TuitionFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = new TuitionDetailsFragment();
                break;
            case 1:
                f = new TuitionHistoryFragment();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return f;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
