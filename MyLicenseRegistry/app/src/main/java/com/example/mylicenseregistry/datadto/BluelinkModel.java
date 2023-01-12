package com.example.mylicenseregistry.datadto;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.example.mylicenseregistry.util.LogUtils;

import java.util.ArrayList;

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
                    + BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK + ")" + " values (" + "'" + insertValue1 +"', '" + insertValue2 + "')";

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
    public ArrayList<Bundle> selectDrivingLicenseImageTable() {

        ArrayList<Bundle> list = new ArrayList<Bundle>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
                sb.append("SELECT");
                sb.append(" *");
                sb.append(" FROM " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE);
                sb.append(";");
//            Logger.v(getClass().getSimpleName(), sb.toString());

            cursor = mDB.rawQuery(sb.toString(), null);
            if(cursor == null)
                return list;
         while (cursor.moveToNext()){
             Bundle bundle = new Bundle();

             int numIndex = cursor.getInt(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.INDEX));
             String frontImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT));
             String backImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK));

             bundle.putInt(BluelinkSettings.DrivingLicenseImage.INDEX,numIndex);
             bundle.putString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT,frontImage);
             bundle.putString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK,backImage);
             list.add(bundle);
         }
        } catch (Exception e) {
            e.printStackTrace();
//            errorLogcat("selectUserPhotoTable 실패",e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public ArrayList<Bundle> selectPositionDrivingLicenseImage(int index){
        ArrayList<Bundle> list = new ArrayList<Bundle>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE);
            sb.append(" WHERE " + "_index = "+ index);
            sb.append(";");
//            Logger.v(getClass().getSimpleName(), sb.toString());

            cursor = mDB.rawQuery(sb.toString(), null);
            if(cursor == null)
                return list;
            while (cursor.moveToNext()){
                Bundle bundle = new Bundle();

                int numIndex = cursor.getInt(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.INDEX));
                String frontImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT));
                String backImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK));

                bundle.putInt(BluelinkSettings.DrivingLicenseImage.INDEX,numIndex);
                bundle.putString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT,frontImage);
                bundle.putString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK,backImage);
                list.add(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            errorLogcat("selectUserPhotoTable 실패",e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean deleteDrivingLicenseImage(int index)
    {
        String sql = null;

        try
        {
            sql = "DELETE FROM " + BluelinkDatabaseHelper.TABLE_DRIVING_LICENSE_IMAGE + " WHERE " + BluelinkSettings.DrivingLicenseImage.INDEX + "=" + index + ";";
        }
        catch (Exception e)
        {
            // DebugPrint.printDebug(TAG, e.getMessage());
            return false;
        }

        try
        {
            mDB.execSQL(sql);
        }
        catch (SQLException e)
        {
            // DebugPrint.printError(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    //vehicle
    public boolean insertVehicleRegistrationImageTable(String insertValue1, String insertValue2) {

        String sql = null;
        try {
            sql = "INSERT INTO " + BluelinkDatabaseHelper.TABLE_VEHICLE_REGISTRATION_IMAGE + "(" + BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT + ", "
                    + BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK + ")" + " values (" + "'" + insertValue1 +"', '" + insertValue2 + "')";

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
    public ArrayList<Bundle> selectVehicleRegistrationImageTable() {

        ArrayList<Bundle> list = new ArrayList<Bundle>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM " + BluelinkDatabaseHelper.TABLE_VEHICLE_REGISTRATION_IMAGE);
            sb.append(";");
//            Logger.v(getClass().getSimpleName(), sb.toString());

            cursor = mDB.rawQuery(sb.toString(), null);
            if(cursor == null)
                return list;
            while (cursor.moveToNext()){
                Bundle bundle = new Bundle();

                int numIndex = cursor.getInt(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.INDEX));
                String frontImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT));
                String backImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK));

                bundle.putInt(BluelinkSettings.VehicleRegistrationImage.INDEX,numIndex);
                bundle.putString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT,frontImage);
                bundle.putString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK,backImage);
                list.add(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            errorLogcat("selectUserPhotoTable 실패",e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public ArrayList<Bundle> selectPositionVehicleRegistrationImage(int index){
        ArrayList<Bundle> list = new ArrayList<Bundle>();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM " + BluelinkDatabaseHelper.TABLE_VEHICLE_REGISTRATION_IMAGE);
            sb.append(" WHERE " + "_index = "+ index);
            sb.append(";");
//            Logger.v(getClass().getSimpleName(), sb.toString());

            cursor = mDB.rawQuery(sb.toString(), null);
            if(cursor == null)
                return list;
            while (cursor.moveToNext()){
                Bundle bundle = new Bundle();

                int numIndex = cursor.getInt(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.INDEX));
                String frontImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT));
                String backImage = cursor.getString(cursor.getColumnIndex(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK));

                bundle.putInt(BluelinkSettings.VehicleRegistrationImage.INDEX,numIndex);
                bundle.putString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT,frontImage);
                bundle.putString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK,backImage);
                list.add(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            errorLogcat("selectUserPhotoTable 실패",e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean deleteVehicleRegistrationImage(int index)
    {
        String sql = null;

        try
        {
            sql = "DELETE FROM " + BluelinkDatabaseHelper.TABLE_VEHICLE_REGISTRATION_IMAGE + " WHERE " + BluelinkSettings.DrivingLicenseImage.INDEX + "=" + index + ";";
        }
        catch (Exception e)
        {
            // DebugPrint.printDebug(TAG, e.getMessage());
            return false;
        }

        try
        {
            mDB.execSQL(sql);
        }
        catch (SQLException e)
        {
            // DebugPrint.printError(TAG, e.getMessage());
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
