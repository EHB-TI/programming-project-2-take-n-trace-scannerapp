package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auth0.android.Auth0;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

public class LoginActivity extends AppCompatActivity {

    Auth0 account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = new Auth0(this);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickLogin(View v)
    {
        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }
}
