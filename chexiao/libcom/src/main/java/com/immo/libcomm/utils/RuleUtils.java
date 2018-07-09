package com.immo.libcomm.utils;

import java.util.Locale;

/**
 * @author Administrator
 * @content 正则表达式
 * @date 2017/11/11
 */

public class RuleUtils {
    public  static String Phone;

    public final static String PS="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    public final static String USERNAME="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{4,20}$";
    //网址的正则表达式
    public final static String Url ="((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|" +
            "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*" +
            "(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
    public final static String POSTCODE="^[1-9][0-9]{5}$";
    public final static String Email="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public final static String IDCARD="^(\\\\d{6})(19|20)(\\\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\\\d{3})(\\\\d|X|x)?$";
    public static String getCoutry(){
        String locale = Locale.getDefault().toString().toUpperCase();
        if (locale.contains("CN")){
            Phone="^((1[3,4,5,7,6,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        }else {
            Phone="^[0-9]*$";
        }
        return Phone;
    }



}
