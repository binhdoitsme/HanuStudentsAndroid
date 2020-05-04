package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.MutableRepository;
import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.base.TwoWayRemoteDataSource;
import com.hanu.students.model.TuitionFee;
import com.hanu.students.repository.remote.TuitionRemoteDataSource;

import java.util.List;

public class TuitionRepository implements MutableRepository<TuitionFee> {
    private TwoWayRemoteDataSource<TuitionFee> remoteDataSource;

    public TuitionRepository() {
        remoteDataSource = new TuitionRemoteDataSource();
    }

    @Override
    public LiveData<List<TuitionFee>> findAll(Object... args) {
        return remoteDataSource.getList(args);
    }

    @Override
    public LiveData<TuitionFee> findOne(Object... args) {
        return remoteDataSource.getSingle(args);
    }

    @Override
    public LiveData<Boolean> add(TuitionFee item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> add(List<TuitionFee> items, Object... args) {
        return remoteDataSource.addList(items, args);
    }

    @Override
    public LiveData<Boolean> remove(TuitionFee item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> remove(List<TuitionFee> items, Object... args) {
        throw new UnsupportedOperationException();
    }
}
