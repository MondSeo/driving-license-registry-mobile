package com.example.drivinglicenseupload.constant;

import com.ubivelox.bluelink_c.BuildConfig;
import com.ubivelox.bluelink_c.datadto.UserInfoLocalDB;
import com.ubivelox.bluelink_c.ui.BluelinkApp;
import com.ubivelox.bluelink_c.util.PreferenceUtil;

/**
 *
 */
public class AppConfig {

    public static boolean SMS_CERT_PASS = BuildConfig.SMS_CERT_PASS; //false == SMS인증 받으라는 화면이 떠야 한다.
    public static boolean NETWORK_TEST = false;
    public static boolean isUseHiddenMenuPreference = false;    //히든 프리퍼런스를 사용할 것인지 여부
    public static boolean isShowDebugToast = BuildConfig.DEBUG_TOAST;
    public static boolean isUvo = "KiaConnect".equalsIgnoreCase(BuildConfig.FLAVOR);

    // 투터치 이슈 해결 테스트, MIN_CLICK_DELAY ms 안에 클릭 시 무시
    public static final long MIN_CLICK_DELAY = 1000;

    public static String getAppName() {
        if (isUvo) {
            return "Kia Connect";
        } else {
            return "Bluelink";
        }
    }

    public static String getCenterNumber() {
        if (isUvo) {
            return "4008-070-018";
        } else {
            return "4008-066-066";
        }
    }

    public static class ServerUrl {

        /** TMS */
        public static final String SERVER_URL_DEVELOP = "https://tmsdsbs.hyundaibluelink.com.cn:8003/"; // 개발계
        public static final String SERVER_URL_VERIFICATION = "https://tmsqsbs.hyundaibluelink.com.cn:8003/"; // 검증계
        public static final String SERVER_URL_PRODUCTION = "https://TMSmwg2.hyundaibluelink.com.cn:8003/"; // 운영계

        private static final String SERVER_CCSP_UVO_STG = "https://stg.cn-ccapi.kia.com";
        private static final String SERVER_CCSP_UVO_PRO = "https://prd.cn-ccapi.kia.com";
        private static final String SERVER_CCSP_BLUELINK_STG = "https://stg.cn-ccapi.hyundai.com";
        private static final String SERVER_CCSP_BLUELINK_PRO = "https://prd.cn-ccapi.hyundai.com";

        private static final String SERVER_DKC_UVO_STG = "https://stg.cn-ccapi.kia.com";
        private static final String SERVER_DKC_UVO_PRO = "https://prd.cn-ccapi.kia.com";
        private static final String SERVER_DKC_BLUELINK_STG = "https://stg.cn-ccapi.hyundai.com";
        private static final String SERVER_DKC_BLUELINK_PRO = "https://prd.cn-ccapi.hyundai.com";

        private static final String SERVER_CPGW_STG = "https://cpapigwq.hyundaibluelink.com.cn:8092";
        private static final String SERVER_CPGW_PRO = "https://cpapigwp.hyundaibluelink.com.cn:8092";

        private static final String SERVER_URL_USER_AGREEMENT_BLUELINK = "https://video.hyundaibluelink.com.cn/agreement/agreement-BlueLink.html";
        private static final String SERVER_URL_PRIVACY_POLICY_BLUELINK = "https://video.hyundaibluelink.com.cn/agreement/privacyPolicy-BlueLink.html";

        private static final String SERVER_URL_USER_AGREEMENT_UVO = "https://video.kiauvo.com.cn/agreement/agreement-UVO.html";
        private static final String SERVER_URL_PRIVACY_POLICY_UVO = "https://video.kiauvo.com.cn/agreement/privacyPolicy-UVO.html";


        private static final String SERVER_URL_AUTOCAREWEB_STG = "https://nccwq.hyundaibluelink.com.cn:8091/nccw/main.do?vin=%s&bleOpt=%s";
        private static final String SERVER_URL_AUTCAREWEB_PRO = "https://nccw.hyundaibluelink.com.cn:8091/nccw/main.do?vin=%s&bleOpt=%s";

