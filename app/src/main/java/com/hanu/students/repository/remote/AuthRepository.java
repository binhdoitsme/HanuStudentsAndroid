package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;

import java.util.List;

public class AuthRepository implements Repository<String> {
    private RemoteDataSource<String> remoteDataSource;

    public AuthRepository() {
        remoteDataSource = new AuthRemoteDataSource();
    }

    @Override
    public LiveData<List<String>> findAll(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<String> findOne(Object... args) {
        return remoteDataSource.getSingle(args);
    }
}
