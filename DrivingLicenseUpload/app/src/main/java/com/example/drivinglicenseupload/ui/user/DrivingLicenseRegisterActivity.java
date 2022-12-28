package com.example.drivinglicenseupload.ui.user;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.constant.AppConfig;
import com.example.drivinglicenseupload.ui.BaseActivity_CommonGNB;
import com.example.drivinglicenseupload.ui.custom.CustomDialog;
import com.example.drivinglicenseupload.util.Util;

public class DrivingLicenseRegisterActivity extends BaseActivity_CommonGNB {

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
        initLayout();
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

    private void initLayout(){
        setTitleText(getString(R.string.DrivingLicenseCertificate_Text));
        RelativeLayout rel_DrivingLicenseRegisterActivity_FrontImageAdd = findViewById(R.id.rel_Registry_Driving_License_Front);
        RelativeLayout rel_DrivingLicenseRegisterActivity_BackImageAdd = findViewById(R.id.rel_Registry_Driving_License_Back);
//        RecyclerView mDrivingLicenseListRecyclerView = findViewById(R.id.driving_License_Register_RecyclerView);

        rel_DrivingLicenseRegisterActivity_FrontImageAdd.setOnClickListener(clickListener);
        rel_DrivingLicenseRegisterActivity_BackImageAdd.setOnClickListener(clickListener);

    }

    private String contentText(boolean isFront){
        if(isFront){
            return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Front_Text);
        }
        return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Back_Text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}