package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.ClassInformation;
import com.hanu.students.repository.remote.ClassRemoteDataSource;

import java.util.List;

public class ClassRepository implements Repository<ClassInformation> {
    private RemoteDataSource<ClassInformation> remoteDataSource;

    public ClassRepository() {
        remoteDataSource = new ClassRemoteDataSource();
    }

    @Override
    public LiveData<List<ClassInformation>> findAll(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<ClassInformation> findOne(Object... args) {
        return remoteDataSource.getSingle(args);
    }
}
