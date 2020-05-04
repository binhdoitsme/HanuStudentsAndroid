package com.hanu.students.viewmodel;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.repository.RegistrationClassRepository;

public class RegistrationClassViewModel extends ListViewModel<RegistrationClass> {
    public RegistrationClassViewModel() {
        super();
        super.setRepository(new RegistrationClassRepository());
    }
}
