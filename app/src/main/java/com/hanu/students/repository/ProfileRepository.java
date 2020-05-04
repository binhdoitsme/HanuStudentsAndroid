package com.hanu.students.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.base.Repository;
import com.hanu.students.model.Profile;
import com.hanu.students.model.User;
import com.hanu.students.repository.remote.ProfileRemoteDataSource;

import java.util.List;

public class ProfileRepository implements Repository<Profile> {
    private RemoteDataSource<Profile> remoteDataSource;

    public ProfileRepository() {
        remoteDataSource = new ProfileRemoteDataSource();
    }

    @Override
    public LiveData<List<Profile>> findAll(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Profile> findOne(Object... args) {

        if (args.length == 1) {
            String apiKey = (String) args[0];
            return remoteDataSource.getSingle(apiKey);
        }
        if (args.length == 2) { // username & password
            String username = (String) args[0];
            String password = (String) args[1];
            User user = new User(username, password);
            return remoteDataSource.getSingle(user);
        }

        return null;
    }
}
