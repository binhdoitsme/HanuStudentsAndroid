package com.hanu.students.viewmodel;

import com.hanu.students.base.MutableListViewModel;
import com.hanu.students.model.Registration;
import com.hanu.students.repository.RegistrationRepository;

public class RegistrationViewModel extends MutableListViewModel<Registration> {
    public RegistrationViewModel() {
        super();
        setRepository(new RegistrationRepository());
    }
}
