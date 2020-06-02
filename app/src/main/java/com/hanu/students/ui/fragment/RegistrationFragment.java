package com.hanu.students.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hanu.students.R;
import com.hanu.students.databinding.FragmentRegistrationClassesBinding;
import com.hanu.students.model.DatePeriod;
import com.hanu.students.model.Profile;
import com.hanu.students.model.Registration;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.model.mapper.RegistrationMapper;
import com.hanu.students.ui.adapter.RegistrationClassExpandableAdapter;
import com.hanu.students.ui.adapter.RegistrationCourseListFragmentAdapter;
import com.hanu.students.ui.wrapper.RegistrationClassWrapper;
import com.hanu.students.viewmodel.RegistrationClassViewModel;
import com.hanu.students.viewmodel.RegistrationViewModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class RegistrationFragment extends Fragment {
    private FragmentRegistrationClassesBinding binder;
    private ExpandableListView listView;
    private RegistrationClassExpandableAdapter adapter;

    private RegistrationClassViewModel registrationClassViewModel;
    private RegistrationViewModel registrationViewModel;
    private List<Registration> preChosenRegistrations;
    private List<RegistrationClass> registrationClasses;
    private SortedMap<String, List<RegistrationClassWrapper>> registrationClassesByCourse;
    private SortedMap<String, List<RegistrationClassWrapper>> registrationClassesByCourseInitialState;
    private SortedMap<DatePeriod, List<RegistrationClassWrapper>> registrationClassesByDatePeriod;
    private MutableLiveData<Map<RegistrationClass, Boolean>> registrationStatus;
    private MutableLiveData<Integer> jobsCompleted = new MutableLiveData<>(0);

    public RegistrationFragment() {
        registrationClassViewModel = new RegistrationClassViewModel();
        registrationViewModel = new RegistrationViewModel();
        preChosenRegistrations = new LinkedList<>();
        registrationStatus = new MutableLiveData<>(new HashMap<>());
        registrationClasses = new LinkedList<>();
        registrationClassesByCourse = new TreeMap<>();
        registrationClassesByCourseInitialState = new TreeMap<>();
        registrationClassesByDatePeriod = new TreeMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater,
                R.layout.fragment_registration_classes, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        LiveData<List<Registration>> registrations = registrationViewModel.getData(authToken, 20192);
        LiveData<List<RegistrationClass>> registrationClasses = registrationClassViewModel.getData(authToken, 20192);

        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        registrations.observe(lifecycleOwner, this::updatePreChosenRegistrations);
        registrationClasses.observe(lifecycleOwner, this::updateRegistrationClassCollection);
        jobsCompleted.observe(lifecycleOwner, this::updateViewOnJobsCompleted);
        binder.setSaveButtonHandler(v -> saveRegistrations());
    }

    private void setupExpandableListView() {
        listView = getView().findViewById(R.id.registration_classes_container);
        adapter = new RegistrationClassExpandableAdapter(getContext(), registrationClassesByCourse);
        adapter.setOnRegistrationClassSelected(this::disableRegistrationIfTimeOverlaps);
        listView.setAdapter(adapter);
    }

    private void incrementJobsCompleted() {
        this.jobsCompleted.setValue(this.jobsCompleted.getValue() + 1);
    }

    private void updatePreChosenRegistrations(List<Registration> registrations) {
        preChosenRegistrations.addAll(registrations);
        incrementJobsCompleted();
    }

    private void updateRegistrationClassCollection(List<RegistrationClass> registrationClassList) {
        this.registrationClasses.clear();
        this.registrationClasses.addAll(registrationClassList);

        incrementJobsCompleted();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupMapping() {
        for (RegistrationClass registrationClass : registrationClasses) {
            String courseCode = registrationClass.getCourseCode();
            registrationClassesByCourse.putIfAbsent(courseCode, new LinkedList<>());
            registrationClassesByCourseInitialState.putIfAbsent(courseCode, new LinkedList<>());
            String classCode = registrationClass.getClassCode();

            boolean isRegistered = preChosenRegistrations.stream().anyMatch(r -> r.getClassCode().equals(classCode));
            RegistrationClassWrapper registrationClassWrapper = new RegistrationClassWrapper(registrationClass, isRegistered);
            RegistrationClassWrapper _registrationClassWrapper = new RegistrationClassWrapper(registrationClass, isRegistered);

            for (TimetableUnit timetableUnit : registrationClass.getTimetables()) {
                DatePeriod datePeriod = new DatePeriod(timetableUnit.getDayOfWeek(),
                        timetableUnit.getTimeStart(), timetableUnit.getTimeEnd());
                registrationClassesByDatePeriod.putIfAbsent(datePeriod, new LinkedList<>());
                registrationClassesByDatePeriod.get(datePeriod).add(registrationClassWrapper);
            }

            registrationClassesByCourse.get(courseCode).add(registrationClassWrapper);
            registrationClassesByCourseInitialState.get(courseCode).add(_registrationClassWrapper);
            if (preChosenRegistrations.stream().anyMatch(r -> r.getClassCode().equals(registrationClass.getClassCode()))) {
                registrationStatus.getValue().put(registrationClass, true);
            } else {
                registrationStatus.getValue().put(registrationClass, false);
            }
        }

        for (Registration rg : preChosenRegistrations) {
            RegistrationClassWrapper registrationClassWrapper = registrationClassesByCourse
                    .get(rg.getCourseCode())
                    .stream()
                    .filter(r -> r.getRegistrationClass().getClassCode().equals(rg.getClassCode()))
                    .findFirst().get();
            disableRegistrationIfTimeOverlaps(registrationClassWrapper);
        }
        System.out.println(registrationClassesByCourse);
    }

    private void showSaveButtonIfStateChanged() {
        if (registrationClassesByCourseInitialState == null || registrationClassesByCourse == null)
            return;
        System.out.println(registrationClassesByCourseInitialState.equals(registrationClassesByCourse));
        binder.setSaveButtonVisibility(!registrationClassesByCourseInitialState.equals(registrationClassesByCourse));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateViewOnJobsCompleted(int jobsCompleted) {
        if (jobsCompleted < 2) {
            return;
        }
        ((ViewGroup) getView()).removeView(getView().findViewById(R.id.progressBar6));
        setupMapping();
        setupExpandableListView();
    }

    private void disableRegistrationIfTimeOverlaps(RegistrationClassWrapper registrationClassWrapper) {
        RegistrationClass registrationClass = registrationClassWrapper.getRegistrationClass();
        boolean shouldDisable = registrationClassWrapper.getCurrentState();
        System.out.println(shouldDisable);
        for (TimetableUnit timetableUnit : registrationClass.getTimetables()) {
            DatePeriod dp = new DatePeriod(timetableUnit.getDayOfWeek(),
                    timetableUnit.getTimeStart(), timetableUnit.getTimeEnd());
            for (RegistrationClassWrapper ignored : registrationClassesByDatePeriod.get(dp)) {
                if (ignored.getRegistrationClass().getCourseCode().equals(registrationClass.getCourseCode())) {

                }
                else
                    ignored.setDisabled(shouldDisable);
            }
        }
        showSaveButtonIfStateChanged();
    }

    private void saveRegistrations() {
        SortedMap<String, List<RegistrationClassWrapper>> backupState
                = new TreeMap<>(registrationClassesByCourseInitialState);
        List<Registration> tobeRemoved = new LinkedList<>();
        List<Registration> tobeAdded = new LinkedList<>();
        Set<String> courseCodes = backupState.keySet();
        Bundle activityArgs = getActivity().getIntent().getExtras();
        Profile profile = activityArgs.getParcelable("profile");
        String authToken = activityArgs.getString("authToken");
        int studentId = profile.getId();
        RegistrationMapper mapper = new RegistrationMapper(studentId);
        for (String courseCode : courseCodes) {
            List<RegistrationClassWrapper> classes = new LinkedList<>(backupState.get(courseCode));
            classes.removeAll(registrationClassesByCourse.get(courseCode));
            for (RegistrationClassWrapper c : classes) {
                System.out.println(c);
                boolean isToBeAdded = !c.getInitialState();
                if (isToBeAdded) tobeAdded.add(mapper.forwardConvert(c));
                else tobeRemoved.add(mapper.forwardConvert(c));
            }
        }
        Log.i("TAG", "saveRegistrations: " + tobeAdded);
        Log.i("TAG", "saveRegistrations: " + tobeRemoved);
        MutableLiveData<Integer> taskCompletions = new MutableLiveData<>(0);

        registrationViewModel.addData(tobeAdded, authToken).observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null) {
                taskCompletions.setValue(taskCompletions.getValue() + 1);
            }
        });
        registrationViewModel.removeData(tobeRemoved, authToken).observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean != null) {
                taskCompletions.setValue(taskCompletions.getValue() + 1);
            }
        });
        taskCompletions.observe(getViewLifecycleOwner(), integer -> {
            if (integer == 2) {
                showSuccessDialog();
            }
        });
    }

    void reload() {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_course_reg);
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(getContext()).setMessage("Đăng kí thành công!")
                .setPositiveButton("OK", (dialog, id) -> reload()).create().show();
    }
}
