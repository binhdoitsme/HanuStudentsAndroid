package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRemoteDataSource extends RemoteDataSource<String> {
    @Override
    public LiveData<List<String>> getList(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<String> getSingle(Object... args) {
        User user = (User)args[0];
        MutableLiveData<String> result = new MutableLiveData<>();
        getConnector().startSession(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200)
                    result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                result.setValue(new String());
            }
        });
        return result;
    }
}
