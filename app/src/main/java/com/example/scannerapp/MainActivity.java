package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
        txtResult = findViewById(R.id.txt_result);

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
        String url ="http://10.3.50.5:3010/getPackageByTrackingNumber";
        String url2 = "http://10.3.50.5:3010/changeStatusToDeliveryByTn";
        String url3 = "http://10.3.50.5:3010/createReport";
        //TODO: Refactor  this
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

        StringRequest putReportsRequest = new StringRequest(Request.Method.POST, url3,
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
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url2,
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

        requestQueue.add(stringRequest);
        requestQueue.add(putRequest);
        requestQueue.add(putReportsRequest);
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


    private void processRawResults(String text) {
        //Package shipment = new Package();
        String[] shipmentInfo = text.split(",");
        Toast.makeText(MainActivity.this,shipmentInfo[0] + ": From " + shipmentInfo[1] + " to " + shipmentInfo[2],Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<String> getDeliveryList() {
        return deliveryList;
    }
}
