package com.hanu.students.base;

import androidx.lifecycle.LiveData;

import com.hanu.students.repository.remote.api.ApiConnector;
import com.hanu.students.util.api.NetworkProvider;

import java.util.List;

public abstract class RemoteDataSource<T> {
    private final ApiConnector apiConnector;

    public RemoteDataSource() {
        apiConnector = NetworkProvider.self().getRetrofit().create(ApiConnector.class);
    }

    protected ApiConnector getConnector() {
        return this.apiConnector;
    }

    public abstract LiveData<List<T>> getList(Object... args);

    public abstract LiveData<T> getSingle(Object... args);
}
