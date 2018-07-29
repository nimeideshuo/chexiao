package com.sunwuyou.swymcx.utils;

/**
 * Created by admin
 * 2018/7/29.
 * content
 */

public class ClickUtils {
    private static long lastClickTime;

    static {
        ClickUtils.lastClickTime = 0;
    }

    public ClickUtils() {
        super();
    }

    public static boolean isFastDoubleClick() {
        long v0 = System.currentTimeMillis();
        long v2 = v0 - ClickUtils.lastClickTime;
        ClickUtils.lastClickTime = v0;
        return !(0 >= v2 || v2 >= 1000);
    }
}
