package com.example.drivinglicenseupload.util;

import android.content.Context;

import com.example.drivinglicenseupload.constant.PrefKeys;
import com.example.drivinglicenseupload.datadto.BluelinkModel;
import com.example.drivinglicenseupload.datadto.BluelinkSettings;


public class DBUtils {



    public static boolean setDrivingLicenseImage(Context context, String licenseImage, Boolean isFront) {
        PreferenceUtil pref = PreferenceUtil.getInstance(context);
        String selectedUserID = pref.getPreference(PrefKeys.KEY_SELECTED_USER_ID);
        if(isFront){
            return BluelinkModel.getInst(context).insertDrivingLicenseImageTable(selectedUserID,
                    BluelinkSettings.UserInfo.DRIVING_LICENSE_IMAGE_FRONT, licenseImage, true);
        }else{
            return BluelinkModel.getInst(context).insertDrivingLicenseImageTable(selectedUserID,
                    BluelinkSettings.UserInfo.DRIVING_LICENSE_IMAGE_BACK, licenseImage, false);
        }

    }
}
