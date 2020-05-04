package com.hanu.students.base;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface Repository<T> {
    LiveData<List<T>> findAll(Object... args);
    LiveData<T> findOne(Object... args);
}
