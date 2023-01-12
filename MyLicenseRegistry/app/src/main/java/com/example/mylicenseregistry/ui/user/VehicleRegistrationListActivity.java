package com.example.mylicenseregistry.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.adapter.VehicleRegistrationListAdapter;
import com.example.mylicenseregistry.datadto.BluelinkModel;
import com.example.mylicenseregistry.datadto.BluelinkSettings;
import com.example.mylicenseregistry.datadto.VehicleRegistrationImage;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.widget.ContentButton;

import java.io.File;
import java.util.ArrayList;

public class VehicleRegistrationListActivity extends BaseActivity_CommonGNB {

    VehicleRegistrationListAdapter mAdapter;
    RelativeLayout rel_VehicleRegistrationListActivity_EmptyList;
    ContentButton btn_VehicleRegistrationListActivity_ListAdd;
    RecyclerView rcl_VehicleRegistrationListActivity;

    private ArrayList<VehicleRegistrationImage> mDataList = new ArrayList<VehicleRegistrationImage>();

    public static final int REQUEST_CODE_IMAGE = 30001;
    public static final int REQUEST_CODE_IMAGE_DETAIL = 30002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration_list);
        initLayout();
        setVehicleRegistrationList();
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE){
            if (resultCode == RESULT_OK) {
                setVehicleRegistrationList();
            }
        }
        if(requestCode == REQUEST_CODE_IMAGE_DETAIL){
            if (resultCode == RESULT_OK){
                int position = data.getIntExtra("Position",0);
                deleteVehicleRegistrationImage(position);
                mDataList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        }

    }


    @Override
    public void initLayout() {
        setTitleText(getString(R.string.VehicleRegistrationCertificate_Text));
        btn_VehicleRegistrationListActivity_ListAdd = findViewById(R.id.btn_VehicleRegistrationListActivity_ListAdd);
        rel_VehicleRegistrationListActivity_EmptyList = findViewById(R.id.rel_VehicleRegistrationListActivity_EmptyList);
        rcl_VehicleRegistrationListActivity = findViewById(R.id.rcl_VehicleRegistrationListActivity);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(VehicleRegistrationRegisterActivity.class, REQUEST_CODE_IMAGE);
            }
        };


        btn_VehicleRegistrationListActivity_ListAdd.setOnClickListener(clickListener);
        rel_VehicleRegistrationListActivity_EmptyList.setOnClickListener(clickListener);
    }

    @Override
    public void initProcess() {

    }

    private void setVehicleRegistrationList() {
        getVehicleRegistrationList();
        mAdapter = new VehicleRegistrationListAdapter(mContext, mDataList);

        rcl_VehicleRegistrationListActivity.setLayoutManager(new LinearLayoutManager(mContext));
        rcl_VehicleRegistrationListActivity.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new VehicleRegistrationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, VehicleRegistrationDetailActivity.class);
                intent.putExtra("Position", position);
                startActivityForResult(intent,REQUEST_CODE_IMAGE_DETAIL);
            }
        });
        if (mDataList == null || mDataList.isEmpty()) {
            rel_VehicleRegistrationListActivity_EmptyList.setVisibility(View.VISIBLE);
            rcl_VehicleRegistrationListActivity.setVisibility(View.GONE);
            btn_VehicleRegistrationListActivity_ListAdd.setVisibility(View.GONE);
        } else {
            rel_VehicleRegistrationListActivity_EmptyList.setVisibility(View.GONE);
            rcl_VehicleRegistrationListActivity.setVisibility(View.VISIBLE);
            btn_VehicleRegistrationListActivity_ListAdd.setVisibility(View.VISIBLE);
        }
    }

    private void getVehicleRegistrationList() {
        mDataList = new ArrayList<>();
        ArrayList<Bundle> dbArrayList = null;

        dbArrayList = BluelinkModel.getInst(mContext).selectVehicleRegistrationImageTable();

        if (dbArrayList != null) {
            for (Bundle item : dbArrayList) {
                mDataList.add(new VehicleRegistrationImage(item.getInt(BluelinkSettings.VehicleRegistrationImage.INDEX),
                        item.getString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT),
                        item.getString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK)));
            }
        }
    }



    private boolean deleteTempFile(File path) {
        if (!path.isDirectory()) {
            return false;
        }
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteTempFile(file);
            } else {
                file.delete();
            }
        }
        return path.delete();
    }

    private void deleteVehicleRegistrationImage(int position)
    {
        VehicleRegistrationImage deleteImage = mDataList.get(position);
        int index = deleteImage.getIndex();
        BluelinkModel.getInst(mContext).deleteVehicleRegistrationImage(index);
    }
}