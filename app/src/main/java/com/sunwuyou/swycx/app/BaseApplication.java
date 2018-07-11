package com.sunwuyou.swycx.app;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.immo.libcomm.utils.TextUtils;
import com.lzy.okgo.OkGo;

/**
 * @author Administrator
 * @content 基础application的封装
 * @date 2018/1/4
 */

public class BaseApplication extends MultiDexApplication {
    private static final BaseApplication instance = new BaseApplication();

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        if (true) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        Utils.init(this);
    }

    @SuppressLint("WrongConstant")
    public String getUniqueCode() {
        if (!TextUtils.isEmptyS(SystemState.getValue("keycode"))) {
            return SystemState.getValue("keycode");
        }
        String str2 = Settings.Secure.getString(getContentResolver(), "android_id");
        Object localObject1 = CTelephoneInfo.getInstance(this);
        ((CTelephoneInfo) localObject1).setCTelephoneInfo();
        Object localObject2 = ((CTelephoneInfo) localObject1).getImeiSIM1();
        String str1 = ((CTelephoneInfo) localObject1).getImeiSIM2();
        localObject1 = localObject2;
        if (TextUtils.isEmptyS((String) localObject2)) {
            localObject1 = "";
        }
        localObject2 = str1;
        if (TextUtils.isEmptyS(str1)) {
            localObject2 = "";
        }
        if (!TextUtils.isEmptyS(str2 + (String) localObject1 + (String) localObject2)) {
            SystemState.setValue("keycode", (str2 + (String) localObject1 + (String) localObject2).toUpperCase());
            return (str2 + (String) localObject1 + (String) localObject2).toUpperCase();
        }
        localObject1 = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        SystemState.setValue("keycode", ((WifiInfo) localObject1).getMacAddress());
        return ((WifiInfo) localObject1).getMacAddress();
    }
}
