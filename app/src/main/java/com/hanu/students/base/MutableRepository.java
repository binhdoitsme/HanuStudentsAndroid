package com.hanu.students.base;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface MutableRepository<T> extends Repository<T> {
    LiveData<Boolean> add(T item, Object... args);
    LiveData<Boolean> add(List<T> items, Object... args);
    LiveData<Boolean> remove(T item, Object... args);
    LiveData<Boolean> remove(List<T> items, Object... args);
}
