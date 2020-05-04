package com.hanu.students.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hanu.students.R;
import com.hanu.students.model.Profile;
import com.hanu.students.model.Registration;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.ui.component.RegistrationClassView;
import com.hanu.students.ui.component.RegistrationCourseView;
import com.hanu.students.viewmodel.RegistrationClassViewModel;
import com.hanu.students.viewmodel.RegistrationViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegistrationFragment extends Fragment {

    //    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
    private ListView listView;
    private ListAdapter adapter;

    private RegistrationClassViewModel registrationClassViewModel;
    private RegistrationViewModel registrationViewModel;
    private List<Registration> preChosenRegistrations;
    private Map<String, List<RegistrationClass>> dataset;

    public RegistrationFragment() {
        registrationClassViewModel = new RegistrationClassViewModel();
        registrationViewModel = new RegistrationViewModel();
        preChosenRegistrations = new LinkedList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_classes, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton button = getView().findViewById(R.id.floatingActionButton);
        button.setOnClickListener(this::onClick);

//        recyclerView = getView().findViewById(R.id.registration_classes_container);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        LinearLayout listView = getView().findViewById(R.id.registration_classes_container);


        MutableLiveData<Integer> finishedJobs = new MutableLiveData<>(0);
        String authToken = getActivity().getIntent().getExtras().getString("authToken");
        registrationViewModel.getData(authToken, 20192).observe(getViewLifecycleOwner(), registrations -> {
            if (registrations.isEmpty()) {
                return;
            }
            preChosenRegistrations.addAll(registrations);
            finishedJobs.setValue(finishedJobs.getValue() + 1);
        });

        registrationClassViewModel.getData(authToken, 20192).observe(getViewLifecycleOwner(), registrationClasses -> {
            if (registrationClasses == null) {
                return;
            }

            if (registrationClasses.isEmpty()) {
                ViewGroup v = (ViewGroup) getView();
                v.removeView(v.findViewById(R.id.progressBar6));
                TextView textView = new TextView(getContext());
                textView.setText("Không có lớp để hiện");
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ((ViewGroup)v.findViewById(R.id.registration_classes_container)).addView(textView);
                return;
            }

            Map<String, List<RegistrationClass>> dataset = new HashMap<>();
            for (RegistrationClass registrationClass : registrationClasses) {
                String courseCode = registrationClass.getCourseCode();
                if (!dataset.containsKey(courseCode)) {
                    dataset.put(courseCode, new LinkedList<>());
                }
                dataset.get(courseCode).add(registrationClass);
            }

            this.dataset = dataset;
            System.out.println(dataset);

            finishedJobs.setValue(finishedJobs.getValue() + 1);

//            mAdapter = new RegistrationClassListFragmentAdapter(dataset).setOnChosenListener(this::onChosenListener);
//            recyclerView.setAdapter(mAdapter);

            List<View> viewsToAdd = new LinkedList<>();
            Context context = getContext();
            List<String> courseCodes = dataset.keySet().stream().sorted().collect(Collectors.toList());
            for (String courseCode : courseCodes) {
                List<RegistrationClass> classes = dataset.get(courseCode);
                String courseName = classes.get(0).getCourseName();
                View viewToAdd = new RegistrationCourseView(context).setCourseCode(courseCode)
                        .setCourseName(courseName)
                        .setOnChosenListener(this::onChosenListener)
                        .setRegistrationClassList(classes);
                viewToAdd.setTag(courseCode);
                viewsToAdd.add(viewToAdd);
            }

            for (View v : viewsToAdd) {
                listView.addView(v);
            }

            ViewGroup v = (ViewGroup) getView();
            v.removeView(v.findViewById(R.id.progressBar6));
        });

        finishedJobs.observe(getViewLifecycleOwner(), finishedJobCnt -> {
            if (finishedJobCnt == 2) { // all done
                final Handler handler = new Handler();

                handler.postDelayed(() -> {
                    // render
                    System.out.println("Finished loading");

                    int registrationCoursesCount = dataset.size();
                    List<String> courseCodes = new LinkedList<>(dataset.keySet());
                    if (preChosenRegistrations.isEmpty()) {
                        return;
                    }

                    for (int i = 0; i < registrationCoursesCount; i++) {
                        RegistrationCourseView v = (RegistrationCourseView) listView.getChildAt(i);
                        String courseCode = courseCodes.get(i);
                        List<RegistrationClass> registrationClasses = dataset.get(courseCode);
                        String courseName = registrationClasses.get(0).getCourseCode();

                        v.findViewById(R.id.registration_course_toggle).setBackgroundColor(Color.parseColor("#FF0000"));

//                        if (preChosenRegistrations.stream().anyMatch(r -> r.getCourseCode().equals(courseCode))) {
//                            ViewGroup classesContainer = v.findViewById(R.id.registration_choice_container);
//                            int classCount = classesContainer.getChildCount();
//                            for (int k = 0; k < classCount; k++) {
//                                TextView textView = ((ViewGroup) classesContainer.getChildAt(k)).getChildAt(0).findViewById(R.id.textView11);
//                                String text = textView.getText().toString();
//                                System.out.println(text);
//                                if (preChosenRegistrations.stream().anyMatch(rg -> rg.getClassCode().equals(text))) {
//                                    ((RegistrationClassView) textView.getParent().getParent()).setInitialState(true);
//                                } else {
//                                    ((RegistrationClassView) textView.getParent().getParent()).setInitialState(false);
//                                }
//                            }
//                            ((RegistrationClassView)v.findViewWithTag(courseCode)).setInitialState(true);
//                        } else {
//                            System.out.println(courseCode + " was not chosen");
//                        }

                    }
                    View v = getView();
                    for (Registration registration : preChosenRegistrations) {
                        System.out.println(v.findViewWithTag(registration.getClassCode()).getClass());
                        ((RegistrationClassView)v.findViewWithTag(registration.getClassCode()))
                                .setInitialState(true);
                    }
                }, 200);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onChosenListener(View v) {
        System.out.println(v);
        RegistrationClassView parent = (RegistrationClassView) v.getParent().getParent();
        String classCode = parent.getRegistrationClass().getClassCode();
        String courseCode = parent.getRegistrationClass().getCourseCode();

        Activity activity = getActivity();
        Bundle activityExtras = activity.getIntent().getExtras();
        Profile profile = activityExtras.getParcelable("profile");
        int studentId = profile.getId();
        int semesterId = 20192;
        Registration registration = new Registration(classCode, courseCode, semesterId, studentId);

        Bundle bundle = getArguments();
        System.out.println(bundle);
        if (bundle == null) bundle = new Bundle();

        ArrayList<Registration> addRegistrations = bundle.getParcelableArrayList("addRegistrations");
        ArrayList<Registration> removeRegistrations = bundle.getParcelableArrayList("removeRegistrations");
        if (addRegistrations == null) {
            addRegistrations = new ArrayList<>();
        }
        if (removeRegistrations == null) {
            removeRegistrations = new ArrayList<>();
        }

        boolean checked = false;

        // remove all checked instances of this course from the backing array list
        addRegistrations.removeIf(r -> r.getCourseCode().equals(courseCode) && !r.equals(registration));
        List<Registration> sameCourseFromPreviousRegistrations =  preChosenRegistrations.stream()
                .filter(r -> r.getCourseCode().equals(courseCode) && !r.equals(registration)).collect(Collectors.toList());
        System.out.println(preChosenRegistrations);
        if (!sameCourseFromPreviousRegistrations.isEmpty()) {
            removeRegistrations.addAll(sameCourseFromPreviousRegistrations);
        }

        // set the course class to be checked
        // set the course to be checked
        System.out.println(addRegistrations.contains(registration));
        System.out.println(removeRegistrations.contains(registration));
        System.out.println(preChosenRegistrations.contains(registration));
        if (preChosenRegistrations.contains(registration)) {
            if (removeRegistrations.contains(registration)) {
                removeRegistrations.remove(registration);
                checked = true;
            }
            else {
                removeRegistrations.add(registration);
            }
        } else {
            if (removeRegistrations.contains(registration)) {
                removeRegistrations.remove(registration);
            } else if (addRegistrations.contains(registration)) {
                addRegistrations.remove(registration);
            } else {
                addRegistrations.add(registration);
                checked = true;
            }
        }
//        if (removeRegistrations.contains(registration)) {
//            removeRegistrations.remove(registration);
//            if (!preChosenRegistrations.contains(registration)) {
//                addRegistrations.add(registration);
//            }
//            checked = true;
//        } else if (addRegistrations.contains(registration)) {
//            addRegistrations.remove(registration);
//        }  else {
//            addRegistrations.add(registration);
//            removeRegistrations.remove(registration);
//            checked = true;
//        }

        bundle.putParcelableArrayList("addRegistrations", addRegistrations);
        bundle.putParcelableArrayList("removeRegistrations", removeRegistrations);

        System.out.println(bundle);

        View fab = getView().findViewById(R.id.floatingActionButton);

        if (addRegistrations.isEmpty() && removeRegistrations.isEmpty()) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
        System.out.println(getView().findViewWithTag(registration.getCourseCode()));
        ViewGroup classesContainer = getView().findViewWithTag(registration.getCourseCode())
                .findViewById(R.id.registration_choice_container);
        int childCount = classesContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            // is a RegistrationClassView
            ((RegistrationClassView) classesContainer.getChildAt(i)).setChecked(false);
        }
        RegistrationClassView classView = getView().findViewWithTag(registration.getClassCode());
        classView.setChecked(checked);

        setArguments(bundle);
    }

    @NotNull
    private Dialog showLoading(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).create();
        dialog.setView(new ProgressBar(context));
        return dialog;
    }

    private void showSuccess(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    final NavController navController = Navigation.findNavController(getActivity(),
                            R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_course_reg);
                });
        builder.create().show();
    }

    public void onClick(View v) {

        Dialog loading = showLoading(getContext());
        loading.show();

        String authToken = getActivity().getIntent().getExtras().getString("authToken");

        Bundle bundle = getArguments();

        if (bundle == null) {
            bundle = new Bundle();
        }

        List<Registration> addRegistrations = bundle.getParcelableArrayList("addRegistrations");
        List<Registration> removeRegistrations = bundle.getParcelableArrayList("removeRegistrations");

        MutableLiveData<Integer> finishState = new MutableLiveData<>(0);

        finishState.observe(getViewLifecycleOwner(), finishedCnt -> {
            if (finishedCnt == 2) {
                // finished
                System.out.println("Finished!");
                showSuccess(getContext(), "Đăng kí thành công!");
                loading.dismiss();
            }
        });

        if (!removeRegistrations.isEmpty()) {
            registrationViewModel.removeData(removeRegistrations, authToken).observe(getViewLifecycleOwner(), finished -> {
                if (finished != null) {
                    finishState.setValue(finishState.getValue() + 1);
                }
            });
        } else {
            finishState.setValue(finishState.getValue() + 1);
        }

        if (!addRegistrations.isEmpty()) {
            registrationViewModel.addData(addRegistrations, authToken).observe(getViewLifecycleOwner(), finished -> {
                if (finished != null) {
                    finishState.setValue(finishState.getValue() + 1);
                }
            });
        } else {
            finishState.setValue(finishState.getValue() + 1);
        }
    }
}
