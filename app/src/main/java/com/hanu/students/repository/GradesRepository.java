package com.hanu.students.repository;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.ClassInformation;
import com.hanu.students.model.Grades;
import com.hanu.students.repository.remote.GradesRemoteDataSource;

import java.util.List;

public class GradesRepository implements Repository<Grades> {

    private RemoteDataSource<Grades> remoteDataSource;

    public GradesRepository() {
        remoteDataSource = new GradesRemoteDataSource();
    }

    @Override
    public LiveData<List<Grades>> findAll(Object... args) {
        return remoteDataSource.getList(args);
    }

    @Override
    public LiveData<Grades> findOne(Object... args) {
        throw new UnsupportedOperationException();
    }
}
