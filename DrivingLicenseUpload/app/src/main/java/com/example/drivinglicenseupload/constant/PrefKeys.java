package com.example.drivinglicenseupload.constant;

public interface PrefKeys {
    String KEY_MY_CCSP_COUNT = "MyCCSPCount";
    String KEY_SHARED_CCSP_COUNT = "SharedCCSPCount";
    String KEY_CCSP_CAR_ID = "ccspCarId";
    String KEY_CCSP_TOKEN_TYPE = "ccspTokenType";
    String KEY_CCSP_ACCESS_TOKEN = "ccspAccessToken";
    String KEY_CCSP_SID = "ccspSID";
    String KEY_CCSP_UID = "ccspUID";
    String KEY_PHONE_NUMBER = "phoneNumber";
    String KEY_CCSP_TOKEN_TIME = "ccspTokenTime";
    String KEY_HELP_PAGE = "HelpPage";
    String KEY_BLE_USER_ID = "bleUserID";
    String KEY_USER_TYPE = "UserType";
    String KEY_CCSP_REFRESH_TOKEN = "ccspRefreshToken";
    String KEY_SELECTED_USER_ID = "selectedUserID";
    String KEY_SELECTED_VIN = "selectedVin";
    String KEY_OWNER_REQUEST_KEY_COUNT = "ownerRequestKeyCnt";
    String KEY_USER_REQUEST_KEY_COUNT = "userRequestKeyCnt";
    String KEY_REMOTE_TIME_STAMP = "remoteTimeStamp";
    String KEY_CONTROL_KEY_TIME_STAMP = "controlKeyTimeStamp";
    String KEY_USER_PASSWORD = "UserPassword";
    String KEY_HOLD_UPDATE_NOTICE_TIME = "holdUpdateNoticeTime";
    String KEY_NOTICE_ID = "noticeID";
    //    String KEY_POWER_SAVED = "powerSaved";
    String KEY_HEADER_TID = "header_TID";
    String KEY_HEADER_NADID = "header_NADID";
    String KEY_HEADER_SID = "header_SID";
    String KEY_CCSP_CONTROL_TOKEN = "controlToken";
    String KEY_QRCODE_SEND_BUTTON = "isQRCode_SendButton";
    String KEY_SELECTED_CAR_INFO = "KEY_SELECTED_CAR_INFO";
    String KEY_PROFILE_INDEX = "KEY_PROFILE_INDEX";
    String KEY_USER_AGREEMENT_INFO = "UserAgreementInfo";
    // 앱 최초 설치시 - PassiveMode, WelcomeLight 설정 팝업
    String KEY_PASSIVE_MODE_AND_WELCOMLIGHT = "PassiveModeAndWelcomeLight";
    // 로그인 사용자 차량리스트 카운트 >  메인화면 상단에 차량리스트 이동 화면 여부 표시에서 사용
    String KEY_USER_VEHICLE_LIST_COUNT = "KEY_USER_VEHICLE_LIST_COUNT";
    //Remote Control 최초진입
    String KEY_REMOTE_CONTROL_FIRST_OPEN = "remote_control_first_open";
    //Splash 화면을 스킵할지 안할지의 여부
    String KEY_SPLASH_SHOW = "splash_show";
    //공조설정 최초 진입 시 애니메이션 띄우기.
    String KEY_CLIMATE_CONTROL_FIRST_OPEN = "climate_control_first_open";

    //FIDO에 지문 등록 되었는지 여부
    String KEY_FINGERPRINT_REGISTERED = "fingerprint_registered_FIDO";
    //교통 위반 조회 서비스 최초 진입 시 개인정보동의 팝업
    String KEY_TRAFFIC_TICKET_AGREEMENT = "traffic_ticket_agreement";
    //Notice Event 팝업 다시 보지않기
    String KEY_POPUP_TODAY_NOT_SHOW = "popup_not_show";
    //SPA Server에서 가져오는 Device Id
    String KEY_SPA_PUSH_DEVICE_ID = "spa_device_id";
    //PINNumber
    String KEY_PIN_NUMBER = "pin_number";
    // Virus Kill On/Off
    String KEY_VIRUS_KILL_ON = "virus_kill_on";

    // Push Notification Unread Count
    String KEY_EXISTING_NOTI_COUNT = "existing_noti_count";

    String KEY_USER_VEHICLE_LIST_TO_SEND = "userVehicleListToSend_";

    String KEY_DKC_CAR_OWNER_COUNT = "DkcOwnerCarCount";
    String KEY_DKC_CAR_SHARED_COUNT = "DkcSharedCarCount";

    String KEY_SELECTED_CCS_CAR_INFO = "KEY_SELECTED_CCS_CAR_INFO";

    String KEY_APP_BG_START_TIME = "AppBgStartTime";
    // Engine Start Option Popup 묻기 여부
    String KEY_DO_NOT_SHOW_ENGINE_START_OPTION = "show_engine_start_option";

    String KEY_FIRST_EXECUTE = "KEY_FIRST_EXECUTE";

    String KEY_BLE_ICON_STATE = "KEY_BLE_ICON_STATE";

    String KEY_USE_AIR_CONTROL = "KEY_USE_AIR_CONTROL";

    String KEY_CAR_PROFILE_INDEX = "KEY_CAR_PROFILE_INDEX";

    String KEY_SETTING_PERSONALAUTH_OPENED = "KEY_SETTING_PERSONALAUTH_OPENED";

    String KEY_DRIVING_LICENSE_DONT_REMIND_7DAYS = "KEY_DRIVING_LICENSE_DONT_REMIND_7DAYS";

    // map 선택 했을때, 권한 팝업 한번만 띄우게
    String KEY_MAP_LOCATION_PERMISSON_SHOW = "KEY_MAP_LOCATION_SHOW";

    String KEY_HUAWEI_PERMISSION_STORAGE_CHECK = "KEY_HUAWEI_PERMISSION_STORAGE_CHECK";
    String KEY_HUAWEI_PERMISSION_LOCATION_CHECK = "KEY_HUAWEI_PERMISSION_LOCATION_CHECK";
    String KEY_HUAWEI_PERMISSION_PHONESTATE_CHECK = "KEY_HUAWEI_PERMISSION_PHONESTATE_CHECK";
    String KEY_HUAWEI_PERMISSION_CAMERA_CHECK = "KEY_HUAWEI_PERMISSION_CAMERA_CHECK";
    String KEY_HUAWEI_PERMISSION_NOTIFICATION_CHECK = "KEY_HUAWEI_PERMISSION_NOTIFICATION_CHECK";
    String KEY_HUAWEI_PERMISSION_BLUETOOTH_CHECK = "KEY_HUAWEI_PERMISSION_BLUETOOTH_CHECK";
    String KEY_SHOW_THIRD_PHONE_POPUP = "KEY_SHOW_THIRD_PHONE_POPUP";

    //Mond 추가
    String KEY_DRIVING_LICENSE_INDEX = "KEY_DRIVING_LICENSE_INDEX";
    String KEY_DRIVING_LICENSE_CERTIFICATE = "KEY_DRIVING_LICENSE_CERTIFICATE";
    String KEY_DRIVING_LICENSE_SURFACE = "KEY_DRIVING_LICENSE_SURFACE";
    String KEY_DRIVING_LICENSE_FRONT = "KEY_DRIVING_LICENSE_FRONT";
    String KEY_DRIVING_LICENSE_BACK = "KEY_DRIVING_LICENSE_BACK";

}
