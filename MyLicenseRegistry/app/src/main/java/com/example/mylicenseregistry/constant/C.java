package com.example.mylicenseregistry.constant;

/**
 * Created by duyong on 2017-04-04.
 */

public final class C {

    public class Url {
        public static final String OPENING_SERVICE_URL = "https://hmc.hyundaibluelink.com.cn/dlw/wechat/one.do?cocd=H";
        public static final String FORGET_PASSWORD_URL = "https://hmc.hyundaibluelink.com.cn/dlw/mup/home.do?ut=";
        public static final String MANUAL_URL = "http://manual.hyundaibluelink.com.cn/bluelink/";
    }

    public class Intent {
        public class key {
            public static final String url = "url";
            public static final String title = "title";
            public static final String ccspChange = "ccspChange";
            public static final String ccsId = "ccsId";
            public static final String ccsPwd = "ccsPwd";
            public static final String ccsAccount = "ccsAccount";
            public static final String strVin = "strVin";
        }
    }
    public class DateFormat {
        public static final String dateformat_yyyyMMdd_HHmmss = "yyyy.MM.dd HH:mm:ss";
        public static final String dateformat_yyyyMMdd_HHmm = "yyyy.MM.dd HH:mm";
        public static final String dateformat_yyyyMMdd = "yyyy.MM.dd";
    }
    public class CallNumber {
        public static final String CallNumber = "4008066066";
    }
}
