package com.hanu.students.viewmodel;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.Student;

import java.util.List;

// this view model does not need a remote data source
// data will be passed from outside classes
public class StudentListViewModel extends ListViewModel<Student> {
    public StudentListViewModel(List<Student> studentList) {
        super(studentList);
    }

    @Override
    public LiveData<List<Student>> getData(Object... args) {
        return super.getSelfData();
    }
}
