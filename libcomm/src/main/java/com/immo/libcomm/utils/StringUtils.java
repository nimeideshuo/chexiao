package com.immo.libcomm.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @content 格式规范
 * @date 2017/11/27
 */

public class StringUtils {

    public final static String GDA = "";

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        return s.trim().length() == 0;
    }

    /**
     * 判断数组中是否含有某个值
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useLoop(List<Integer> arr, int targetValue) {
        for (int s : arr) {
            if (s == targetValue)
                return true;
        }
        return false;
    }

    //隐藏手机号
    public static String phoneFormat(String s) {
        if (s.length() != 11) {
            return s;
        } else {
            return s.substring(0, 3) + "****" + s.substring(7, s.length());
        }
    }

    public static DecimalFormat getDecimalFormat() {
        return new DecimalFormat("0.00");
    }

    public static DecimalFormat getDecimalFormatLon() {
        return new DecimalFormat("0.0000000");
    }

    public static DecimalFormat getDecimalFormat2() {
        return new DecimalFormat("0");
    }

    public static String getStr(String str) {
        if (str == null || str.length() == 0 || str.equals("县") || str.equals("市辖区")) {
            str = "";
        }
        return str;
    }

    public static String formatNum(String num) {
        if (num == null) {
            num = "销量0";
        } else if (num.length() < 5) {
            num = "销量" + num;
        } else {
            num = "销量" + Integer.parseInt(num) / 10000 + "万件";
        }
        return num;
    }

    public static String getPrice(float price) {
        if (price == 0) {
            return "免费";
        }
        return "￥" + getDecimalFormat().format(price);
    }

    public static String getBalance(float account_money) {
        return "(余额:¥" + getDecimalFormat().format(account_money) + ")";
    }

    public static String getBalanceNum(float balance) {
        String result = "" + balance;
        if (balance > 10000) {
            BigDecimal b = new BigDecimal(balance = balance / 10000);
            float prife = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            result = prife + "万";
        }
        return result;
    }

    public static String getPriceOrder(float price) {
        BigDecimal b = new BigDecimal(price);
        float prife = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return "￥" + getDecimalFormat().format(prife);
    }

    public static String getPriceOrder02(float price) {
        return getDecimalFormat().format(price);
    }

    public static String getGDA(float gda) {

        return getDecimalFormat().format(gda) + StringUtils.GDA;
    }

    public static int getPages(int num, int rp) {
        int s = 1;
        if (num % rp == 0) {
            s = num / rp;
        } else {
            s = num / rp + 1;
        }
        return s;
    }

    public static String getDistance(double distance) {
        if (distance < 1000) {
            return (int) distance + "m";
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(distance / 1000) + "km";
        }
    }

    /**
     * 姓名中间替代为*
     */
    public static void changeShow(String content, TextView tv) {
        if (!TextUtils.isEmpty(content)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (i >= 3 && i <= content.length() - 5) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            tv.setText(sb.toString());
        }
    }

    public static List<Map<String, Object>> getData(int size) {
        List<Map<String, Object>> listMap = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "测试" + i);
            listMap.add(map);
        }
        return listMap;
    }


}
