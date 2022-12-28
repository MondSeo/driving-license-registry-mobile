package com.example.drivinglicenseupload.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.ui.BaseActivity_CommonGNB;
import com.example.drivinglicenseupload.widget.ContentButton;


public class DrivingLicenseListActivity extends BaseActivity_CommonGNB {

    RelativeLayout rel_DrivingLicenseListActivity_EmptyList;
    ContentButton btn_DrivingLicenseListActivity_ListAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_list);
        initLayout();
    }

    private void initLayout(){
        setTitleText(getString(R.string.DrivingLicenseCertificate_Text));
        btn_DrivingLicenseListActivity_ListAdd = findViewById(R.id.btn_DrivingLicenseListActivity_ListAdd);
        rel_DrivingLicenseListActivity_EmptyList = findViewById(R.id.rel_DrivingLicenseListActivity_EmptyList);

        Intent intent = new Intent(this, DrivingLicenseRegisterActivity.class);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        };

        btn_DrivingLicenseListActivity_ListAdd.setOnClickListener(clickListener);
        rel_DrivingLicenseListActivity_EmptyList.setOnClickListener(clickListener);
    }

}