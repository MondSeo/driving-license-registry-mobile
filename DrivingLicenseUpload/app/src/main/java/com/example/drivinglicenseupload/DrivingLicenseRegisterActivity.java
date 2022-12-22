package com.example.drivinglicenseupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.example.idcarduploadapp.R;

public class DrivingLicenseRegisterActivity extends AppCompatActivity {

    public static final int PERMISSION_CHECK_CAMERA_ID = 10000;
    public static final int PERMISSION_CHECK_EXTERNAL_STORAGE_ID = 10001;

    public static final String[] CHECK_CAMERA_PERMISSIONS = {
            Manifest.permission.CAMERA
    };

    public static final String[] CHECK_EXTERNAL_STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_register);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}