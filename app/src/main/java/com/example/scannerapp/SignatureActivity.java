package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.util.HashMap;

public class SignatureActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String trackingNumber;
    public SignaturePad signaturePad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackingNumber = getIntent().getExtras().getString("trackingnumber");
        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        setContentView(R.layout.activity_signature);

    }
    public void clickClear(View v) {

       signaturePad.clear();

    }
    public void clickSave(View v) {

                HashMap<String,String> parameters = new HashMap<>();
                HashMap<String,String> parameters2 = new HashMap<>();


                //Add the parameters needed for changeStatusToDeliveredByTn
                parameters.put("trackingnumber",trackingNumber);

                //Add the parameters needed for createReport
                parameters2.put("courierid", "1");
                parameters2.put("trackingnumber", trackingNumber);
                parameters2.put("status","Delivered");

                NetworkController.getInstance(getApplicationContext()).createHTTPPostRequest(parameters2,"createReport");
                NetworkController.getInstance(getApplicationContext()).createHTTPPutRequest(parameters,"changeStatusToDeliveredByTn");
                Toast.makeText(SignatureActivity.this,"Signature saved!",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(SignatureActivity.this, HomeActivity.class);
                SignatureActivity.this.startActivity(myIntent);
    }
}
