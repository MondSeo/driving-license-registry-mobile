package com.example.drivinglicenseupload.ui.user;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.ui.custom.CustomDialog;
import com.example.drivinglicenseupload.util.Util;

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


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rel_Registry_Driving_License_Front:
                    popupDrivingLicenseSelect(true);
                    break;
                case R.id.rel_Registry_Driving_License_Back:
                    popupDrivingLicenseSelect(false);
                    break;
            }
            Log.d(TAG,"popupDrivingLicenseSelect");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_register);
        RelativeLayout mDrivingLicenseRegisterFrontButton = findViewById(R.id.rel_Registry_Driving_License_Front);
        RelativeLayout mDrivingLicenseRegisterBackButton = findViewById(R.id.rel_Registry_Driving_License_Back);

        mDrivingLicenseRegisterFrontButton.setOnClickListener(clickListener);
        mDrivingLicenseRegisterBackButton.setOnClickListener(clickListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void popupDrivingLicenseSelect(Boolean isFront) {
        Util.customViewDialog(this, getString(R.string.DrivingLicenseCustomDialog_Title_Text), contentText(isFront), R.layout.customdialog_drivinglicense_select, new CustomDialog.OnCustomInitialize() {
            CustomDialog dialog;
            @Override
            public void onInitialize(View contentView, CustomDialog dialog) {
                this.dialog = dialog;
                contentView.findViewById(R.id.btn_driving_license_snap_shot).setOnClickListener(popupListener);
                contentView.findViewById(R.id.btn_driving_license_find_gallery).setOnClickListener(popupListener);
            }
            private View.OnClickListener popupListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    switch (v.getId())
                    {
                        case R.id.btn_driving_license_snap_shot:
                        case R.id.btn_driving_license_find_gallery:
                            break;
                    }
                }
            };
        },getString(R.string.Common_Close),null,false);
    }

    private String contentText(boolean isFront){
        if(isFront){
            return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Front_Text);
        }
        return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Back_Text);
    }
}