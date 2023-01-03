package com.example.drivinglicenseupload.datadto;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.logging.Logger;

public class BluelinkModel {

    private SQLiteDatabase mDB;
    private static BluelinkModel mBluelinkModel;

    public static synchronized BluelinkModel getInst(Context context)
    {
        if (mBluelinkModel == null)
        {
            if (context != null)
            {
                mBluelinkModel = new BluelinkModel(context);
            }
            else
            {
                return null;
            }
        }
        return mBluelinkModel;
    }

    public BluelinkModel(Context context)
    {
        SQLiteOpenHelper mOpenHelper = new BluelinkDatabaseHelper(context);
        mDB = mOpenHelper.getWritableDatabase();
    }

    //Mond 추가
    public boolean insertDrivingLicenseImageTable(String UserID, String columnName, String updateValue, Boolean isFront) {

        UserID = changeStringForDB(UserID);
//        pinNumber = changeStringForDB(pinNumber);
        String sql = null;
        try
        {
            if(isFront){
                sql = "INSERT OR REPLACE INTO "+BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE +" (UserID, "+ BluelinkSettings.UserInfo.DRIVING_LICENSE_IMAGE_FRONT+") values ('"+UserID+"', '"+updateValue+"')";
            }else{
                sql = "INSERT OR REPLACE INTO "+BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE +" (UserID, "+ BluelinkSettings.UserInfo.DRIVING_LICENSE_IMAGE_BACK+") values ('"+UserID+"', '"+updateValue+"')";

            }
                  }
        catch (Exception e)
        {
            return false;
        }
        try
        {
            mDB.execSQL(sql);
        }
        catch (SQLException e)
        {
            return false;
        }
        return true;
    }


    // UserInfo
    public boolean insertUserPhoto(String userID, String updateValue)
    {

        userID = changeStringForDB(userID);
//        pinNumber = changeStringForDB(pinNumber);
        String sql = null;
        try
        {
            sql = "INSERT OR REPLACE INTO "+BluelinkDatabaseHelper.TABLE_USER_PHOTO +" (userId, "+ BluelinkSettings.UserInfo.USER_PHOTO+") values ('"+userID+"', '"+updateValue+"')";
        }
        catch (Exception e)
        {
            return false;
        }
        try
        {
            mDB.execSQL(sql);

        }
        catch (SQLException e)
        {
            return false;
        }
        return true;
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
