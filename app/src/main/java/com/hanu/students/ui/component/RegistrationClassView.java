package com.hanu.students.ui.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentRegistrationChoiceRowBinding;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.ui.wrapper.RegistrationClassWrapper;
import com.hanu.students.util.converter.DateConverter;

import java.util.LinkedList;
import java.util.List;

public class RegistrationClassView extends ConstraintLayout {

    private static final int INITIAL_SELECTED = Color.parseColor("#1976D2");
    private static final int TO_BE_DELETED = Color.parseColor("#D32F2F");
    private static final int TO_BE_SELECTED = Color.parseColor("#FFA000");
    private static final int INITIAL_UNSELECTED = Color.parseColor("#FAFAFA");

    private Runnable onClassChosenHandler;

    private ComponentRegistrationChoiceRowBinding binder;
    private Button selectButton;
    private boolean checked;

    public RegistrationClassView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_registration_choice_row, this, true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        selectButton = findViewById(R.id.registration_choose_button);
    }

    public RegistrationClassView setRegistrationClass(RegistrationClassWrapper registrationClassWrapper) {
        RegistrationClass registrationClass = registrationClassWrapper.getRegistrationClass();
        binder.setRegistrationChoice(registrationClass);
        ViewGroup timetableContainer = findViewById(R.id.registration_time_text_container);
        timetableContainer.removeAllViews();

        /////////////////////
        List<TimetableUnit> timetableUnits = new LinkedList<>(registrationClass.getTimetables());
        //////////////////////

        for (TimetableUnit t : timetableUnits) {
            TextView textView = new TextView(getContext());
            textView.setText(DateConverter.from(t.getDayOfWeek()) + ", " + t.getTimeStart().substring(0, 5) + "-" + t.getTimeEnd().substring(0, 5));
            timetableContainer.addView(textView);
        }

        initViewState(registrationClassWrapper.getInitialState(),
                        registrationClassWrapper.getCurrentState());
        setDisable(registrationClassWrapper.isDisabled());

        if (registrationClassWrapper.isDisabled()) {
            selectButton.setText("Trùng");
        } else {
            selectButton.setText(registrationClassWrapper.getCurrentState() ? "Hủy" : "Chọn");
        }
        return this;
    }

    private void initViewState(boolean initialState, boolean checked) {
        if (initialState) {
            if (!checked) {
                setBackgroundColor(TO_BE_DELETED);
            } else {
                setBackgroundColor(INITIAL_SELECTED);
            }
        } else {
            if (checked) {
                setBackgroundColor(TO_BE_SELECTED);
            } else {
                setBackgroundColor(INITIAL_UNSELECTED);
            }
        }
//        selectButton.setText(checked ? "Hủy" : "Chọn");
    }

    public RegistrationClassView setOnClassChosen(Runnable onClassChosenHandler) {
        this.onClassChosenHandler = onClassChosenHandler;
        binder.setOnClassChosen(v -> onClassChosenHandler.run());
        return this;
    }

    public RegistrationClassView setDisable(boolean isDisabled) {
        selectButton.setEnabled(!isDisabled);
        return this;
    }
}
