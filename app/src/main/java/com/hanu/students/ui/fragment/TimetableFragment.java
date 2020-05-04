package com.hanu.students.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hanu.students.R;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.ui.component.TimetableGridView;
import com.hanu.students.ui.component.TimetableUnitView;
import com.hanu.students.util.converter.DateConverter;
import com.hanu.students.viewmodel.TimetableViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TimetableFragment extends Fragment {

    private TimetableViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(TimetableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerLiveDataListener();
    }

    private void registerLiveDataListener() {
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        viewModel.getData(authToken).observe(getViewLifecycleOwner(), timetableUnits -> {
            LinearLayout layout = getView().findViewById(R.id.timetable_container);
            if (timetableUnits == null) return;
            if (timetableUnits.isEmpty()) {
                TextView textView = new TextView(getContext());
                textView.setText("Không có lớp để hiện");
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                layout.removeAllViews();
                layout.addView(textView);
                return;
            }

            Context context = layout.getContext();
            List<View> addedGrids = createGridsFromTimetable(context, timetableUnits);
            layout.removeAllViews();
            for (View view : addedGrids) {
                layout.addView(view);
            }
        });
    }

    private List<View> createGridsFromTimetable(Context context,
                                                List<TimetableUnit> timetableUnits) {
        final NavController navController = Navigation.findNavController(getActivity(),
                                                                        R.id.nav_host_fragment);

        Map<Integer, List<TimetableUnit>> timetableByDays = new HashMap<>();

        for (TimetableUnit ttu : timetableUnits) {
            int key = ttu.getDayOfWeek();
            if (!timetableByDays.containsKey(key)) {
                timetableByDays.put(key, new LinkedList<>());
            }
            timetableByDays.get(key).add(ttu);
        }

        // the date today
        int dateToday = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2)% 7;
        Log.i("TAG", "createGridsFromTimetable: " + dateToday);

        List<View> addedGrids = new LinkedList<>();
        for (int key : timetableByDays.keySet()) {
            boolean isToday = key == dateToday;
            String dayOfToday = isToday ? "Hôm nay" : DateConverter.from(key);
            TimetableGridView grid = new TimetableGridView(context);
            ViewGroup gridContainer = grid.findViewById(R.id.ttd_grid);
            final List<TimetableUnit> ttus = timetableByDays.get(key);
            for (TimetableUnit timetableUnit : ttus) {
                gridContainer.addView(createTimetableGrid(context, navController, timetableUnit));
            }
            grid.setDateToday(dayOfToday);
            if (isToday) {
                addedGrids.add(0, grid);
            } else {
                addedGrids.add(grid);
            }
        }
        return addedGrids;
    }

    private View createTimetableGrid(Context context, NavController navController,
                                    TimetableUnit timetableUnit) {
        TimetableUnitView view = new TimetableUnitView(context)
                .setTimetable(timetableUnit);
        view.findViewById(R.id.timetable_container).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("label", timetableUnit.getCourseName());
            b.putString("classCode", timetableUnit.getClassCode());
            navController.navigate(R.id.nav_course_details, b);
        });
        return view;
    }
}
