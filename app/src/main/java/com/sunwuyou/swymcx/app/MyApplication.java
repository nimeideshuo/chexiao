package com.sunwuyou.swymcx.app;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.dou361.dialogui.DialogUIUtils;
import com.immo.libcomm.utils.TextUtils;
import com.lzy.okgo.OkGo;

/**
 * @author Administrator
 * @content 基础application的封装
 * @date 2018/1/4
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        OkGo.getInstance().init(this);
        Utils.init(this);
        DialogUIUtils.init(this);
    }


    public String getUniqueCode() {
        String keycode = SystemState.getValue("keycode");
        if ((TextUtils.isEmptyS(keycode))) {
            return keycode;
        }
        @SuppressLint("HardwareIds") String str1 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        CTelephoneInfo localCTelephoneInfo = CTelephoneInfo.getInstance(this);
        localCTelephoneInfo.setCTelephoneInfo();
        String str2 = localCTelephoneInfo.getImeiSIM1();
        String str3 = localCTelephoneInfo.getImeiSIM2();
        if (!TextUtils.isEmptyS(str2)) {
            str2 = "";
        }
        if (!TextUtils.isEmptyS(str3)) {
            str3 = "";
        }
        if ((TextUtils.isEmptyS(str1 + str2 + str3))) {
            // 将所有字母 全部转换成大写 toUpperCase
            keycode = (str1 + str2 + str3).toUpperCase();
            SystemState.setValue("keycode", keycode);
            return keycode;
        }
        @SuppressLint("WrongConstant") WifiInfo localWifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        keycode = localWifiInfo.getMacAddress();
        SystemState.setValue("keycode", keycode);
        return localWifiInfo.getMacAddress();
    }

    public String getMac() {
        return ((WifiManager) getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }
}
