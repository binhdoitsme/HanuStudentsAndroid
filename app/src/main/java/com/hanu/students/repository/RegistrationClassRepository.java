package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.repository.remote.RegistrationClassRemoteDataSource;

import java.util.List;

public class RegistrationClassRepository implements Repository<RegistrationClass> {

    private RemoteDataSource<RegistrationClass> remoteDataSource;

    public RegistrationClassRepository() { remoteDataSource = new RegistrationClassRemoteDataSource(); }

    @Override
    public LiveData<List<RegistrationClass>> findAll(Object... args) {
        return remoteDataSource.getList(args);
    }

    @Override
    public LiveData<RegistrationClass> findOne(Object... args) {
        throw new UnsupportedOperationException();
    }
}
