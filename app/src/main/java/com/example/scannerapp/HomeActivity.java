package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void startScan(View v)
    {
        Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
    public void callDispatch(View v)
    {
        String phone = "+32488389575"; //Dispatch phone number here
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public void onClickDelivery(View v)
    {
        Intent myIntent = new Intent(HomeActivity.this, DeliveryActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }
    public void onClickPickUp(View v) {
        Intent myIntent = new Intent(HomeActivity.this, PickUpActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }
    public void onClicky(View v)
    {
        Intent myIntent = new Intent(HomeActivity.this, DeliveredActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }

}
