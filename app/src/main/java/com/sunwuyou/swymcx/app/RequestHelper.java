package com.sunwuyou.swymcx.app;

/**
 * Created by admin
 * 2018/7/11.
 * content
 */

public class RequestHelper {
    public static boolean isSuccess(String paramString) {
        return !A.isFail(paramString);
    }
}
