package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.Grades;

import java.util.LinkedList;
import java.util.List;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradesRemoteDataSource extends RemoteDataSource<Grades> {
    public GradesRemoteDataSource() { super(); }

    @Override
    public LiveData<List<Grades>> getList(Object... args) {
        var data = new MutableLiveData<List<Grades>>(new LinkedList<>());

        String authToken = (String)args[0];

        super.getConnector().getGrades(authToken).enqueue(new Callback<List<Grades>>() {
            @Override
            public void onResponse(Call<List<Grades>> call, Response<List<Grades>> response) {
                if (response.body() != null) {
                    data.getValue().addAll(response.body());
                }
                data.setValue(data.getValue());
            }

            @Override
            public void onFailure(Call<List<Grades>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    @Override
    public LiveData<Grades> getSingle(Object... args) {
        throw new UnsupportedOperationException();
    }


}
