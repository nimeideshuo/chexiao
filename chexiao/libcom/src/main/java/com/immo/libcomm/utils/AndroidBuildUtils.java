package com.immo.libcomm.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by liupiao
 * content:android设备信息工具类
 * data  2018/3/1
 */

public class AndroidBuildUtils {

    /**
     *
     *  String phoneInfo = "Product: " + android.os.Build.PRODUCT;
     phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
     phoneInfo += ", TAGS: " + android.os.Build.TAGS;
     phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
     phoneInfo += ", MODEL: " + android.os.Build.MODEL;
     phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;
     phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
     phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
     phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
     phoneInfo += ", BRAND: " + android.os.Build.BRAND;
     phoneInfo += ", BOARD: " + android.os.Build.BOARD;
     phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
     phoneInfo += ", ID: " + android.os.Build.ID;
     phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
     phoneInfo += ", USER: " + android.os.Build.USER;
     *
     Product: FRD-AL00, CPU_ABI: armeabi-v7a, TAGS: release-keys, VERSION_CODES.BASE: 1, MODEL: FRD-AL00, SDK: 24, VERSION.RELEASE: 7.0, DEVICE: HWFRD, DISPLAY: FRD-AL00C00B396, BRAND: honor, BOARD: FRD, FINGERPRINT: honor/FRD-AL00/HWFRD:7.0/HUAWEIFRD-AL00/C00B396:user/release-keys, ID: HUAWEIFRD-AL00, MANUFACTURER: HUAWEI, USER: test
     */

    /**
     * 判断当前应用是否是debug状态
     */

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 手获取机型号  FRD-AL00
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机品牌
     * honor
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }


    /**
     * 获取SDK版本
     * sdk:24
     *
     * @return
     */
    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 系统版本   7.0
     *
     * @return
     */
    public static String getServiceRELEASE() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前版本号 VersionName:1.1.0
     */

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 获取当前版本号 VersionCode:4
     */

    public static int getAppVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取主板
     *
     * @return
     */
    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 获取ID
     *
     * @return
     */
    public static String getID() {
        return android.os.Build.ID;
    }


    /**
     * 获取系统启动程序版本号
     *
     * @return
     */
    public static String getBootloader() {
        return android.os.Build.BOOTLOADER;
    }

    /**
     * 获取 设备名称  HWFRD
     *
     * @return
     */
    public static String getDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取硬件制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }


}
