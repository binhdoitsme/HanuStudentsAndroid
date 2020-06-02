package com.hanu.students.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hanu.students.R;
import com.hanu.students.ui.adapter.TuitionFragmentAdapter;
import com.hanu.students.viewmodel.TuitionViewModel;

import java.util.ArrayList;

public class TuitionFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.tuition_tab_1, R.string.tuition_tab_2 };

    private TuitionViewModel viewModel;
    private TuitionFragmentAdapter adapter;
    private ViewPager2 viewPager;

    public TuitionFragment() {
        viewModel = new TuitionViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registerLiveDataListener();
    }

    private void registerLiveDataListener() {
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        viewModel.getData(authToken).observe(getViewLifecycleOwner(), tuitionList -> {
            if (tuitionList == null) return;

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tuitionList", new ArrayList<>(tuitionList));
            setArguments(bundle);

            ViewGroup view = (ViewGroup) getView();
            view.removeAllViews();

            adapter = new TuitionFragmentAdapter(this);

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

    @Override
    public void onClick(View v) {
        Dialog loading = showLoading(getContext());
        loading.show();
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        viewModel.addData(getArguments().getParcelableArrayList("tuitionList"), authToken, 20192)
                .observe(getViewLifecycleOwner(), aBoolean -> {
                    if (aBoolean) {
                        showSuccess(getContext(), "Đóng học phí thành công!");
                        loading.dismiss();
                    } else {
                        showSuccess(getContext(), "Đóng học phí thất bại!");
                        loading.dismiss();
                    }
                });

    }

    private Dialog showLoading(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).create();
        dialog.setView(new ProgressBar(context));
        return dialog;
    }

    private void showSuccess(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", this);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        final NavController navController = Navigation.findNavController(getActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.nav_tuition_fee);
    }
}
