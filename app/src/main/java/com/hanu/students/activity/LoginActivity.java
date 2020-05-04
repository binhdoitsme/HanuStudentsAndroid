package com.hanu.students.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hanu.students.R;
import com.hanu.students.base.ListViewModel;
import com.hanu.students.model.Profile;
import com.hanu.students.model.User;
import com.hanu.students.util.validate.LoginInputValidator;
import com.hanu.students.viewmodel.LoginViewModel;
import com.hanu.students.viewmodel.ProfileViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText studentIdInput;
    private EditText passwordInput;
    private Button submitButton;

    private final LoginInputValidator validator;
    private LoginViewModel viewModel;
    private ProfileViewModel profileViewModel;

    public LoginActivity() {
        validator = new LoginInputValidator();
        viewModel = new LoginViewModel();
        profileViewModel = new ProfileViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // simple log out handler
        Bundle extra = getIntent().getExtras();
        boolean isLoggingOut = extra == null ? false : extra.getBoolean("isLoggingOut");

        if (isLoggingOut) {
            SharedPreferences sharedPreferences = getSharedPreferences("_shared_auth", MODE_PRIVATE);
            sharedPreferences.edit().remove("auth_token").commit();
        }

        setContentView(R.layout.activity_login);

        studentIdInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        submitButton = findViewById(R.id.btn_login);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Logging in...").create();
        dialog.show();
        String username = studentIdInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        switch (validator.validate(username, password)) {
            case -1:
                studentIdInput.setError("Mã sinh viên không được bỏ trống!");
                break;
            case -2:
                passwordInput.setError("Mật khẩu không được bỏ trống!");
                break;
            case 0:
                viewModel.authenticate(new User(username, password)).observe(this, authToken -> {
                    if (authToken == null && !authToken.isEmpty()) return;
                    else {
                        SharedPreferences sharedPreferences = getSharedPreferences("_shared_auth", MODE_PRIVATE);
                        Log.i("TAG", "onClick: " + sharedPreferences.edit().clear().putString("auth_token", authToken).commit());
                        profileViewModel.getUserFromApiKey(authToken).observe(this, profile -> {
                            if (profile != null) {
                                startMainWithProfileAndAuthToken(profile, authToken);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
        }

        dialog.dismiss();
    }

    private void startMainWithProfileAndAuthToken(Profile profile, String authToken) {
        Intent navigateToMain = new Intent(LoginActivity.this, MainActivity.class);
        navigateToMain.putExtra("profile", profile);
        navigateToMain.putExtra("authToken", authToken);
        startActivity(navigateToMain);
        finish();
    }
}
