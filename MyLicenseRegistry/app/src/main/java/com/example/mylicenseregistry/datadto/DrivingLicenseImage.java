package com.example.mylicenseregistry.datadto;

import java.io.Serializable;

public class DrivingLicenseImage implements Serializable {

    private int mIndex;
    private String mFrontDrivingLicenseBitmap;
    private String mBackDrivingLicenseBitmap;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public String getFrontDrivingLicenseBitmap() {
        return mFrontDrivingLicenseBitmap;
    }

    public void setFrontDrivingLicenseBitmap(String mFrontDrivingLicenseBitmap) {
        this.mFrontDrivingLicenseBitmap = mFrontDrivingLicenseBitmap;
    }

    public String getBackDrivingLicenseBitmap() {
        return mBackDrivingLicenseBitmap;
    }

    public void setBackDrivingLicenseBitmap(String mBackDrivingLicenseBitmap) {
        this.mBackDrivingLicenseBitmap = mBackDrivingLicenseBitmap;
    }

    public DrivingLicenseImage(int index, String frontDrivingLicenseBitmap, String backDrivingLicenseBitmap){
        mIndex = index;
        mFrontDrivingLicenseBitmap = frontDrivingLicenseBitmap;
        mBackDrivingLicenseBitmap = backDrivingLicenseBitmap;
    }

}
