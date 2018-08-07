package com.sunwuyou.swymcx.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.model.AccountSetEntity;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.model.Warehouse;


/**
 * Created by admin
 * 2018/7/9.
 * content
 */

public class SystemState {
    public static final String GPS_INTERVAL = "gpsinterval";
    public static final String KEY_CODE = "keycode";
    public static final String P_ACCOUNTSET = "accountset";
    public static final String P_CUR_USER = "cu_user";
    public static final String P_STORE = "department";
    public static final String P_WAREHOUSE = "warehouse";
    public static final String SETTING = "basic_setting";
    public static final String[] customer_select_items;
    public static final String[] customer_select_keys;
    public static final String[] goods_select_items;
    public static final String[] goods_select_keys = {"id", "pinyin", "name", "barcode"};
    public static String random;
    public static SharedPreferences basic_setting = MyApplication.getInstance().getSharedPreferences("basic_setting", Context.MODE_PRIVATE);

    static {
        goods_select_items = new String[]{"编号", "拼音", "名称", "条形码"};
        customer_select_keys = new String[]{"id", "pinyin", "name"};
        customer_select_items = new String[]{"编号", "拼音", "名称"};
        random = "";
    }

    public static void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().build());
    }

    public static AccountSetEntity getAccountSet() {
        return (AccountSetEntity) getObject("accountset", AccountSetEntity.class);
    }

    public static Department getDepartment() {
        return (Department) getObject("department", Department.class);
    }

    public static <T> T getObject(String key, Class<T> classs) {
        return JSON.parseObject(basic_setting.getString(key, ""), classs);
    }

    public static String getValue(String key) {
        return basic_setting.getString(key, "");
    }

    public static Warehouse getWarehouse() {
        return (Warehouse) getObject("warehouse", Warehouse.class);
    }

    public static boolean saveObject(String paramString, Object paramObject) {
        SharedPreferences.Editor localEditor = basic_setting.edit();
        localEditor.putString(paramString, JSON.toJSONString(paramObject));
        return localEditor.commit();
    }

    public static boolean setValue(String key, String value) {
        return basic_setting.edit().putString(key, value).commit();
    }
    // 获取 user 用户
    public static User getUser() {
        return ((User) getObject("cu_user", User.class));
    }
}
