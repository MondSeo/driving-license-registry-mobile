package com.example.drivinglicenseupload.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.adapter.DrivingLicenseListAdapter;
import com.example.drivinglicenseupload.datadto.BluelinkSettings.UserInfo;
import com.example.drivinglicenseupload.ui.BaseActivity_CommonGNB;
import com.example.drivinglicenseupload.widget.ContentButton;

import java.util.ArrayList;


public class DrivingLicenseListActivity extends BaseActivity_CommonGNB {

    ArrayList<UserInfo> mUserInfo;
    DrivingLicenseListAdapter mAdapter;
    RelativeLayout rel_DrivingLicenseListActivity_EmptyList;
    ContentButton btn_DrivingLicenseListActivity_ListAdd;
    RecyclerView rcl_DrivingLicenseListActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_list);
        initLayout();
    }

    private void initLayout(){
//        mAdapter = new DrivingLicenseListAdapter(mContext, mUserInfo)
        setTitleText(getString(R.string.DrivingLicenseCertificate_Text));
        btn_DrivingLicenseListActivity_ListAdd = findViewById(R.id.btn_DrivingLicenseListActivity_ListAdd);
        rel_DrivingLicenseListActivity_EmptyList = findViewById(R.id.rel_DrivingLicenseListActivity_EmptyList);
        rcl_DrivingLicenseListActivity = findViewById(R.id.rcl_DrivingLicenseListActivity);

        
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