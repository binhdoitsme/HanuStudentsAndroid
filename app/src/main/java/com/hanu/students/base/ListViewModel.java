package com.hanu.students.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public abstract class ListViewModel<T> extends ViewModel {
    private Repository<T> repository;
    private MutableLiveData<List<T>> data;

    public ListViewModel() {
        data = new MutableLiveData<>();
    }

    protected ListViewModel(List<T> data) {
        this.data = new MutableLiveData<>(data);
    }

    protected LiveData<List<T>> getSelfData() {
        return this.data;
    }

    public LiveData<List<T>> getData(Object... args) {
        return repository.findAll(args);
    }

    protected Repository<T> getRepository() { return this.repository; }

    protected void setRepository(Repository<T> repository) {
        this.repository = repository;
    }

    public void updateViewModel() {
        data.setValue(getData().getValue());
    }
}
