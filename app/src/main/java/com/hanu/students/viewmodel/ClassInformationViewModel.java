package com.hanu.students.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hanu.students.base.Repository;
import com.hanu.students.model.ClassInformation;
import com.hanu.students.repository.ClassRepository;

// TODO: finish this!
public class ClassInformationViewModel extends ViewModel {
    private MutableLiveData<ClassInformation> data;
    private Repository<ClassInformation> repository;
    private final String classCode;

    public ClassInformationViewModel(String classCode) {
        data = new MutableLiveData<>();
        this.classCode = classCode;
        this.repository = new ClassRepository();
    }

    public LiveData<ClassInformation> getData() {
        String authToken = "auth";
        return repository.findOne(classCode, authToken);
    }
}
