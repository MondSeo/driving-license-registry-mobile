package com.example.drivinglicenseupload.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.drivinglicenseupload.constant.PrefKeys;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PreferenceUtil {
    private final static String FILE_NAME = "appPreference";
    private final static String CryptKeys[] = {
            PrefKeys.KEY_CCSP_ACCESS_TOKEN,
            PrefKeys.KEY_CCSP_SID,
            PrefKeys.KEY_CCSP_UID,
            PrefKeys.KEY_CCSP_CONTROL_TOKEN,
            PrefKeys.KEY_CCSP_REFRESH_TOKEN};
    private final static Set<String> CryptKeysSet = new HashSet<>(Arrays.asList(CryptKeys));
    private static PreferenceUtil mInstance;

    private SharedPreferences pref;

    private PreferenceUtil(Context context) {
        pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
    }

    public static PreferenceUtil getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new PreferenceUtil(context);
        }
        return mInstance;
    }

    public synchronized void setPreference(String key, String value) {
        try {
            SharedPreferences.Editor editor = pref.edit();
            String result;
            result = value;

            editor.putString(key, result);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void setPreference(String key, boolean value) {
        try {
            SharedPreferences.Editor editor = pref.edit();
            String result;

            result = String.valueOf(value);

            editor.putString(key, result);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public synchronized String getPreference(String key) {
        try {
            String result;
            result = pref.getString(key, "");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public synchronized String getPreference(String key, String defValue) {
        try {
            String result;
            result = pref.getString(key, defValue);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public synchronized void removePreference(String key) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }


}
