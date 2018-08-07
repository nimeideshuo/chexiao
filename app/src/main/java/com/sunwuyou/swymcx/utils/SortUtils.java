package com.sunwuyou.swymcx.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class SortUtils {
    public static List sort(List arg4, final String key, SortMode arg6) {
        ArrayList v1;
        MLog.d("排序:" + key);
        if (arg4 == null || (arg4.isEmpty())) {
            return null;
        }
//        Collections.sort(arg4, new Comparator() {
//
//
//            @Override
//            public int compare(Object o1, Object o2) {
//                HashMap arg9 = (HashMap) o1;
//                HashMap arg10 = (HashMap) o2;
//                int v4;
//                int v5 = -1;
//                String v2 = String.valueOf(arg9.get(key));
//                String v3 = String.valueOf(arg10.get(key));
//                int v0 = v2.length();
//                int v1 = v3.length();
//
//
//
//
//
//
//                return 0;
//            }
//        });

        return arg4;

    }
}
