package com.example.mylicenseregistry.datadto;

import java.io.Serializable;

public class DrivingLicenseImage implements Serializable {

    private int mIndex;
    private String mFrontDrivingLicenseBitmap;
    private String mBackDrivingLicenseBitmap;

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public String getmFrontDrivingLicenseBitmap() {
        return mFrontDrivingLicenseBitmap;
    }

    public void setmFrontDrivingLicenseBitmap(String mFrontDrivingLicenseBitmap) {
        this.mFrontDrivingLicenseBitmap = mFrontDrivingLicenseBitmap;
    }

    public String getmBackDrivingLicenseBitmap() {
        return mBackDrivingLicenseBitmap;
    }

    public void setmBackDrivingLicenseBitmap(String mBackDrivingLicenseBitmap) {
        this.mBackDrivingLicenseBitmap = mBackDrivingLicenseBitmap;
    }

    public DrivingLicenseImage(int index, String frontDrivingLicenseBitmap, String backDrivingLicenseBitmap){
        mIndex = index;
        mFrontDrivingLicenseBitmap = frontDrivingLicenseBitmap;
        mBackDrivingLicenseBitmap = backDrivingLicenseBitmap;
    }

}
