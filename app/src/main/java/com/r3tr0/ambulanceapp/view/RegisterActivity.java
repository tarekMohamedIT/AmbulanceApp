package com.r3tr0.ambulanceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.r3tr0.ambulanceapp.MainActivity;
import com.r3tr0.ambulanceapp.R;
import com.r3tr0.ambulanceapp.model.events.OnFirebaseProcessEndListener;
import com.r3tr0.ambulanceapp.model.events.OnValidationProcessEndListener;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseHelper;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseManager;
import com.r3tr0.ambulanceapp.model.models.User;

public class RegisterActivity extends BaseAuthActivity {
    FirebaseManager manager;


    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText phoneEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initManager();
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
                    case FirebaseHelper.CHECK_FIRST_NAME:
                        Toast.makeText(RegisterActivity.this, "Invalid first name", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_LAST_NAME:
                        Toast.makeText(RegisterActivity.this, "Invalid last name", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_EMAIL:
                        Toast.makeText(RegisterActivity.this, "Invalid E-mail", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_PASSWORD:
                        Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_PHONE:
                        Toast.makeText(RegisterActivity.this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        manager.setOnFirebaseProcessEndListener(new OnFirebaseProcessEndListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                hideProgressDialog();
                Toast.makeText(RegisterActivity.this, "You are a valid driver now!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFail(Exception e) {
                hideProgressDialog();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initViews() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
    }

    public void register(View v) {
        showProgressDialog("Registering ...");

        manager.signUp(
                new User(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        phoneEditText.getText().toString()
                ));
    }

}
