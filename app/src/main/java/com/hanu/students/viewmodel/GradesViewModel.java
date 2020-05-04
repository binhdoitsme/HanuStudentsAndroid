package com.hanu.students.viewmodel;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.Grades;
import com.hanu.students.repository.GradesRepository;

public class GradesViewModel extends ListViewModel<Grades> {
    public GradesViewModel() {
        super();
        setRepository(new GradesRepository());
    }
}
