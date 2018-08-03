package com.sunwuyou.swymcx.utils;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class PinYin4JUtils {
    public static String getPinYinHeadChar(String arg7) {
        String v0 = "";
        int v1;
        for(v1 = 0; v1 < arg7.length(); ++v1) {
            char v3 = arg7.charAt(v1);
            String[] v2 = PinyinHelper.toHanyuPinyinStringArray(v3);
            v0 = v2 != null ? String.valueOf(v0) + v2[0].charAt(0) : String.valueOf(v0) + v3;
        }

        return v0;
    }
}