        private static final String SERVER_URL_CHANGE_CCSP_STG = "http://hmcq.hyundaibluelink.com.cn/dlw/qropen/auth/oneConvertId.do?cocd=%s&csmrId=%s";
        private static final String SERVER_URL_CHANGE_CCSP_PRD = "https://hmc.hyundaibluelink.com.cn/dlw/qropen/auth/oneConvertId.do?cocd=%s&csmrId=%s";

        public static String TMS_URL = getTMSUrl();

        public static String CCSP_ACCOUNT = getCCSPUrl();

        public static String DKC_API_SERVER = getDKCUrl();

        public static String CP_GW_SERVER = getCPGWUrl();

        public static String FIDO_URL = getFidoUrl();

        public static String ADD_PARM = BuildConfig.DEBUG_URL ? "" : ":443";

        public static String CCSP_CHANGE_URL = getCCSPChangeUrl();

        /** CCSP Login */
        //로그인
        public static final String CCSP_LOGIN_REDIRECT_URL  = CCSP_ACCOUNT + ADD_PARM + "/api/v1/user/oauth2/redirect";

        public static final String CCSP_LOGIN_URL           = CCSP_ACCOUNT + "/api/v1/user/oauth2/authorize?lang=" +
                "&client_id=" + Auth.ClientId +
                "&redirect_uri=" + CCSP_LOGIN_REDIRECT_URL +
                //"https%3A%2F%2Fstg.cn-ccapi.hyundai.com%2Fapi%2Fv1%2Fuser%2Foauth2%2Fredirect&state=r_basicprofile" +
                "&state=test" +
                "&response_type=code";
        /* 기존버전
        public static final String CCSP_LOGIN_URL           = CCSP_ACCOUNT + "/api/v1/user/oauth2/authorize?response_type=code" +
                "&client_id=" + Auth.ClientId +
                "&redirect_uri=" + CCSP_LOGIN_REDIRECT_URL +
                //"https%3A%2F%2Fstg.cn-ccapi.hyundai.com%2Fapi%2Fv1%2Fuser%2Foauth2%2Fredirect&state=r_basicprofile" +
                "&state=test";

         */

        //차량등록
        public static final String CCSP_ADD_VEHICLE_URL  = "http://106.39.45.29/dlw/qropen/auth/one.do?cocd=K";//2020-07-27 12:24 jiangxu mail
//        public static final String CCSP_ADD_VEHICLE_URL  = "http://10.109.134.67:8081/dlw/qropen/auth/one.do?cocd=K";//2020-11-05
        public static final String CCSP_ADD_VEHICLE_URL_COM  = "https://hmc.hyundaibluelink.com.cn/dlw/qropen/auth/one.do";
        // 실명 인증
        public static final String CCSP_REAN_NAME_ONE_URL = getRealNameOneUrl(); // BLE 등록 화면 버튼 선택시
        public static final String CCSP_REAN_NAME_RN_URL = getRealNameRnUrl(); // [ 차량 리스트, 메인] 차량 정보 조회시
        // 실명 인증 링크 URL 검증계
        private static final String CCSP_REAN_NAME_URL = "http://hmcq.hyundaibluelink.com.cn/dlw/qropen/auth/";
        // 실명 인증 링크 URL 운영계
        private static final String CCSP_REAN_NAME_URL_COM = "https://hmc.hyundaibluelink.com.cn/dlw/qropen/auth/";
        // 서비스 동의 링크 URL 검증계
        private static final String CCSP_PERSONAL_INFO_AUTH_URL = "http://hmcq.hyundaibluelink.com.cn/dlw/qropen/auth/appselectService.do?vin=%s&brand=%s&Authorization=%s";
        // 서비스 동의 링크 URL 운영계
        private static final String CCSP_PERSONAL_INFO_AUTH_URL_COM = "https://hmc.hyundaibluelink.com.cn/dlw/qropen/auth/appselectService.do?vin=%s&brand=%s&Authorization=%s";

