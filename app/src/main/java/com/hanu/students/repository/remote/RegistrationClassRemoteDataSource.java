package com.hanu.students.repository.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.RegistrationClass;
import com.hanu.students.model.TimetableUnit;

import java.util.LinkedList;
import java.util.List;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationClassRemoteDataSource extends RemoteDataSource<RegistrationClass> {

    @Override
    public LiveData<List<RegistrationClass>> getList(Object... args) {
        var data = new MutableLiveData<List<RegistrationClass>>();

        String authToken = (String) args[0];
        int semesterId = (Integer) args[1];

        super.getConnector().getAvailableClasses(authToken, semesterId)
                .enqueue(new Callback<List<RegistrationClass>>() {
            @Override
            public void onResponse(Call<List<RegistrationClass>> call, Response<List<RegistrationClass>> response) {
                if (response.body() == null) {
                    return;
                } else if (response.body().isEmpty()) {
                    data.setValue(new LinkedList<>());
                    return;
                }
                Log.i(getClass().getName(), "onResponse: " + response.body().toString());

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<RegistrationClass>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    @Override
    public LiveData<RegistrationClass> getSingle(Object... args) {
        throw new UnsupportedOperationException();
    }
}
