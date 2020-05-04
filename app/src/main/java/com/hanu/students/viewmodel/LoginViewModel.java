package com.hanu.students.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hanu.students.base.Repository;
import com.hanu.students.model.User;
import com.hanu.students.repository.remote.AuthRepository;

public class LoginViewModel extends ViewModel {
    private Repository<String> authTokenRepository;

    public LoginViewModel() {
        authTokenRepository = new AuthRepository();
    }

    public LiveData<String> authenticate(User user) {
        return authTokenRepository.findOne(user);
    }
}
