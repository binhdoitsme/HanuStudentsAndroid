package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRemoteDataSource extends RemoteDataSource<Profile> {

    @Override
    public LiveData<List<Profile>> getList(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Profile> getSingle(Object... args) {
        String apiKey = (String) args[0];
        MutableLiveData<Profile> data = new MutableLiveData<>();
        super.getConnector().getProfile(apiKey).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
                data.setValue(null);
            }
        });
        return data;
    }
}
