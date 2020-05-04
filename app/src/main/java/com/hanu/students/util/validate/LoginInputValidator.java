package com.hanu.students.util.validate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hanu.students.base.Repository;
import com.hanu.students.model.User;
import com.hanu.students.repository.ProfileRepository;

public class LoginInputValidator extends ViewModel {

    public LoginInputValidator() {
        super();
    }

    // return a status code indicating validation state
    public int validate(String username, String password) {
        if (username.isEmpty()) {
            return -1; // -1 = empty username
        }
        if (password.isEmpty()) {
            return  -2; // -2 = empty password
        }

        // validate by using repository
        return 0; // 0 = OK
    }
}
