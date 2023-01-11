package com.example.mylicenseregistry.datadto;

import java.io.Serializable;

public class VehicleRegistrationImage implements Serializable {
    private int mIndex;
    private String mFrontVehicleRegistrationBitmap;
    private String mBackVehicleRegistrationBitmap;

  

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public String getFrontVehicleRegistrationBitmap() {
        return mFrontVehicleRegistrationBitmap;
    }

    public void setFrontVehicleRegistrationBitmap(String mFrontVehicleRegistrationBitmap) {
        this.mFrontVehicleRegistrationBitmap = mFrontVehicleRegistrationBitmap;
    }

    public String getBackVehicleRegistrationBitmap() {
        return mBackVehicleRegistrationBitmap;
    }

    public void setBackVehicleRegistrationBitmap(String mBackVehicleRegistrationBitmap) {
        this.mBackVehicleRegistrationBitmap = mBackVehicleRegistrationBitmap;
    }

    public VehicleRegistrationImage(int index, String frontVehicleRegistrationBitmap, String backVehicleRegistrationBitmap){
        mIndex = index;
        mFrontVehicleRegistrationBitmap = frontVehicleRegistrationBitmap;
        mBackVehicleRegistrationBitmap = backVehicleRegistrationBitmap;
    }
    
  
}
