package com.example.mylicenseregistry.preference;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.util.List;

public class ContentResolverUtil {
    private static ContentResolverUtil mInstance;

    private ContentResolverUtil() {
    }

    public static ContentResolverUtil getInstance() {
        if (null == mInstance) {
            mInstance = new ContentResolverUtil();
        }
        return mInstance;
    }

    public void setContentValue(Context context, String key, String value) {
        ContentResolver cr = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(AppContentProvider.CONTENT_KEY, key);
        cv.put(AppContentProvider.CONTENT_VALUE, value);
        cr.update(AppContentProvider.CONTENTS_URI_SET, cv, null, null);
    }

    public String getContentValue(Context context, String key) {
        ContentResolver cr = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(AppContentProvider.CONTENT_KEY, key);
        Uri uri = cr.insert(AppContentProvider.CONTENTS_URI_GET, cv);
        List<String> values = uri.getPathSegments();

        String value = "";
        if(values.size() > 1) {
            value = values.get(1);
        }

        return value;
    }
}
