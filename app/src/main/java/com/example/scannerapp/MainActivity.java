package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    RequestQueue requestQueue;
    private ZXingScannerView scannerView;
    private TextView txtResult;
    private static ArrayList<String> deliveryList = new ArrayList<String>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
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
    public void createHTTPPOSTRequest(final String parameter) {
        String url ="http://10.3.50.5:3010/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "getPackageByTrackingNumber",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        if (deliveryList.contains(response)) {
                            Log.i(TAG, "duplicate: " + response);
                        } else {
                            deliveryList.add(response);
                        }
                        Log.i(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("trackingnumber",parameter);
                return map;
            }
        };

        StringRequest putReportsRequest = new StringRequest(Request.Method.POST, url + "createReport",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("courierid", "1");
                params.put("trackingnumber", parameter);
                params.put("status","Delivery");
                return params;
            }
        };
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url + "changeStatusToDeliveryByTn",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("trackingnumber", parameter);
                return params;
            }
        };

        NetworkController.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        NetworkController.getInstance(getApplicationContext()).addToRequestQueue(putRequest);
        NetworkController.getInstance(getApplicationContext()).addToRequestQueue(putReportsRequest);
    }
    @Override
    public void handleResult(Result rawResult) {
        //TODO: Do this check server side
        /*if (deliveryList.contains(rawResult.getText())){
            Toast.makeText(MainActivity.this,"Package has already been scanned!",Toast.LENGTH_SHORT).show();
        } else {*/

        createHTTPPOSTRequest(rawResult.getText());



        scannerView.setResultHandler(MainActivity.this);
        scannerView.startCamera();
    }
    public void onClickDeliveryListBut(View v) {
        //Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(MainActivity.this, DeliveryActivity.class);
        startActivity(myIntent);
    }

    public static ArrayList<String> getDeliveryList() {
        return deliveryList;
    }
}
