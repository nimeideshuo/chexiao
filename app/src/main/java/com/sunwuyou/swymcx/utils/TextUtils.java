package com.sunwuyou.swymcx.utils;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

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

    public static CharSequence setTextStyle(String str1, String str2) {
        SpannableString spannable = new SpannableString(str1 + str2);
        spannable.setSpan(new ForegroundColorSpan(-65536), spannable.length() - str2.length(), spannable.length(), 33);
        return spannable;
    }
    public static String out(String arg1) {
        if(TextUtils.isEmptyS(arg1)) {
            arg1 = "";
        }

        return arg1;
    }
}
