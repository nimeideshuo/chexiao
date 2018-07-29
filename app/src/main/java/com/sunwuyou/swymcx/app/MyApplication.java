package com.sunwuyou.swymcx.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.dou361.dialogui.DialogUIUtils;
import com.immo.libcomm.utils.TextUtils;
import com.lzy.okgo.OkGo;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.ui.SplashAct;
import com.sunwuyou.swymcx.utils.MLog;

/**
 * @author Administrator
 * @content 基础application的封装
 * @date 2018/1/4
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance = null;
    private ActivityManager activityManager;

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
        //设置服务器IP地址
        String serverIp = new AccountPreference().getServerIp();
        MLog.d("本地服务器地址>>" + serverIp);
        BaseUrl.setUrl(serverIp);
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

    @SuppressLint("WrongConstant")
    public String getMac() {
        return ((WifiManager) getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public void exit() {
        this.activityManager.popAllActivityExceptOne(null);
        System.exit(0);
    }

    public void splash() {
        this.activityManager.popAllActivityExceptOne(null);
        main();
    }

    private void main() {
        Intent localIntent = new Intent(instance, SplashAct.class);
        localIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(localIntent);
    }
}
