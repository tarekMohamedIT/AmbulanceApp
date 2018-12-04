package com.r3tr0.ambulanceapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.r3tr0.ambulanceapp.R;
import com.r3tr0.ambulanceapp.model.events.OnFirebaseProcessEndListener;
import com.r3tr0.ambulanceapp.model.events.OnValidationProcessEndListener;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseHelper;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseManager;

public class LoginActivity extends BaseAuthActivity {
    FirebaseManager manager;
    EditText emailEdittext;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    void initManager() {
        manager = new FirebaseManager(this);

        manager.setOnValidationProcessEndListener(new OnValidationProcessEndListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail(int id) {
                switch (id) {
                    case FirebaseHelper.CHECK_EMAIL:
                        Toast.makeText(LoginActivity.this, "Invalid E-mail", Toast.LENGTH_SHORT).show();
                        break;

                    case FirebaseHelper.CHECK_PASSWORD:
                        Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        manager.setOnFirebaseProcessEndListener(new OnFirebaseProcessEndListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                
            }

            @Override
            public void onFail() {

            }
        });

    }

    void initViews() {

    }

    public void login(View v) {

    }
}
