package com.hanu.students.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanu.students.model.RegistrationClass;
import com.hanu.students.ui.component.RegistrationCourseView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegistrationCourseListFragmentAdapter extends RecyclerView.Adapter<RegistrationCourseListFragmentAdapter.ViewHolder> {

    private Map<String, List<RegistrationClass>> mDataset;
    private View.OnClickListener onChosenListener;

    public RegistrationCourseListFragmentAdapter(Map<String, List<RegistrationClass>> dataset) {
        mDataset = dataset;
        setHasStableIds(true);
    }

    public RegistrationCourseListFragmentAdapter setOnChosenListener(View.OnClickListener onChosenListener) {
        this.onChosenListener = onChosenListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RegistrationCourseView view = new RegistrationCourseView(parent.getContext());
        view.setTag(view.getCourseCode());
        return new ViewHolder(view.setOnChosenListener(onChosenListener));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String courseCode = new LinkedList<>(mDataset.keySet()).get(position);
        List<RegistrationClass> registrationClassList = mDataset.get(courseCode);
        String courseName = registrationClassList.get(0).getCourseName();

        holder.view.setCourseCode(courseCode).setCourseName(courseName)
                .setRegistrationClassList(registrationClassList);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RegistrationCourseView view;

        public ViewHolder(@NonNull RegistrationCourseView itemView) {
            super(itemView);
            view = itemView;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
