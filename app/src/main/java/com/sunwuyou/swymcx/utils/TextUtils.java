package com.sunwuyou.swymcx.utils;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class TextUtils {
    public static boolean isEmptyS(String arg1) {
        return arg1 == null || arg1.length() == 0 || ("null".equals(arg1));
    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }
}
