package com.sunwuyou.swymcx.utils;

import android.util.Log;

/**
 * Created by admin
 * 2018/7/18.
 * content
 */

public class MLog {
    public static final String TAG = "TEST";

    public MLog() {
        super();
    }

    public static void d(Object obj) {
        if (obj != null) {
            Log.d(TAG, obj.toString());
        }
    }

    public static void d(Object arg2, Object arg3) {
        if (arg2 != null && arg3 != null) {
            Log.d(arg2.getClass().getSimpleName(), arg3.toString());
        }
    }
}
