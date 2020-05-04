package com.hanu.students.ui.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentRegistrationChoiceRowBinding;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.util.converter.DateConverter;

import java.util.LinkedList;
import java.util.List;

public class RegistrationClassView extends ConstraintLayout {

    private static final int INITIAL_SELECTED = Color.parseColor("#1976D2");
    private static final int TO_BE_DELETED = Color.parseColor("#D32F2F");
    private static final int TO_BE_SELECTED = Color.parseColor("#FFA000");
    private static final int INITIAL_UNSELECTED = Color.parseColor("#FAFAFA");

    private ComponentRegistrationChoiceRowBinding binder;
    private boolean checked;
    private boolean initialState;
    private Button selectButton;

    public RegistrationClassView(Context context) {
        super(context);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                                R.layout.component_registration_choice_row, this, true);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
        selectButton = findViewById(R.id.registration_choose_button);

    }

    public RegistrationClassView setRegistrationClass(RegistrationClass registrationClass) {
        binder.setRegistrationChoice(registrationClass);
        ViewGroup timetableContainer = findViewById(R.id.registration_time_text_container);

        /////////////////////
        List<TimetableUnit> timetableUnits = new LinkedList<>();
        timetableUnits.addAll(registrationClass.getTimetables());
        //////////////////////

        for (TimetableUnit t : timetableUnits) {
            TextView textView = new TextView(getContext());
            textView.setText(DateConverter.from(t.getDayOfWeek()) + ", " + t.getTimeStart().substring(0, 5) + "-" + t.getTimeEnd().substring(0, 5));
            timetableContainer.addView(textView);
        }
        return this;
    }

    public RegistrationClass getRegistrationClass() {
        return binder.getRegistrationChoice();
    }

    public RegistrationClassView setOnClassChosen(OnClickListener onClassChosenListener) {
        binder.setOnClassChosen(onClassChosenListener);
        return this;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
        selectButton.setText(this.checked ? "Hủy" : "Chọn");
    }

    public RegistrationClassView setInitialState(final boolean initialState) {
        this.initialState = initialState;
        setChecked(initialState);
        return this;
    }

    public boolean isChecked() {
        return checked;
    }
}
