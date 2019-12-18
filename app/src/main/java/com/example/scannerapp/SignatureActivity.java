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
    private Button mClearButton;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackingNumber = getIntent().getExtras().getString("trackingnumber");
        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mClearButton = findViewById(R.id.clear_button);
        mSaveButton = findViewById(R.id.save_button);

        setContentView(R.layout.activity_signature);
        doSome();

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

    public void doSome() {
        /*
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });


         */
    }



    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
