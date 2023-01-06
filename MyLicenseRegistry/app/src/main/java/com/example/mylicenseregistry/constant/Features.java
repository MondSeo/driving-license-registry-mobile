package com.example.mylicenseregistry.constant;

public class Features {
    // //////////////////////////////////////////////////////////////////////
    // Build Flag
    // //////////////////////////////////////////////////////////////////////

    // Debug Option
	public static final boolean TM_DEBUG = true;
    public static final boolean TM_SERVICE_DEBUG = true;

    /** GEN1, GEN2 ServerType */
    public final static int GEN1_SERVER = 0;
    public final static int GEN2_SERVER = 1;
    public final static int GEN3_SERVER = 2;

    /** TMU Type */
    public final static int GENERAL     = 10;

    //BLE 지원하는 차
    public final static int BLE_AV      = 20;

    //전기차
    public final static int AEEV        = 30;
    public final static int HDcEV       = 31; //※ HDc EV 차종의 경우, 예약충전/공조, 즉시충전(충전시작/충전취소) 기능 미 제공(invisible)
    public final static int OScEV       = 32;

    //친화경차
    public final static int PHEV        = 41;
    public final static int ADcBDcPHEV  = 42;


    /** Vehicle Style */
    public final static int UNKNOWN = 9; //알수없음
    public final static int SEDAN = 10; //승용차
    public final static int SUV = 11; //SUV
    public final static int RV = 12; //Sliding door
    public final static int MPV = 13; //MPV

    public static final String HEADER_BRAND_HYUNDAI = "H";
    public static final String HEADER_BRAND_KIA = "K";

    public final static long REMOTE_CONTROL_WAIT_TIME = 3 * 1000; //30초 이내로 호출하지 못하게 함. 시간은 프리퍼런스에 저장해놓고 본다.  <!-- 2021-04-12 : 30초에서 3초로 수정, 문구 수정 > 请稍后再试 (잠시 후에 다시 실행해 주세요.)-->
    public final static String CALL_CENTER_NUMBER = "18990627";


    public final static long CONTROL_KEY_EXPIRE_TIME = 570 * 1000; //9분30초 넘으면 expire로 간주 .

    public static final boolean TM_REMODEL_DATABASE = false;
}
