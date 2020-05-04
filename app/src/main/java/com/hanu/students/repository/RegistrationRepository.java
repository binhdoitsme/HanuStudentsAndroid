package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.MutableRepository;
import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.TwoWayRemoteDataSource;
import com.hanu.students.model.Registration;
import com.hanu.students.repository.remote.RegistrationRemoteDataSource;

import java.util.List;

public class RegistrationRepository implements MutableRepository<Registration> {

    private TwoWayRemoteDataSource<Registration> remoteDataSource;

    public RegistrationRepository() { remoteDataSource = new RegistrationRemoteDataSource(); }

    @Override
    public LiveData<Boolean> add(Registration item, Object... args) {
        return remoteDataSource.addSingle(item, args);
    }

    @Override
    public LiveData<Boolean> add(List<Registration> items, Object... args) {
        return remoteDataSource.addList(items, args);
    }

    @Override
    public LiveData<Boolean> remove(Registration item, Object... args) {
        return remoteDataSource.removeSingle(item, args);
    }

    @Override
    public LiveData<Boolean> remove(List<Registration> items, Object... args) {
        return remoteDataSource.removeList(items, args);
    }

    @Override
    public LiveData<List<Registration>> findAll(Object... args) {
        return remoteDataSource.getList(args);
    }

    @Override
    public LiveData<Registration> findOne(Object... args) {
        return remoteDataSource.getSingle(args);
    }
}
