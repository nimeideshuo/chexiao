package com.sunwuyou.swymcx.utils;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public enum SortMode {
    ASC, DESC;

    public static SortMode getAnother(SortMode paramSortMode) {
        if (paramSortMode == ASC) {
            return DESC;
        }
        return ASC;
    }

}
