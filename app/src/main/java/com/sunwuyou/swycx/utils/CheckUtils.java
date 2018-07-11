package com.sunwuyou.swycx.utils;


import android.widget.TextView;

import com.sunwuyou.swycx.app.BaseActivity;


/**
 * @author Administrator
 * @content  判断规格
 * @date 2018/2/26
 */

public class CheckUtils {

    /**
     * 检验是否为空
     * @param tv   文本框
     * @param activity  当前的activity
     * @param content   错误提示
     * @return   是否正确
     */

    public static boolean checkFormatNull(TextView tv, BaseActivity activity, String content){
        if (tv.getText().toString().length() == 0) {
            activity.toast(content);
            return false;
        }
        return  true;
    }

    /**
     * 检验格式是否正确
     * @param tv 文本框
     * @param activity 当前的activity
     * @param content 错误提示
     * @param rule 规则
     * @return 是否正确
     */
    public static boolean checkFormatRule(TextView tv, BaseActivity activity, String content,String rule){
        if (!tv.getText().toString().matches(rule)) {
            activity.toast(content);
            return false;
        }
        return  true;
    }
    /**
     * 检验格式是否正确
     * @param tv 文本框
     * @param activity 当前的activity
     * @param content 错误提示
     * @param length 长度
     * @return 是否正确
     */
    public static boolean checkFormatLength(TextView tv, BaseActivity activity, String content,int length){
        if (tv.getText().toString().length() < length) {
            activity.toast(content);
            return false;
        }
        return  true;
    }
}
