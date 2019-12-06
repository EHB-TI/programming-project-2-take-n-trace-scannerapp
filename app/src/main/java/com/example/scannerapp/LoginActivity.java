package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View v)
    {
        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }
}
