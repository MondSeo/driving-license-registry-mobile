package com.example.mylicenseregistry.datadto;

/**
 * Settings related utilities.
 */
public class BluelinkSettings { 
	public static final int CATEGORY_HOME				= 0;
	public static final int CATEGORY_OFFICE				= 1;
	public static final int CATEGORY_RESTAURANT			= 2;
	public static final int CATEGORY_SHOPPINGMALL		= 3;
	public static final int CATEGORY_HOTEL				= 4;
	public static final int CATEGORY_HOSPITAL			= 5;
	public static final int CATEGORY_BANK				= 6;
	public static final int CATEGORY_CARCENTER			= 7;
	public static final int CATEGORY_ETC				= 8;
	
//	private static int[] CATEGORY_LIST_ID = {R.string.category_home,
//			R.string.category_office, R.string.category_restaurant,
//			R.string.category_shoppingmall, R.string.category_hotel,
//			R.string.category_hospital, R.string.category_bank,
//			R.string.category_carcenter, R.string.category_etc};
//
//	private static String[] CATEGORY_LIST = new String[9];
	
//	public static String[] getCategory(Context context) {
//		for (int i = 0; i < 9; i++) {
//			if(CATEGORY_LIST[i]== null) {
//				CATEGORY_LIST[i] = context.getString(CATEGORY_LIST_ID[i]);
//			}
//		}
//		return CATEGORY_LIST;
//	}
	
	public static final class UserInfo {
		/**
		 * Type : TEXT
		 */
		public static final String USER_ID = "user_id";
		
		/**
		 * Type : TEXT
		 */
		public static final String PIN_NUMBER = "pin_number";
		
		/**
		 * Type : INTEGER (0 : false, 1 : true)
		 */
		public static final String AUTO_LOGIN = "auto_login";
		
		/**
		 * Type : INTEGER (0 : false, 1 : true)
		 */
		public static final String AGREEMENT = "agreement";
		
		/**
		 * Type : INTEGER (0 : false, 1 : true)
		 */
		public static final String INITIALIZE = "initialize";
		
		/**
		 * Type : TEXT
		 */
		public static final String PHONE_NUMBER = "phone_number";

		/**
		 * Type : TEXT
		 */
		public static final String USER_PHOTO = "user_photo";

		/**
		 * Type : TEXT
		 */
		public static final String USER_PHOTO_CPW = "user_photo_cpw";

	}
	
	public static final class VehicleStatus {
		/**
		 * Type : INTEGER
		 * PRIMARY KEY AUTOINCREMENT
		 */
		public static final String INDEX = "_index";
		
		/**
		 * Type : TEXT
		 */
		public static final String USER_INFO_ID = "user_info_id";
		
		/**
		 * Type : TEXT
		 */
		public static final String TEMP = "temp";
		
		/**
		 * Type : INTEGER (0 : false, 1 : true)
		 */
		public static final String IS_SAVE_TEMP = "is_save_temp";
		
		/**
		 * Type : INTEGER (0 : 섭씨, 1 : 화씨)
		 */
		public static final String TEMP_TYPE = "temp_type";
		
		/**
		 * Type : TEXT
		 */
		public static final String LAST_ENGINE_ON_TIME = "last_engine_on_time";
		
		/**
		 * Type : INTEGER (0 ~ 20)
		 */
		public static final String ENGINE_ON_CYCLE = "engine_on_cycle";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_VIN = "info_vin";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_VEHICLE_TYPE = "info_vehicle_type";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_VEHICLE_NUMBER = "info_vehicle_number";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_OUT_COLOR = "info_out_color";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_CAR_YEAR = "info_car_year";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_FUEL_TYPE = "info_fuel_type";
		
		/**
		 * Type : TEXT
		 */
		public static final String INFO_TMISSION = "info_tmission";
	}
	
	public static final class DealerInfo {
		/**
		 * Type : INTEGER
		 * PRIMARY KEY AUTOINCREMENT
		 */
		public static final String INDEX = "_index";
		
		/**
		 * Type : TEXT
		 */
		public static final String VIN_NUMBER = "vin_number";
		
		/**
		 * Type : TEXT
		 */
		public static final String DEALER_NAME = "dealer_name";
		
		/**
		 * Type : TEXT
		 */
		public static final String PHONE_NUM = "phone_num";
	}

	//Mond 추가
	public static final class DrivingLicenseImage {
		//Mond 추가
		/**
		 * Type : INTEGER
		 * PRIMARY KEY AUTOINCREMENT
		 */
		public static final String INDEX = "_index";

		/**
		 * Type : TEXT
		 */
		public static final String DRIVING_LICENSE_IMAGE_FRONT = "driving_license_image_front";

		/**
		 * Type : TEXT
		 */
		public static final String DRIVING_LICENSE_IMAGE_BACK = "driving_license_image_back";

		/**
		 * Type : INTEGER
		 */
		public static final String SEQ = "seq";

	}

	public static final class VehicleRegistrationImage {
		/**
		 * Type : INTEGER
		 * PRIMARY KEY AUTOINCREMENT
		 */
		public static final String INDEX = "_index";

		/**
		 * Type : TEXT
		 */
		public static final String VEHICLE_REGISTRATION_IMAGE_FRONT = "vehicle_registration_image_front";

		/**
		 * Type : TEXT
		 */
		public static final String VEHICLE_REGISTRATION_IMAGE_BACK = "vehicle_registration_image_back";

		/**
		 * Type : INTEGER
		 */
		public static final String SEQ = "seq";
	}
	
//	public static final class POI {
//		/**
//		 * Type : INTEGER
//		 * PRIMARY KEY AUTOINCREMENT
//		 */
//		public static final String INDEX = "_index";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String USER_INFO_ID = "user_info_id";
//		
//		/**
//		 * Type : INTEGER
//		 */
//		public static final String CATEGORY = "category";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String NAME = "name";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String PHONE_NUMBER = "phone_number";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String PHOTO_PATH_1 = "photo_path_1";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String PHOTO_PATH_2 = "photo_path_2";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String PHOTO_PATH_3 = "photo_path_3";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String LATITUDE = "latitude";
//		
//		/**
//		 * Type : TEXT
//		 */
//		public static final String LONGITUDE = "longitude";
//	}
}
