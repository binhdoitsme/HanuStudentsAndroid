package com.hanu.students.viewmodel;

import com.hanu.students.base.MutableListViewModel;
import com.hanu.students.model.TuitionFee;
import com.hanu.students.repository.TuitionRepository;

public class TuitionViewModel extends MutableListViewModel<TuitionFee> {
    public TuitionViewModel() {
        super();
        setRepository(new TuitionRepository());
    }
}
