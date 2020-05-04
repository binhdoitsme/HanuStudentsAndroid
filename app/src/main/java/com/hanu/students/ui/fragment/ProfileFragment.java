package com.hanu.students.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hanu.students.R;
import com.hanu.students.databinding.FragmentProfileBinding;
import com.hanu.students.model.Profile;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle activityArgs = getActivity().getIntent().getExtras();
        Profile profile = activityArgs.getParcelable("profile");
        binder.setProfile(profile);
    }
}
