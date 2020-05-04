package com.hanu.students.base;

import androidx.lifecycle.LiveData;

import java.util.List;

public abstract class TwoWayRemoteDataSource<T> extends RemoteDataSource<T> {
    public abstract LiveData<Boolean> addSingle(T item, Object... args);
    public abstract LiveData<Boolean> addList(List<T> items, Object... args);
    public abstract LiveData<Boolean> removeSingle(T item, Object... args);
    public abstract LiveData<Boolean> removeList(List<T> items, Object... args);
}
