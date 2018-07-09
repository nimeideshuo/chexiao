package com.immo.libcomm.http;


import android.os.Environment;

import java.io.File;

/**
 * @author Administrator
 *         content   文件的 默认地址
 *         date 2017/11/29
 */

public class BaseFileUrl {
    public static final String URL = Environment.getExternalStorageDirectory() + File.separator + "yimai1" + File.separator;


    //拼团享优惠
    public final static String shareGoodsDiscount = "share_goods_discount.jpg";

    public static String getUrl(String url) {

        return URL + url;
    }

}
