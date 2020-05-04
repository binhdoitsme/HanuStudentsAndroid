package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.ClassInformation;

import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassRemoteDataSource extends RemoteDataSource<ClassInformation> {
    @Override
    public LiveData<List<ClassInformation>> getList(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<ClassInformation> getSingle(Object... args) {
        if (args.length != 2) {
            throw new IllegalStateException();
        }
        String classCode = (String)args[0];
        String authToken = (String)args[1];

        MutableLiveData<ClassInformation> data = new MutableLiveData<>();
        getConnector().getClassInformation(classCode, authToken, 20192)
                .enqueue(new Callback<ClassInformation>() {
            @Override
            public void onResponse(Call<ClassInformation> call, Response<ClassInformation> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ClassInformation> call, Throwable t) {
                Logger.getLogger(getClass().getName()).warning(t.getMessage());
            }
        });
        return data;
    }
}
