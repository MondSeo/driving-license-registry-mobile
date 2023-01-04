package com.example.drivinglicenseupload.util;

import android.content.Context;

import com.example.drivinglicenseupload.constant.PrefKeys;
import com.example.drivinglicenseupload.datadto.BluelinkModel;
import com.example.drivinglicenseupload.datadto.BluelinkSettings;


public class DBUtils {


    public static boolean setDrivingLicenseImage(Context context, String frontImage, String backImage) {
//        PreferenceUtil pref = PreferenceUtil.getInstance(context);
//        String selectedDrivingLicenseIndex = pref.getPreference(PrefKeys.KEY_DRIVING_LICENSE_INDEX);
//
        return BluelinkModel.getInst(context).insertDrivingLicenseImageTable(frontImage, backImage);

    }

    public static String getDrivingLicenseImage(Context context) {
        PreferenceUtil pref = PreferenceUtil.getInstance(context);
        String selectedUserID = pref.getPreference(PrefKeys.KEY_SELECTED_USER_ID);
        String image = BluelinkModel.getInst(context).selectDrivingLicenseImageTable(selectedUserID, BluelinkSettings.UserInfo.USER_PHOTO);
        return image;
    }
}
