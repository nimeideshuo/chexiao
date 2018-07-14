package com.sunwuyou.swymcx.http;

/**
 * @author YunXing
 * @content 网络回调接口
 * @date 2017/11/10
 */
public interface HttpListener {
    void loadHttp(Object object, String response);
}
