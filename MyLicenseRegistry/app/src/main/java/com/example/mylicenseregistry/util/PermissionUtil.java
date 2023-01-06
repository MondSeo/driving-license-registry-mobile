package com.example.mylicenseregistry.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mylicenseregistry.constant.PrefKeys;

public class PermissionUtil {
    public static final String TAG = "PermissionUtil";

    public PermissionUtil(){}

    public static final int STORAGE_PERMISSION = 0;
    public static final int CAMERA_PERMISSION = 1;


    public static boolean isHuaweiDevice(){
        String manufacture = Build.MANUFACTURER;
        if(manufacture.equalsIgnoreCase("HUWEI")){
            return true;
        }
        return false;
    }

    public static boolean isStoragePermissionDeny(Context mContext) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public static boolean isStorageFirstAskPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE) || !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return false;
    }
    public static boolean huaweiShouldShowRequestPermission(Context mContext, int permission) {
        if (isHuaweiDevice()) {
            switch (permission) {
                case STORAGE_PERMISSION:
                    if ("true".equals(PreferenceUtil.getInstance(mContext).getPreference(PrefKeys.KEY_HUAWEI_PERMISSION_STORAGE_CHECK, "true"))) {
                        PreferenceUtil.getInstance(mContext).setPreference(PrefKeys.KEY_HUAWEI_PERMISSION_STORAGE_CHECK, "false");
                        return false;
                    } else {
                        return true;
                    }
                case CAMERA_PERMISSION:
                    if ("true".equals(PreferenceUtil.getInstance(mContext).getPreference(PrefKeys.KEY_HUAWEI_PERMISSION_CAMERA_CHECK, "true"))) {
                        PreferenceUtil.getInstance(mContext).setPreference(PrefKeys.KEY_HUAWEI_PERMISSION_CAMERA_CHECK, "false");
                        return false;
                    } else {
                        return true;
                    }
            }
        }
        return true;
    }
}
