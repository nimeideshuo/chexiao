package com.immo.libcomm.utils;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class TextUtils {
    public static boolean isEmptyS(String str) {
        return ((str != null) && (str.length() != 0) && (!("null".equals(str))));
    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }
}
