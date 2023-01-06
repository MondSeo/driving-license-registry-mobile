package com.example.mylicenseregistry.ui.user;

import android.os.Bundle;
import android.view.View;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;

public class MyLicenseListActivity extends BaseActivity_CommonGNB {

    private View.OnClickListener menuClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lin_MyLicenseListActivity_Driving_License_Certificate:
                    startActivity(DrivingLicenseListActivity.class);
                    break;
                case R.id.lin_MYLicenseListActivity_Vehicle_Registration_Certificate:
//                    startActivity();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_license_list);


    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    public void initLayout() {
        setTitleText(getString(R.string.My_License));

    }

    @Override
    public void initProcess() {

    }


}