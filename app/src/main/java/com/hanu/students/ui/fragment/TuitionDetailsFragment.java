package com.hanu.students.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hanu.students.R;
import com.hanu.students.model.TuitionFee;
import com.hanu.students.ui.component.TuitionRowView;

import java.util.List;

public class TuitionDetailsFragment extends Fragment {

    public TuitionDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuition_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        renderTuitionFeeList();
        addEventListenerOnPayFeeButton();
    }

    private void addEventListenerOnPayFeeButton() {
        Button payTuitionBtn = getView().findViewById(R.id.btn_pay_tuition);
        if (payTuitionBtn != null)
            payTuitionBtn.setOnClickListener((TuitionFragment)getParentFragment());
    }

    private void renderTuitionFeeList() {
        TableLayout container = getView().findViewById(R.id.tuition_rows_container);
        Context context = getContext();

        Bundle bundle = getParentFragment().getArguments();
        List<TuitionFee> tuitionList = bundle.getParcelableArrayList("tuitionList");

        System.out.println(tuitionList);

        int rowId = 0;
        long aggregateVal = 0;
        int aggregateCredCnt = 0;
        for (TuitionFee tuition : tuitionList) {
            if (tuition.isPaid()) continue;
            TableRow row = new TuitionRowView(context).setRowId(++rowId).setTuition(tuition);
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            container.addView(row);
            aggregateVal += tuition.getValue();
            aggregateCredCnt += tuition.getCreditCount();
        }
        renderAggregate(aggregateCredCnt, aggregateVal, context, container);
    }

    private void renderAggregate(int aggregateCredCnt, long aggregateVal, Context context, ViewGroup container) {
        if (aggregateCredCnt > 0) {
            TableRow row = new TuitionRowView(context).setRowId(null)
                    .setTuition(new TuitionFee(
                            "Tổng",
                            aggregateCredCnt,
                            aggregateVal,
                            false));
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            container.addView(row);
        } else {
            TextView noMoreTuitionToPayText = new TextView(context);
            noMoreTuitionToPayText.setText(R.string.label_no_more_fee);
            noMoreTuitionToPayText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            container.addView(noMoreTuitionToPayText);
            ViewGroup viewGroup = (ViewGroup) getView();
            viewGroup.removeView(viewGroup.findViewById(R.id.tuition_label_summary));
            viewGroup.removeView(viewGroup.findViewById(R.id.btn_pay_tuition));
        }
    }
}
