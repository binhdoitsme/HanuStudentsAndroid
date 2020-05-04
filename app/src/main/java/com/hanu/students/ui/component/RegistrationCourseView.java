package com.hanu.students.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentRegistrationCourseRowBinding;
import com.hanu.students.model.RegistrationClass;

import java.util.ArrayList;
import java.util.List;

public class RegistrationCourseView extends ConstraintLayout {

    private ComponentRegistrationCourseRowBinding binder;
    private List<RegistrationClass> registrationClassList;
    private OnClickListener onChosenListener;
    private boolean checked;

    public RegistrationCourseView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_registration_course_row, this, true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        binder.setOnClick((view) -> {
            View container = ((View) view.getParent()).findViewById(R.id.registration_choice_container);
            int visibility = container.getVisibility();
            container.setVisibility(visibility == GONE ? VISIBLE : GONE);
        });
    }

    public String getCourseCode() {
        return binder.getCourseCode();
    }

    public RegistrationCourseView setRegistrationClassList(List<RegistrationClass> registrationClassList) {
        if (this.registrationClassList == null) {
            this.registrationClassList = registrationClassList;
            renderRegistrationClasses(registrationClassList);
        }
        return this;
    }

    private void renderRegistrationClasses(List<RegistrationClass> registrationClassList) {
        ViewGroup container = findViewById(R.id.registration_choice_container);
        // create views
        List<View> viewsToAdd = new ArrayList<>();
        for (RegistrationClass registrationClass : registrationClassList) {
            RegistrationClassView view = new RegistrationClassView(getContext()).setRegistrationClass(registrationClass)
                    .setOnClassChosen(this.onChosenListener);
            String classCode = registrationClass.getClassCode();
            view.setTag(classCode);
            viewsToAdd.add(view);
        }

        for (View view : viewsToAdd) {
            container.addView(view);
        }
    }

    public RegistrationCourseView setCourseCode(String courseCode) {
        binder.setCourseCode(courseCode);
        return this;
    }

    public RegistrationCourseView setCourseName(String courseName) {
        binder.setCourseName(courseName);
        return this;
    }

    public RegistrationCourseView setOnChosenListener(OnClickListener onChosenListener) {
        this.onChosenListener = onChosenListener;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
