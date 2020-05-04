package com.hanu.students.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hanu.students.base.TwoWayRemoteDataSource;
import com.hanu.students.model.Registration;

import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRemoteDataSource extends TwoWayRemoteDataSource<Registration> {
    @Override
    public LiveData<Boolean> addSingle(Registration item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> addList(List<Registration> items, Object... args) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>(null);
        String authToken = (String) args[0];
        getConnector().addNewRegistrations(authToken, items).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(true);
                } else {
                    liveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(false);
            }
        });
        return liveData;
    }

    @Override
    public LiveData<Boolean> removeSingle(Registration item, Object... args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LiveData<Boolean> removeList(List<Registration> items, Object... args) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>(null);
        String authToken = (String) args[0];
        getConnector().removeRegistrations(authToken, items).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(true);
                } else {
                    liveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(false);
            }
        });
        return liveData;
    }

    @Override
    public LiveData<List<Registration>> getList(Object... args) {
        MutableLiveData<List<Registration>> liveData = new MutableLiveData<>();
        String authToken = (String) args[0];
        int semesterId = (Integer) args[1];
        getConnector().getRegisteredClassesThisSemester(authToken, semesterId)
                .enqueue(new Callback<List<Registration>>() {
                    @Override
                    public void onResponse(Call<List<Registration>> call, Response<List<Registration>> response) {
                        List<Registration> registrationList = new LinkedList<>();
                        registrationList.addAll(response.body());
                        liveData.setValue(registrationList);
                    }

                    @Override
                    public void onFailure(Call<List<Registration>> call, Throwable t) {

                    }
                });
        return liveData;
    }

    @Override
    public LiveData<Registration> getSingle(Object... args) {
//        MutableLiveData<Registration> liveData = new MutableLiveData<>();
//        getConnector().getRegisteredClassesThisSemester("auth", 20192)
//                .enqueue(new Callback<Registration>() {
//                    @Override
//                    public void onResponse(Call<Registration> call, Response<Registration> response) {
//                        liveData.setValue(response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Registration> call, Throwable t) {
//
//                    }
//                });
//        return liveData;
        throw new UnsupportedOperationException();
    }
}
