package com.sunwuyou.swymcx.http;


/**
 * @author Administrator
 * @content
 * @date 2017/11/29
 */

public class BaseUrl {
    //基础url
    private static String URL = "https://api.no1im.com";

    public static final String SYSTEM_CHECKREGISTER = "/system/checkregister";
    //查询工作账套
    public static final String SUPPORT_QUERYSACCOUNTSET = "/support/querysaccountset";

    public static String getUrl(String url) {

        return URL + url;
    }

    public static boolean setUrl(String urls) {
        URL = urls;
        return true;
    }

}
