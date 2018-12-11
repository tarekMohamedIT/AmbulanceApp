package com.r3tr0.ambulanceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.r3tr0.ambulanceapp.MainActivity;
import com.r3tr0.ambulanceapp.R;
import com.r3tr0.ambulanceapp.model.events.OnFirebaseProcessEndListener;
import com.r3tr0.ambulanceapp.model.events.OnValidationProcessEndListener;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseHelper;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseManager;
import com.r3tr0.ambulanceapp.model.models.User;

public class LoginActivity extends BaseAuthActivity {
    FirebaseManager manager;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initManager();
        initViews();
    }

    void initManager() {
        manager = new FirebaseManager(this);

        manager.setOnValidationProcessEndListener(new OnValidationProcessEndListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail(int id) {
                hideProgressDialog();
                switch (id) {
                    case FirebaseHelper.CHECK_EMAIL:
                        showMessage("Error", "Invalid E-mail", 0);
                        //Toast.makeText(LoginActivity.this, "Invalid E-mail", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_PASSWORD:
                        showMessage("Error", "Invalid password format", 0);
                        break;
                }
            }
        });

        manager.setOnFirebaseProcessEndListener(new OnFirebaseProcessEndListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                hideProgressDialog();
                showMessage("Success", "You are logged in!", 1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                hideProgressDialog();
                showMessage("Error", e.getMessage(), 0);
            }
        });
    }

    void initViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void login(View v) {
        showProgressDialog("Logging in ...");

        manager.signIn(
                new User(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString()
                ));
    }
}
