package com.example.drivinglicenseupload.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.widget.ContentButton;


public class DrivingLicenseListActivity extends AppCompatActivity {

    public ContentButton mDrivingLicenseAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_list);
        mDrivingLicenseAddButton = findViewById(R.id.btn_driving_license_Add);
        Intent intent = new Intent(this, DrivingLicenseRegisterActivity.class);

        mDrivingLicenseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }


}