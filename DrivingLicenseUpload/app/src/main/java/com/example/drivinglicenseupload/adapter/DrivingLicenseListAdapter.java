package com.example.drivinglicenseupload.adapter;

import android.content.Context;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.drivinglicenseupload.R;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class DrivingLicenseListAdapter extends RecyclerView.Adapter {

    private final ArrayList<String> mIdCardList;
    public static class DrivingLicenseListViewHolder extends RecyclerView.ViewHolder {
        ImageView uploadedDrivingLicenseFrontImage;
        ImageView uploadedDrivingLicenseBackImage;

        public DrivingLicenseListViewHolder(@NonNull View itemView) {
            super(itemView);

            uploadedDrivingLicenseFrontImage = itemView.findViewById(R.id.uploaded_Driving_License_front_image);
            uploadedDrivingLicenseBackImage = itemView.findViewById(R.id.uploaded_Driving_License_back_image);

        }
    }
    public DrivingLicenseListAdapter(Context context, ArrayList<String> mIdCardList, LayoutInflater mInflater) {
        mInflater = LayoutInflater.from(context);
        this.mIdCardList = mIdCardList;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_driving_license_list,parent,false);
        return new DrivingLicenseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mIdCardList.size();
    }
}



