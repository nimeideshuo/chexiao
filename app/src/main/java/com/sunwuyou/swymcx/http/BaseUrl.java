package com.sunwuyou.swymcx.http;


/**
 * @author Administrator
 * @content
 * @date 2017/11/29
 */

public class BaseUrl {
    public static final String SYSTEM_CHECKREGISTER = "/system/checkregister";
    //注册
    public static final String SYSTEM_REGISTER = "/system/register";

    public static final String SYSTEM_GETBIZPARAMETER = "/system/getbizparameter";
    //查询工作账套
    public static final String SUPPORT_QUERYSACCOUNTSET = "/support/querysaccountset";
    //查询部门
    public static final String SUPPORT_QUERYDEPARTMENT = "/support/querydepartment";
    //查询地区
    public static final String SUPPORT_QUERYREGION = "/support/queryregion";
    //查询路线
    public static final String SUPPORT_QUERYVISITLINE = "/support/queryvisitline";
    //用户登陆
    public static final String USER_USERLOGIN = "/user/userlogin";

    public static final String USER_GETAUTHORITYS = "/user/getauthoritys";
    //
    public static final String SYNCHRONIZE_QUERYACCOUNTRECORDS = "/synchronize/queryaccountrecords";
    //查询需要更新的表
    public static final String SYNCHRONIZE_QUERYUPDATEINFO = "/synchronize/queryupdateinfo";

    //默认版本名称
    public static String BASE_NAME = "mchexiaoban";
    //基础url
    private static String URL = "http://192.168.1.5:9682";

    public static String getUrl(String url) {

        return URL + url;
    }

    public static boolean setUrl(String urls) {
        URL = "http://" + urls;
        return true;
    }

}
