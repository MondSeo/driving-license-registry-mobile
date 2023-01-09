package com.example.mylicenseregistry.ui.user;


import android.os.Bundle;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;

public class DrivingLicenseDetailActivity extends BaseActivity_CommonGNB {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_detail);
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    public void initLayout() {
        setTitleText(getString( R.string.DrivingLicenseDetailActivity_Title));
    }

    @Override
    public void initProcess() {

    }

}