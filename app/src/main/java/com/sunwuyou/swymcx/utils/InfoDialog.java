package com.sunwuyou.swymcx.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class InfoDialog {
    public static void showError(Context arg3, String arg4) {
        new AlertDialog.Builder(arg3).setTitle("错误").setMessage(arg4).setPositiveButton("确定", null).show();
    }

    public static void showMessage(Context arg3, String arg4) {
        new AlertDialog.Builder(arg3).setTitle("提示").setMessage(arg4).setPositiveButton("确定", null).show();
    }
}
