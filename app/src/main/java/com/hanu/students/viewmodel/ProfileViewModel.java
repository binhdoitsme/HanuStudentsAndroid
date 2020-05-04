package com.hanu.students.viewmodel;

import androidx.lifecycle.LiveData;

import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.Profile;
import com.hanu.students.model.User;
import com.hanu.students.repository.ProfileRepository;

public class ProfileViewModel extends ListViewModel<Profile> {
    public ProfileViewModel() {
        super();
        setRepository(new ProfileRepository());
    }

    public LiveData<Profile> getUserFromApiKey(String apiKey) {
        return getRepository().findOne(apiKey);
    }
}
