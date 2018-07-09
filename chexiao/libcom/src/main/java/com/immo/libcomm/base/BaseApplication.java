package com.immo.libcomm.base;

import com.bulong.rudeness.RudenessScreenHelper;
import com.lzy.okgo.OkGo;

import android.support.multidex.MultiDexApplication;

/**
 * @author Administrator
 * @content 基础application的封装
 * @date 2018/1/4
 */

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
        int designWidth = 750;
        new RudenessScreenHelper(this, designWidth).activate();
    }


}
