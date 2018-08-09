package com.sunwuyou.swymcx.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by admin
 * 2018/8/9.
 * content
 */

public class BmobManager {
    private static BmobManager mInstance = null;

    public static BmobManager getInstance() {

        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        //第一：默认初始化
        Bmob.initialize(context, "9f6763619c382ffb8e5f4cef8e582c28");
    }

}
