package com.hanu.students.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableRow;

import androidx.databinding.DataBindingUtil;

import com.hanu.students.R;
import com.hanu.students.databinding.ComponentTuitionRowBinding;
import com.hanu.students.model.TuitionFee;

public class TuitionRowView extends TableRow {

    private ComponentTuitionRowBinding binder;

    public TuitionRowView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        binder = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.component_tuition_row, this, true);
    }

    public TuitionRowView setRowId(Integer rowId) {
        binder.setRowId(rowId != null ? rowId.toString() : "");
        return this;
    }

    public TuitionRowView setTuition(TuitionFee tuition) {
        binder.setTuition(tuition);
        return this;
    }
}
