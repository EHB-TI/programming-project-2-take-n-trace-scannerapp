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
    Button delivery_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        scan_but = (Button)findViewById(R.id.scan_button);
        delivery_but = (Button)findViewById(R.id.delivery_button);

    }

    public void startScan(View v)
    {
        //Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(HomeActivity.this, MainActivity.class);
        HomeActivity.this.startActivity(myIntent);
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
}
