package com.example.drivinglicenseupload.datadto;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.drivinglicenseupload.util.LogUtils;

import java.util.logging.Logger;

public class BluelinkModel {

    private SQLiteDatabase mDB;
    private static BluelinkModel mBluelinkModel;

    public static synchronized BluelinkModel getInst(Context context) {
        if (mBluelinkModel == null) {
            if (context != null) {
                mBluelinkModel = new BluelinkModel(context);
            } else {
                return null;
            }
        }
        return mBluelinkModel;
    }

    public BluelinkModel(Context context) {
        SQLiteOpenHelper mOpenHelper = new BluelinkDatabaseHelper(context);
        mDB = mOpenHelper.getWritableDatabase();
    }

//    public boolean updateDrivingLicenseImageTable(String columnName, String updateValue, Boolean isFront) {
//        if (TextUtils.isEmpty(updateValue))
//            updateValue = "";
//
//        String sql;
//        Cursor cursor = null;
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("UPDATE " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE)
//                .append(" SET " + columnName + "='" + updateValue + "'")
//                .append(" WHERE " + BluelinkSettings.DrivingLicenseImage.INDEX + "'" + "'");
//
//        sb.append("; ");
//
//        sql = sb.toString();
//
//
//        try {
////            mDB.execSQL(sql);
//            cursor = mDB.rawQuery(sql, null);
//            if (cursor != null && cursor.getCount() > 0) {
//                logcat("업데이트 성공\n" + sql);
//            } else {
//                logcat("업데이트 실패 - 사용자 추가 \n" + sql);
//                insertDrivingLicenseImageTable(updateValue, isFront);
//            }
//
//        } catch (SQLException e) {
//
//            return false;
//        } finally {
//            if (null != cursor) {
//                cursor.close();
//            }
//        }
//        return true;
//    }

    //Mond 추가
    public boolean insertDrivingLicenseImageTable(String insertValue1, String insertValue2) {

        String sql = null;
        try {
            sql = "INSERT INTO " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE + "(" + BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT + ", "
                    + BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK + ")" + " values (" + insertValue1 + ", " + insertValue2 + ")";

        } catch (Exception e) {
            return false;
        }
        try {
            mDB.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //mond 추가
    public String selectDrivingLicenseImageTable(String userId, String columnName) {
        String result = null;

        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT");
            if (!TextUtils.isEmpty(columnName)) sb.append(" " + columnName);
            sb.append(" FROM " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE)
                    .append(" WHERE")
                    .append(" userId = '" + userId + "'");
            sb.append(";");

//            Logger.v(getClass().getSimpleName(), sb.toString());

            cursor = mDB.rawQuery(sb.toString(), null);
            if (cursor.getCount() > 0) {
                cursor.moveToNext();

                result = cursor.getString(cursor.getColumnIndex(columnName));

            }

        } catch (Exception e) {
            e.printStackTrace();
//            errorLogcat("selectUserPhotoTable 실패",e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    // UserInfo
    public boolean insertUserPhoto(String userID, String updateValue) {

        userID = changeStringForDB(userID);
//        pinNumber = changeStringForDB(pinNumber);
        String sql = null;
        try {
            sql = "INSERT OR REPLACE INTO " + BluelinkDatabaseHelper.TABLE_USER_PHOTO + " (userId, " + BluelinkSettings.UserInfo.USER_PHOTO + ") values ('" + userID + "', '" + updateValue + "')";
        } catch (Exception e) {
            return false;
        }
        try {
            mDB.execSQL(sql);

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private void logcat(String msg) {
        LogUtils.logcat(getClass().getSimpleName(), msg);
    }

    private String changeStringForDB(String orgin) {

        String temp = null;
        if (orgin != null) {
            temp = orgin.replaceAll("'", "''");
        } else {
            temp = orgin;
        }

        return temp;
    }
}
