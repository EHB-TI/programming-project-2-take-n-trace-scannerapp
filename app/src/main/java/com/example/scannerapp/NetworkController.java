package com.example.scannerapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class NetworkController {
    private static NetworkController mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private String url ="http://10.3.50.5:3010/";
    private static ArrayList<String> deliveryList = new ArrayList<String>();

    private NetworkController(Context context) {
        mCtx = context.getApplicationContext();
        mRequestQueue = getRequestQueue();

    }
    public static synchronized NetworkController getInstance(Context context) {
        // If instance is not available, create it. If available, reuse and return the object.
        if (mInstance == null) {
            mInstance = new NetworkController(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key. It should not be activity context,
            // or else RequestQueue won’t last for the lifetime of your app
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
    public void createHTTPPostRequest(final HashMap<String,String> parameters, final String apiCall) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + apiCall,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        if(apiCall == "getPackageByTrackingNumber") {
                            if (deliveryList.contains(response)) {
                                Toast.makeText(mCtx,"Tracking number has already been scanned!",Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "duplicate: " + parameters.get("trackingnumber"));
                            } else {
                                deliveryList.add(response);
                            }
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
                return parameters;
            }
        };


        mRequestQueue.add(stringRequest);
    }

    public void createHTTPPutRequest(final HashMap<String,String> parameters, String apiCall) {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url + apiCall,
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
                return parameters;
            }
        };
        mRequestQueue.add(putRequest);
    }
    public static ArrayList<String> getDeliveryList() {
        return deliveryList;
    }
}
