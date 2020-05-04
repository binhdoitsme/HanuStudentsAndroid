package com.hanu.students.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hanu.students.R;
import com.hanu.students.model.Profile;
import com.hanu.students.viewmodel.ProfileViewModel;

public class SplashActivity extends AppCompatActivity {
    private ProfileViewModel viewModel;

    public SplashActivity() {
        viewModel = new ProfileViewModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // find API in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("_shared_auth", MODE_PRIVATE);
        String apiKey = sharedPreferences.getString(getString(R.string.pref_auth_token), "");

        if (apiKey.equals("")) {
            startLoginActivity();
            return;
        } else {
            // get user profile by token
            viewModel.getUserFromApiKey(apiKey).observe(this, profile -> {
                // if user token is still valid then return the profile
                // else the profile is null
//                Log.i("TAG", "onCreate: " + profile.toString());
                if (profile == null) {
                    startLoginActivity();
                    return;
                } else {
                    startMainWithProfileAndAuthToken(profile, apiKey);
                }
            });
        }
    }

    private void startMainWithProfileAndAuthToken(Profile profile, String authToken) {
        Intent navigateToMain = new Intent(SplashActivity.this, MainActivity.class);
        navigateToMain.putExtra("profile", profile);
        navigateToMain.putExtra("authToken", authToken);
        startActivity(navigateToMain);
        finish();
    }

    private void startLoginActivity() {
        // Start login activity
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        // close splash activity
        finish();
    }
}
