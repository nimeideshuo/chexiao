package com.sunwuyou.swymcx.utils;

import com.sunwuyou.swymcx.app.AccountPreference;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class Service {
    /*
     * 进行接口地址 拼接
	 */
    public static String getServiceAddress(String paramString1, String paramString2) {
        return paramString1 + "/" + paramString2;
    }

    /*
     * get 获取 IP 地址
     */
    public static String getIpUrl(String serviceIP) {
        // http://192.168.1.181:9682/system/checkregister
        return "http://" + serviceIP+"/";
    }

//    public static String getUrl() {
//        return "http://" + new AccountPreference().getServerIp() + ":90/";
//    }
}
