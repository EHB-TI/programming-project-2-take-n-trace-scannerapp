package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button scan_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scan_but = (Button)findViewById(R.id.scan_button);

    }

    public void startScan(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }
    public void callDispatch(View v)
    {
        String phone = "+32488389575"; //Dispatch phone number here
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
}
