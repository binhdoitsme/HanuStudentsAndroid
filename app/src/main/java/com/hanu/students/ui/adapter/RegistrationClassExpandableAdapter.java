package com.hanu.students.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.hanu.students.R;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.ui.component.RegistrationClassView;
import com.hanu.students.ui.component.RegistrationCourseView;
import com.hanu.students.ui.wrapper.RegistrationClassWrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class RegistrationClassExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<RegistrationClassWrapper>> classesByCourse;
    private List<String> keyList;
    private Consumer<RegistrationClassWrapper> onRegistrationClassSelected;

    public RegistrationClassExpandableAdapter(Context context, Map<String, List<RegistrationClassWrapper>> classesByCourse) {
        this.context = context;
        this.classesByCourse = classesByCourse;
        this.keyList = new LinkedList<>(classesByCourse.keySet());
    }

    @Override
    public int getGroupCount() {
        return classesByCourse.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return classesByCourse.get(keyList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return classesByCourse.get(keyList.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return classesByCourse.get(keyList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        List<RegistrationClassWrapper> registrationClasses = (List<RegistrationClassWrapper>) getGroup(groupPosition);
        if (convertView == null) {
            convertView = new RegistrationCourseView(context);
        }
        ((RegistrationCourseView)convertView).setCourseCode(registrationClasses.get(0).getRegistrationClass().getCourseCode())
                .setCourseName(registrationClasses.get(0).getRegistrationClass().getCourseName())
                .setOnChosenListener(v -> System.out.println("onChosen: " + v))
                .setChecked(registrationClasses.stream().anyMatch(rc -> rc.getCurrentState()));
        return convertView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final RegistrationClassWrapper childContent = (RegistrationClassWrapper) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = new RegistrationClassView(context);
        }
        ((RegistrationClassView)convertView)
                .setRegistrationClass(childContent)
                .setOnClassChosen(() -> {
                    String courseCode = childContent.getRegistrationClass().getCourseCode();
                    List<RegistrationClassWrapper> classesForCourseCode = classesByCourse.get(courseCode);
                    for (RegistrationClassWrapper registrationClass : classesForCourseCode) {
                        if (registrationClass.getRegistrationClass().equals(childContent.getRegistrationClass())) continue;
                        registrationClass.setCurrentState(false);
                        onRegistrationClassSelected.accept(registrationClass);
                    }
                    childContent.setCurrentState(!childContent.getCurrentState());
                    onRegistrationClassSelected.accept(childContent);
                    notifyDataSetChanged();
                });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setOnRegistrationClassSelected(Consumer<RegistrationClassWrapper> onRegistrationClassSelected) {
        this.onRegistrationClassSelected = onRegistrationClassSelected;
    }
}