        // 드라이빙 라이선스 URL 검증계
        private static final String CCSP_DRIVING_LICENSE_URL = "http://hmcq.hyundaibluelink.com.cn/dlw/qropen/auth/dlpage.do?vin=%s&cocd=%s";
        // 서비스 동의 링크 URL 운영계
        private static final String CCSP_DRIVING_LICENSE_URL_COM = "https://hmc.hyundaibluelink.com.cn/dlw/qropen/auth/dlpage.do?vin=%s&cocd=%s";

        /** CCSP Share */
        //03. Redirect Shared Info Web 공유 비밀번호 입력 & 공유 차량 선택
        public static final String CCSP_SHARE_URL           = CCSP_ACCOUNT + "/api/v1/profile/users/%s/shares/%s"; //{userid}/shares/{shareid} global api 변경사항 없음.

        //01. Request Car Sharing 차량 공유하기 API
        public static final String URL_SHARE                = CCSP_ACCOUNT + "/api/v1/profile/users/%s/cars/%s/share?redirect_uri=" //{user_id}/cars/{car_id}
                                                                        + ServerUrl.CCSP_ACCOUNT + "/api/v1/profile/redirect";
        //02. Request Member Sharing 차량 공유 요청하기
        public static final String URL_SHARE_REQUEST        = CCSP_ACCOUNT + "/api/v1/profile/users/%s/shares?redirect_uri="
                                                                        + ServerUrl.CCSP_ACCOUNT + "/api/v1/profile/redirect";
        //04. Redirect Shared Managing Web 공유 비밀번호 입력 & 공유 차량 선택
        public static final String URL_CHECK_SHARE          = CCSP_ACCOUNT + "/api/v1/profile/users/%s/cars/%s/members"; // userid, carid

        //05. USER_PROFILE cpw 사용자 설정 url
        public static final String URL_CPW_LOGIN          = CCSP_ACCOUNT + "/web/v1/cpw/login?token={CCSP_TOKEN}&vehicleId={CCSP_VEHICLE_ID}&lang=zh-CN";
        public static final String URL_CPW_LOGIN_BACKUP_ID          = CCSP_ACCOUNT + "/web/v1/cpw/login?token={CCSP_TOKEN}&vehicleId={CCSP_VEHICLE_ID}&backupId={CPW_BACKUP_ID}&lang=zh-CN";

        /** Irdeto */
        public static final String IRDETO_CLOUD_SERVER      = CCSP_ACCOUNT + "/api/v1/apps/dkc/web";

        /** FIDO - fingerprint */
        public static final String FIDO_SERVER_DEVELOP = "https://dev1.roundee.com/FIDO_Server_hmc/";  //개발서버
        public static final String FIDO_SERVER_BLUELINK_PROD = "https://cmpass.hyundai.com/";  //현대 운영서버
        public static final String FIDO_SERVER_UVO_PROD = "https://cmpass.kia.com/";  //기아 운영서버
        public static final String FIDO_REQUEST_URL = "processUafRequest.jspx?site=HMC_AUTOWAY";
        public static final String FIDO_RESPONSE_URL = "processUafResponse.jspx?site=HMC_AUTOWAY";

        private static final String FIDO_CN_SERVER_DEVELOP = "https://devauth.hyundai.com.cn/";  //중국 FIDO 개발서버
        private static final String FIDO_CN_SERVER_PRODUCT = "https://auth.hyundai.com.cn/";  //중국 FIDO 운영서버
        public static final String FIDO_CN_REQUEST_URL = "processUafRequest.jspx?site=chmc";
        public static final String FIDO_CN_RESPONSE_URL = "processUafResponse.jspx?site=chmc";

