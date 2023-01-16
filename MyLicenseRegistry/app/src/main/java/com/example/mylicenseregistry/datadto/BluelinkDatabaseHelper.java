package com.example.mylicenseregistry.datadto;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BluelinkDatabaseHelper extends SQLiteOpenHelper {

	private final String TAG = "BluelinkDatabaseHelper";
	
	private static final String DATABASE_NAME = "Bluelink2012c.db";
	private static final int DATABASE_VERSION = 7;
	/*********************
     DATABASE_VERSION update comment
     version : date : message
     6 : 2021-03-25 : TABLE_USER_PHOTO 추가
	*********************/

    //Mond 추가
    public static final String TABLE_DRIVING_LICENSE_IMAGE = "DRIVING_LICENSE_IMAGE";
    public static final String TABLE_VEHICLE_REGISTRATION_IMAGE = "VEHICLE_REGISTRATION_IMAGE";

    public static final String TABLE_USER_PHOTO = "USER_PHOTO";
	public static final String TABLE_USER_INFO = "USER_INFO";
	public static final String TABLE_VEHICLE_INFO = "VEHICLE_INFO";
	public static final String TABLE_DEALER_INFO = "DEALER_INFO";
	public static final String TABLE_POLICY_INFO = "POLICY_INFO"; //2020-06-15 Add TABLE > DATABASE_VERSION : 5 에서 추가
	//@@Apps Remote control 테이블 추가  .
    public static final String TABLE_REMOTE_CONTROL_INFO = "REMOTE_CONTROL_INFO";
    public static final String TABLE_START_TIME_INFO = "START_TIME_INFO";  //공조 및 예약 시동 설정 테이블 추가.
    public static final String TABLE_NOTICE_EVENT_INFO = "NOTICE_EVENT_INFO";  //Notice/Event 테이블 추가.
    public static final String TABLE_BLE_REMOTE_CONTROL_INFO = "BLE_REMOTE_CONTROL_INFO"; // 서비스 히스토리 (BLE 리모트 컨트롤)
    public static final String TABLE_BLE_SHARE_KEY_INFO = "BLE_SHARE_KEY_INFO"; // 서비스 히스토리 (디지털키 공유)

	public BluelinkDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

        db.execSQL(getUserInfoCreateQuery());
        db.execSQL(getBleRemoteControlCreateQuery()); // 201028
        db.execSQL(getUserPhotoCreateQuery()); // 2021-03-25
        db.execSQL(getDrivingLicenseImageCreateQuery());
        db.execSQL(getVehicleRegistrationImageCreateQuery());
	}

	private String getUserInfoCreateQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO);
        sb.append( " ('userId' TEXT PRIMARY KEY ,");
        sb.append( " 'user_info_json' TEXT DEFAULT NULL );");
        return sb.toString();
    }



    private String getUserPhotoCreateQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_USER_PHOTO);
        sb.append( " ('userId' TEXT PRIMARY KEY ,");
        sb.append( " '"+ BluelinkSettings.UserInfo.USER_PHOTO +"' TEXT DEFAULT NULL,");
        sb.append( " '"+ BluelinkSettings.UserInfo.USER_PHOTO_CPW +"' TEXT DEFAULT NULL );");
        return sb.toString();
    }



    private String addUserPhotoCpwColumn() {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE " + TABLE_USER_PHOTO + " ADD COLUMN ");
        sb.append(BluelinkSettings.UserInfo.USER_PHOTO_CPW + " TEXT DEFAULT NULL");
        return sb.toString();
    }


    //Mond 추가
    private String getDrivingLicenseImageCreateQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_DRIVING_LICENSE_IMAGE);
        sb.append( " ('_index' INTEGER PRIMARY KEY ,");
        sb.append(" '" + BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT + "' TEXT NOT NULL,");
        sb.append(" '" + BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK + "' TEXT NOT NULL);");
        return sb.toString();
    }

    //Mond 추가
    private String getVehicleRegistrationImageCreateQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLE_REGISTRATION_IMAGE);
        sb.append( " ('_index' INTEGER PRIMARY KEY ,");
        sb.append(" '" + BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT + "' TEXT NOT NULL,");
        sb.append(" '" + BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK + "' TEXT NOT NULL);");
        return sb.toString();
    }

    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dbUpgradeForCpwColumn(db);
	}



    private void dbUpgradeForCpwColumn(SQLiteDatabase db) {
        db.execSQL(addUserPhotoCpwColumn());
//        db.execSQL(addDringLicenseImageCpwColumn());
    }


    public String getOldEncryptedID(Context context) {
        String PREFER_NAME_LOCAL_DATA = "BluelinkCNLocalData";
        String PREFER_KEY_ID = "id";

        String id = "";
        SharedPreferences pref = context.getSharedPreferences(PREFER_NAME_LOCAL_DATA, Context.MODE_PRIVATE);
        try {
            id = pref.getString(PREFER_KEY_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void cleanOldPreference(Context context) {
        String PREFER_NAME_LOCAL_DATA = "BluelinkCNLocalData";
            SharedPreferences pref = context.getSharedPreferences(PREFER_NAME_LOCAL_DATA, Context.MODE_PRIVATE);
            if (pref.getAll().size() > 0) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

            }

    }

    private String getBleRemoteControlCreateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_BLE_REMOTE_CONTROL_INFO);
        sb.append( " ('userId' TEXT PRIMARY KEY ,");
        sb.append( " 'ble_remote_control_info_json' TEXT DEFAULT NULL );");
        return sb.toString();
    }


}


