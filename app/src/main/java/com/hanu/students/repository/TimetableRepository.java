package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.TimetableUnit;
import com.hanu.students.repository.remote.TimetableRemoteDataSource;

import java.util.List;

public class TimetableRepository implements Repository<TimetableUnit> {
    private RemoteDataSource<TimetableUnit> remoteDataSource;

    public TimetableRepository() {
        remoteDataSource = new TimetableRemoteDataSource();
    }

    public LiveData<List<TimetableUnit>> findAll(Object... args) {
        return remoteDataSource.getList(args);
    }

    @Override
    public LiveData<TimetableUnit> findOne(Object... args) {
        throw new UnsupportedOperationException();
    }
}
