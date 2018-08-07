package com.immo.libcomm.global;

import android.os.Environment;

import java.io.IOException;

/**
 * @author Administrator
 * @content  全局资源文件配置
 * @date 2018/5/10
 */
public class GlobalContent {
    public static String path;

    static {
        try {
            path = Environment.getExternalStorageDirectory().getCanonicalPath() + "/budejie/";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public static final String goodsImageId="goodsImageId.jpg";
    public static final String memberIconId="memberIconId.jpg";
    public static final String personalCenterImageId="personalCenterImageId.jpg";
    public static final String storeImageId="storeImageId.jpg";
    public static String getPath(String url) {
        return path + url;
    }
}
