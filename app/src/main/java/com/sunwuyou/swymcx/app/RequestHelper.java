package com.sunwuyou.swymcx.app;

import com.sunwuyou.swymcx.utils.PDH;

/**
 * Created by admin
 * 2018/7/11.
 * content
 */

public class RequestHelper {
    public static boolean isSuccess(String str) {
        return !A.isFail(str);
    }

    public static void showError(String arg2) {
        if("无网络连接".equals(arg2)) {
            PDH.showError("无网络连接");
        }
        else if("nodog".equals(arg2)) {
            PDH.showError("加密狗不存在，程序即将退出");
            new Thread() {
                public void run() {
                    long v1 = 2000;
                    try {
                        Thread.sleep(v1);
                    }
                    catch(InterruptedException v0) {
                        v0.printStackTrace();
                    }

                    MyApplication.getInstance().exit();
                }
            }.start();
        }
        else if("forbid".equals(arg2)) {
            PDH.showFail("你的权限不足!");
        }
        else if("fail".equals(arg2)) {
            PDH.showFail("操作失败");
        }
        else if("login".equals(arg2)) {
            PDH.showMessage("请登录");
        }
        else if(arg2.startsWith("<htm")) {
            PDH.showFail("请求失败, 服务器异常.");
        }
        else {
            PDH.showFail("操作失败，" + arg2);
        }
    }
}
