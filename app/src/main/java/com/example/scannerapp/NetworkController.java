package com.example.scannerapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkController {
    private static NetworkController mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

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
}
