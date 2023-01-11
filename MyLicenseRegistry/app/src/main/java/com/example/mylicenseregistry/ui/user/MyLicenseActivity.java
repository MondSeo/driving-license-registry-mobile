package com.example.mylicenseregistry.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;

public class MyLicenseActivity extends BaseActivity_CommonGNB {

    private RelativeLayout rel_MyLicenseActivity_DrivingLicenseCertificate, rel_MyLicenseActivity_VehicleRegistrationCertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_license);
        initLayout();
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    public void initLayout() {
        setTitleText(getString(R.string.MyLicenseActivity_Title));
        rel_MyLicenseActivity_DrivingLicenseCertificate = findViewById(R.id.rel_MyLicenseActivity_Driving_License_Certificate);
        rel_MyLicenseActivity_VehicleRegistrationCertificate = findViewById(R.id.rel_MyLicenseActivity_Vehicle_Registration_Certificate);

        View.OnClickListener menuClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.rel_MyLicenseActivity_Driving_License_Certificate:
                        startActivity(DrivingLicenseListActivity.class);
                        break;
                    case R.id.rel_MyLicenseActivity_Vehicle_Registration_Certificate:
                        startActivity(VehicleRegistrationListActivity.class);
                        break;
                }
            }
        };

        rel_MyLicenseActivity_DrivingLicenseCertificate.setOnClickListener(menuClicked);
        rel_MyLicenseActivity_VehicleRegistrationCertificate.setOnClickListener(menuClicked);
    }

    @Override
    public void initProcess() {

    }

}