package com.hanu.students.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.repository.TimetableRepository;

import java.util.Collection;
import java.util.List;

public class TimetableViewModel extends ListViewModel<TimetableUnit> {

    public TimetableViewModel() {
        super();
        setRepository(new TimetableRepository());
    }
}
