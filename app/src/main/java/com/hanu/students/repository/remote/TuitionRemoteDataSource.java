package com.hanu.students.repository.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.TwoWayRemoteDataSource;
import com.hanu.students.model.TuitionFee;

import java.util.LinkedList;
import java.util.List;

import lombok.var;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuitionRemoteDataSource extends TwoWayRemoteDataSource<TuitionFee> {
    @Override
    public LiveData<List<TuitionFee>> getList(Object... args) {
        var data = new MutableLiveData<List<TuitionFee>>();
        String authToken = (String) args[0];

        super.getConnector().getTuition(authToken).enqueue(new Callback<List<TuitionFee>>() {
            @Override
            public void onResponse(Call<List<TuitionFee>> call, Response<List<TuitionFee>> response) {
                if (response.body() == null) {
                    data.setValue(new LinkedList<>());
                    return;
                }
                data.getValue().addAll(response.body());
                data.setValue(data.getValue());
            }

            @Override
            public void onFailure(Call<List<TuitionFee>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return data;
    }

    @Override
    public LiveData<TuitionFee> getSingle(Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> addSingle(TuitionFee item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> addList(List<TuitionFee> items, Object... args) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        String authToken = "auth";
        super.getConnector().postTuitionFee(items, authToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        Log.i(getClass().getName(), "onResponse: " + response.body().toString());
                        liveData.setValue(true);
                        break;
                    case 400:
                    case 500:
                        Log.i("TAG", "onResponse: " + response.body().toString());
                        liveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(false);
                t.printStackTrace();
            }
        });
        return liveData;
    }

    @Override
    public LiveData<Boolean> removeSingle(TuitionFee item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> removeList(List<TuitionFee> items, Object... args) {
        throw new UnsupportedOperationException();
    }
}
