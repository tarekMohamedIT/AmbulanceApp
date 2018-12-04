package com.r3tr0.ambulanceapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.r3tr0.ambulanceapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void login(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signup(View v){

    }
}
