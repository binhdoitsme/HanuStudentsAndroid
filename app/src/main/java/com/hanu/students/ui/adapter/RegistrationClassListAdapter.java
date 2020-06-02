package com.hanu.students.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanu.students.model.RegistrationClass;
import com.hanu.students.ui.component.RegistrationClassView;
import com.hanu.students.ui.wrapper.RegistrationClassWrapper;

import java.util.List;

public class RegistrationClassListAdapter extends RecyclerView.Adapter<RegistrationClassListAdapter.ViewHolder> {

    private final List<RegistrationClassWrapper> registrationClassList;

    public RegistrationClassListAdapter(List<RegistrationClassWrapper> registrationClassList) {
        this.registrationClassList = registrationClassList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new RegistrationClassView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegistrationClassWrapper registrationClass = registrationClassList.get(position);
        holder.view.setRegistrationClass(registrationClass);
    }

    @Override
    public int getItemCount() {
        return registrationClassList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RegistrationClassView view;

        public ViewHolder(@NonNull RegistrationClassView itemView) {
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
