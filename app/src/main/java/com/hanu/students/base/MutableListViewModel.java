package com.hanu.students.base;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.ListViewModel;

import java.util.List;

public abstract class MutableListViewModel<T> extends ListViewModel<T> {
    public LiveData<Boolean> addData(List<T> data, Object... args) {
        MutableRepository<T> repository = (MutableRepository<T>)getRepository();
        return repository.add(data, args);
    }

    public LiveData<Boolean> addSingle(T data, Object... args) {
        MutableRepository<T> repository = (MutableRepository<T>)getRepository();
        return repository.add(data, args);
    }

    public LiveData<Boolean> removeData(List<T> data, Object... args) {
        MutableRepository<T> repository = (MutableRepository<T>)getRepository();
        return repository.remove(data, args);
    }

    public LiveData<Boolean> removeSingle(T data, Object... args) {
        MutableRepository<T> repository = (MutableRepository<T>)getRepository();
        return repository.remove(data, args);
    }
}
