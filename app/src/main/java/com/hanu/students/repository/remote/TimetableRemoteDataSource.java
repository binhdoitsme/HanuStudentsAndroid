package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.RemoteDataSource;
import com.hanu.students.model.TimetableUnit;

import java.util.LinkedList;
import java.util.List;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableRemoteDataSource extends RemoteDataSource<TimetableUnit> {

    public TimetableRemoteDataSource() {
        super();
    }

    @Override
    public LiveData<List<TimetableUnit>> getList(Object... args) {
        var data = new MutableLiveData<List<TimetableUnit>>(null);

        String authToken = (String) args[0];

        super.getConnector().getTimetable(authToken).enqueue(new Callback<List<TimetableUnit>>() {
            @Override
            public void onResponse(Call<List<TimetableUnit>> call, Response<List<TimetableUnit>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<TimetableUnit>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    @Override
    public LiveData<TimetableUnit> getSingle(Object... args) {
        throw new UnsupportedOperationException();
    }
}
