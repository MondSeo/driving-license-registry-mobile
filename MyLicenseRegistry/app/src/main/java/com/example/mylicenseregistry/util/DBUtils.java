package com.example.mylicenseregistry.util;

import android.content.Context;

import com.example.mylicenseregistry.datadto.BluelinkModel;


public class DBUtils {


    public static boolean setDrivingLicenseImage(Context context, String frontImage, String backImage) {
//        PreferenceUtil pref = PreferenceUtil.getInstance(context);
//        String selectedDrivingLicenseIndex = pref.getPreference(PrefKeys.KEY_DRIVING_LICENSE_INDEX);
//
        return BluelinkModel.getInst(context).insertDrivingLicenseImageTable(frontImage, backImage);

    }



}