        public static String BAIDU_API_URL = "http://api.map.baidu.com"; // FindMyCar에서 바이두 맵 관련 API 추가

        private static String getFidoUrl(){
            if(BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug"))
                return FIDO_CN_SERVER_DEVELOP;
            else
                return FIDO_CN_SERVER_PRODUCT;
        }

        private static String getTMSUrl() {
            if (BuildConfig.DEBUG_URL) {
                return SERVER_URL_VERIFICATION;
            } else {
                return SERVER_URL_PRODUCTION;
            }
        }


        private static String getCCSPUrl() {
            if (AppConfig.isUvo) {
                if (BuildConfig.DEBUG_URL) {
                    return SERVER_CCSP_UVO_STG;
                }else {
                    return SERVER_CCSP_UVO_PRO;
                }
            } else {
                if (BuildConfig.DEBUG_URL) {
                    return SERVER_CCSP_BLUELINK_STG;
                }else {
                    return SERVER_CCSP_BLUELINK_PRO;
                }
            }
        }

        private static String getDKCUrl() {
            if (AppConfig.isUvo) {
                if (BuildConfig.DEBUG_URL) {
                    return SERVER_DKC_UVO_STG;
                } else {
                    return SERVER_DKC_UVO_PRO;
                }
            } else {
                if (BuildConfig.DEBUG_URL) {
                    return SERVER_DKC_BLUELINK_STG;
                } else {
                    return SERVER_DKC_BLUELINK_PRO;
                }

            }
        }

        private static String getCPGWUrl() { //CP GateWay는 브랜드 구분 없음.
            if (BuildConfig.DEBUG_URL) {
                return SERVER_CPGW_STG;
            }else {
                return SERVER_CPGW_PRO;
            }
        }

        public static String getDefaultHelpUrl() {
            String domainBluelink = "https://video.hyundaibluelink.com.cn";
            String domainUvo = "https://video.kiauvo.com.cn" ;

            if (AppConfig.isUvo) {
                if (BluelinkApp.isCCSP()) {
                    return domainUvo + "/manual/ccsp_uvo/index1.html";
                } else {
                    return domainUvo + "/manual/ccs_uvo/index2.html";
                }
            } else {
                if (BluelinkApp.isCCSP()) {
                    return domainBluelink + "/manual/ccsp_bluelink/index1.html";
                } else {
                    return domainBluelink + "/manual/ccs_bluelink/index2.html";
                }
            }
        }

        public static String getManualUrl() {
            String BLUELINK_MANUAL_URL = "http://manual.hyundaibluelink.com.cn/bluelink";
            String UVO_MANUAL_URL = "http://manual.hyundaibluelink.com.cn/uvo";

            if (AppConfig.isUvo) {
                return UVO_MANUAL_URL;
            } else {
                return BLUELINK_MANUAL_URL;
            }
        }

        public static String getUserAgreementUrl() {
            if (AppConfig.isUvo) {
                return SERVER_URL_USER_AGREEMENT_UVO;
            } else {
                return SERVER_URL_USER_AGREEMENT_BLUELINK;
            }
        }


        public static String getPrivacyPolicyUrl() {
            if (AppConfig.isUvo) {
                return SERVER_URL_PRIVACY_POLICY_UVO;
            } else {
                return SERVER_URL_PRIVACY_POLICY_BLUELINK;
            }
        }

        // Autocare
        public static String getAutocareUrl(){
            String autocarUrl = "";
            if (BuildConfig.DEBUG_URL) {
                autocarUrl = SERVER_URL_AUTOCAREWEB_STG;
            }else{
                autocarUrl = getCCSPUrl() + "/api/v1/nccw/main.do?vin=%s&bleOpt=%s";
            }
            return autocarUrl;
        }

        public static String getPersonalInfoAuthUrl(){
            if (BuildConfig.DEBUG_URL) {
                return CCSP_PERSONAL_INFO_AUTH_URL;
            } else {
                return CCSP_PERSONAL_INFO_AUTH_URL_COM;
            }
        }

        public static String getDrivingLicenseUrl(){
            if (BuildConfig.DEBUG_URL) {
                return CCSP_DRIVING_LICENSE_URL;
            } else {
                return CCSP_DRIVING_LICENSE_URL_COM;
            }
        }

        // 딱지범칙금대납 조회하기
        public static String getTrafficTicketStickerUrl(){
            String trafficTicketH5_URL = "https://bl.cx580.com/illegal?";

            return trafficTicketH5_URL;
            //TODO 딱지범칙금대납 조회 Web Url 받아서 기입.
//            if (AppConfig.isUvo) {
//                return null;
//            } else {
//                return null;
//            }
        }

        public static String getCCSPChangeUrl() {
            if (BuildConfig.DEBUG_URL) {
                return SERVER_URL_CHANGE_CCSP_STG;
            }else {
                return SERVER_URL_CHANGE_CCSP_PRD;
            }
        }

        public static String getRealNameOneUrl(){
            String url = "oneConvertIdCcsp.do";
            if (BuildConfig.DEBUG_URL) {
                return CCSP_REAN_NAME_URL + url;
            }else {
                return CCSP_REAN_NAME_URL_COM + url;
            }
        }

        public static String getRealNameRnUrl(){
            String url = "rnCertification.do";
            if (BuildConfig.DEBUG_URL) {
                return CCSP_REAN_NAME_URL + url;
            }else {
                return CCSP_REAN_NAME_URL_COM + url;
            }
        }

        public static String makeRealNameUrl(UserInfoLocalDB userInfoLocalDB, PreferenceUtil preferenceUtil, String realNameUrl, String vinNumber){
            String mobileNum = userInfoLocalDB.userInfo_CCSP.getMobileNum();

            if(mobileNum != null && mobileNum.length() > 0)
                mobileNum = mobileNum.replace("+86", "");

            String email = userInfoLocalDB.userInfo_CCSP.getEmail();
            String name = userInfoLocalDB.userInfo_CCSP.getName();
            String cocd = AppConfig.isUvo ? Features.HEADER_BRAND_KIA : Features.HEADER_BRAND_HYUNDAI;
            String masterId = preferenceUtil.getPreference(PrefKeys.KEY_CCSP_UID);
            String birthdate = userInfoLocalDB.userInfo_CCSP.getBirthdate();

            String postStr = "";
            // URL 인코딩 제거
            if (vinNumber == null) {
                postStr = "masterId=" + masterId;
            } else {
                postStr = "vin=" + vinNumber
                        + "&masterId=" + masterId;
            }
            postStr += "&birthdate=" + birthdate
                    + "&mobileNum=" + mobileNum
                    + "&email=" + email
                    + "&name=" + name
                    + "&cocd=" + cocd;

            return realNameUrl + "?" + postStr;
        }
    }

    public static class Auth {
        public static final String ClientId = isUvo ? "9d5df92a-06ae-435f-b459-8304f2efcc67" : (BuildConfig.DEBUG_URL ? "5acbbdd4-210a-4abd-b6b0-ca5cf31ce032" : "72b3d019-5bc7-443d-a437-08f307cf06e2" );
        public static final String secretID = isUvo ? (BuildConfig.DEBUG_URL ? "secret" : "tsXdkUg08Av2ZZzXOgWzJyxUT6yeSnNNQkXXPRdKWEANwl1p" ) : "secret";
        public static final String AppicationId = isUvo ? (BuildConfig.DEBUG_URL ? "03773c41-d724-4e75-8050-2b035acc4d23" : "eea8762c-adfc-4ee4-8d7a-6e2452ddf342") : (BuildConfig.DEBUG_URL ? "78e1b077-6899-43dc-b650-97b0d1c323f3" : "ed01581a-380f-48cd-83d4-ed1490c272d0" );
    }
}
