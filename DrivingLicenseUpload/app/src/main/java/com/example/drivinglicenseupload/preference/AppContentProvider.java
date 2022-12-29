package com.example.drivinglicenseupload.preference;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.drivinglicenseupload.BuildConfig;
import com.example.drivinglicenseupload.util.PreferenceUtil;


public class AppContentProvider extends ContentProvider {

    private static final int CODE_GET_LAMP_BODY = 0x01;
    private static final int CODE_SET_LAMP_BODY = 0x02;
    private static final String AUTH = BuildConfig.APPLICATION_ID + ".multi_process_save_preference";
    public static final Uri CONTENTS_URI_GET = Uri.parse("content://" + AUTH + "/" + "get");
    public static final Uri CONTENTS_URI_SET = Uri.parse("content://" + AUTH + "/" + "set");
    public static final String CONTENT_KEY = "content_key";
    public static final String CONTENT_VALUE = "content_value";

    private static final UriMatcher mUriMather;
    static {
        mUriMather = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMather.addURI(AUTH, "get", CODE_GET_LAMP_BODY);
        mUriMather.addURI(AUTH, "set", CODE_SET_LAMP_BODY);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if(contentValues != null && contentValues.containsKey(CONTENT_KEY)) {
            String prefKey = contentValues.getAsString(CONTENT_KEY);
            if(mUriMather.match(uri) == CODE_GET_LAMP_BODY) {
                return Uri.parse(CONTENTS_URI_GET + "/" + getValue(prefKey));
            }
        }

        return uri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if(contentValues != null && contentValues.containsKey(CONTENT_KEY)) {
            String prefKey = contentValues.getAsString(CONTENT_KEY);
            String prefValue = contentValues.getAsString(CONTENT_VALUE);
            if(mUriMather.match(uri) == CODE_SET_LAMP_BODY) {
                setValue(prefKey, prefValue);
            }
        }
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private void setValue(String key, String value) {
        PreferenceUtil.getInstance(getContext()).setPreference(key, value);
    }

    private String getValue(String key) {
        return PreferenceUtil.getInstance(getContext()).getPreference(key, "");
    }
}
