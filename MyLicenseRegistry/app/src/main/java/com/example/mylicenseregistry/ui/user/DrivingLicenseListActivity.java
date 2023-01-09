package com.example.mylicenseregistry.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.adapter.DrivingLicenseListAdapter;
import com.example.mylicenseregistry.datadto.BluelinkModel;
import com.example.mylicenseregistry.datadto.BluelinkSettings;
import com.example.mylicenseregistry.datadto.DrivingLicenseImage;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.widget.ContentButton;

import java.util.ArrayList;


public class DrivingLicenseListActivity extends BaseActivity_CommonGNB {

    DrivingLicenseListAdapter mAdapter;
    RelativeLayout rel_DrivingLicenseListActivity_EmptyList;
    ContentButton btn_DrivingLicenseListActivity_ListAdd;
    RecyclerView rcl_DrivingLicenseListActivity;
    DrivingLicenseImage drivingLicenseImage;

    private ArrayList<DrivingLicenseImage> mDataList = new ArrayList<DrivingLicenseImage>();

    public static final int REQUEST_CODE_IMAGE = 30001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_list);
        initLayout();
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mAdapter.notifyDataSetChanged();
        setDrivingLicenseList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            mAdapter.addImageItem();
            setDrivingLicenseList();
        }
    }

    @Override
    public void initLayout() {
        //        mAdapter = new DrivingLicenseListAdapter(mContext, mUserInfo)
        setTitleText(getString(R.string.DrivingLicenseCertificate_Text));
        btn_DrivingLicenseListActivity_ListAdd = findViewById(R.id.btn_DrivingLicenseListActivity_ListAdd);
        rel_DrivingLicenseListActivity_EmptyList = findViewById(R.id.rel_DrivingLicenseListActivity_EmptyList);
        rcl_DrivingLicenseListActivity = findViewById(R.id.rcl_DrivingLicenseListActivity);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(DrivingLicenseRegisterActivity.class, REQUEST_CODE_IMAGE);
            }
        };


        btn_DrivingLicenseListActivity_ListAdd.setOnClickListener(clickListener);
        rel_DrivingLicenseListActivity_EmptyList.setOnClickListener(clickListener);
    }

    @Override
    public void initProcess() {

    }

    private void setDrivingLicenseList() {
        getDrivingLicenseList();
        mAdapter = new DrivingLicenseListAdapter(mContext, mDataList);

        rcl_DrivingLicenseListActivity.setLayoutManager(new LinearLayoutManager(mContext));
        rcl_DrivingLicenseListActivity.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DrivingLicenseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, DrivingLicenseDetailActivity.class);
                intent.putExtra("FrontImage",mDataList.get(position).getmFrontDrivingLicenseBitmap());
                intent.putExtra("BackImage",mDataList.get(position).getmBackDrivingLicenseBitmap());
                startActivity(intent);
            }
        });
        if (mDataList == null || mDataList.isEmpty()) {
            rel_DrivingLicenseListActivity_EmptyList.setVisibility(View.VISIBLE);
            rcl_DrivingLicenseListActivity.setVisibility(View.GONE);
            btn_DrivingLicenseListActivity_ListAdd.setVisibility(View.GONE);
        } else {
            rel_DrivingLicenseListActivity_EmptyList.setVisibility(View.GONE);
            rcl_DrivingLicenseListActivity.setVisibility(View.VISIBLE);
            btn_DrivingLicenseListActivity_ListAdd.setVisibility(View.VISIBLE);
        }
    }

    private void getDrivingLicenseList() {
        mDataList = new ArrayList<>();
        ArrayList<Bundle> dbArrayList = null;

        dbArrayList = BluelinkModel.getInst(mContext).selectDrivingLicenseImageTable();

        if (dbArrayList != null) {
            for (Bundle item : dbArrayList) {
                mDataList.add(new DrivingLicenseImage(item.getInt(BluelinkSettings.DrivingLicenseImage.INDEX),
                        item.getString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT),
                        item.getString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK)));
            }
        }
    }
}