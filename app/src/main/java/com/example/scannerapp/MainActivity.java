package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init scanner
        scannerView = findViewById(R.id.szxscan);

        //Request permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this,"You must accept this permissions",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }


    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }
    @Override
    public void handleResult(Result rawResult) {

        String trackingNumber = rawResult.getText();
        HashMap<String,String> parameters = new HashMap<>();
        HashMap<String,String> parameters2 = new HashMap<>();


        //Add the parameters needed for getPackageByTrackingNumber & changeStatusToDeliveryByTn
        parameters.put("trackingnumber",trackingNumber);

        //Add the parameters needed for createReport
        parameters2.put("courierid", "1");
        parameters2.put("trackingnumber", trackingNumber);
        parameters2.put("status","Delivery");

        NetworkController.getInstance(getApplicationContext()).createHTTPPostRequest(parameters,"getPackageByTrackingNumber");
        NetworkController.getInstance(getApplicationContext()).createHTTPPostRequest(parameters2,"createReport");
        NetworkController.getInstance(getApplicationContext()).createHTTPPutRequest(parameters,"changeStatusToDeliveryByTn");



        scannerView.setResultHandler(MainActivity.this);
        scannerView.startCamera();
    }
    public void onClickDeliveryListBut(View v) {
        Intent myIntent = new Intent(MainActivity.this, DeliveryActivity.class);
        startActivity(myIntent);
    }


}
