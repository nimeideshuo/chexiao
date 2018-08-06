package com.sunwuyou.swymcx.http;

import com.alibaba.fastjson.JSON;
import com.immo.libcomm.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.model.Response;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.utils.JSONUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.immo.libcomm.utils.AndroidBuildUtils.getAppVersionName;
import static com.lzy.okgo.cache.CacheMode.DEFAULT;
import static com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST;
import static com.lzy.okgo.cache.CacheMode.IF_NONE_CACHE_REQUEST;
import static com.lzy.okgo.cache.CacheMode.NO_CACHE;
import static com.lzy.okgo.cache.CacheMode.REQUEST_FAILED_READ_CACHE;

/**
 * @author Administrator
 * @content 网络连接类
 * @date 2017/11/10
 */

public class HttpConnect {
    public final static int PAGECOUNT = 30;
    public static Object obj;
    private static HttpListener mListener;//接口
    private static HttpErrorConnnet httpErrorConnnet;//接口
    private KProgressHUD kProgressHUD;


    public HttpConnect(HttpListener mListener) {
        HttpConnect.mListener = mListener;
    }

    public HttpConnect(HttpErrorConnnet httpErrorConnnet) {
        HttpConnect.httpErrorConnnet = httpErrorConnnet;
    }

    public HttpConnect(HttpListener mListener, HttpErrorConnnet httpErrorConnnet) {
        HttpConnect.mListener = mListener;
        HttpConnect.httpErrorConnnet = httpErrorConnnet;
    }

    public HttpConnect() {
    }

    /**
     * 选用缓存模式
     */
    private CacheMode getCacheMode(int cache) {
        CacheMode cacheMode = NO_CACHE;
        switch (cache) {
            case 0:
                //无缓存模式
                cacheMode = NO_CACHE;
                break;
            case 1:
                //默认缓存模式,遵循304头
                cacheMode = DEFAULT;
                break;
            case 2:
                //请求网络失败后读取缓存
                cacheMode = REQUEST_FAILED_READ_CACHE;
                break;
            case 3:
                //如果缓存不存在才请求网络，否则使用缓存
                cacheMode = IF_NONE_CACHE_REQUEST;
                break;
            case 4:
                //先使用缓存，不管是否存在，仍然请求网络
                cacheMode = FIRST_CACHE_THEN_REQUEST;
                break;
        }
        return cacheMode;
    }

    /**
     * 清理缓存数据
     */
    public void clearCache() {
        CacheManager.getInstance().clear();
    }

    /**
     * 时间
     */
    @SuppressLint("SimpleDateFormat")
    public String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    /**
     * 数据回调
     */
    private StringCallback stringCallBack(final Context context, final Class clazz, final String postHeader, final boolean progress, final int cache) {
        return new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                Log.i("OkGo:返回结果>>", "   " + body);
                if (body.contains("register")) {
                    mListener.loadHttp("", body);
                    return;
                }
                if (!RequestHelper.isSuccess(body)) {
                    Toast.makeText(context, body, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (JSONUtil.isJson(body)) {
                    mListener.loadHttp(JSON.parseObject(body, clazz), body);
                } else {
                    mListener.loadHttp("", body);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Toast.makeText(context, response.code() == 404 ? context.getString(R.string.addr_no_found) : context.getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                if (httpErrorConnnet != null) {
                    httpErrorConnnet.loadHttpError(response.code());
                }
            }

            @Override
            public void onFinish() {
                kProgressHUD.dismiss();
            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy
                    .okgo.request.base.Request> request) {
                super.onStart(request);
                kProgressHUD = new KProgressHUD(context);
                if (progress) {
                    kProgressHUD.show();
                }
            }
        };
    }


    /**
     * 普通的Get请求
     *
     * @param url        请求地址
     * @param context    上下文
     * @param clazz      基类
     * @param postHeader 请求头部
     * @param progress   进度条
     * @param cache      缓存模式
     */

    public void jsonGet(final String url, final Context context, final Class clazz, final String postHeader, final boolean progress, int cache) {
        if (!new NetConnectUtil().isNetworkConnected(context)) {
            Toast.makeText(context, context.getString(R.string.network_failed_please_check), Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>get(url).tag(context).cacheMode(getCacheMode(cache))
                .headers("v", "1.0")
                .headers("version", "android" + getAppVersionName(context))
                .headers("lang", context.getResources().getConfiguration().locale.getCountry())
                .headers("country", context.getResources().getConfiguration().locale.getCountry())
                .execute(stringCallBack(context, clazz, postHeader, progress, cache));
    }

    /**
     * 普通的Post请求
     *
     * @param url        请求地址
     * @param context    上下文
     * @param clazz      基类
     * @param postHeader 请求头部
     * @param progress   进度条
     * @param cache      缓存模式
     */
    public void jsonPost(final String url, final Context context, HashMap<String, String> map, final Class clazz, final String postHeader, final boolean progress, int cache) {
        if (!new NetConnectUtil().isNetworkConnected(context)) {
            Toast.makeText(context, context.getString(R.string.network_failed_please_check), Toast.LENGTH_SHORT).show();
            return;
        }
        String json = getJson(map);
        OkGo.<String>post(url).cacheKey(url).cacheMode(getCacheMode(cache))
                .upString(json)
                .headers("key", "mchexiaoban")
                .headers("code", MyApplication.getInstance().getUniqueCode())
                .execute(stringCallBack(context, clazz, postHeader, progress, cache));
    }

    private String getJson(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        if (SystemState.getAccountSet() != null) {
            sb.append("accountset").append("=").append(SystemState.getAccountSet().getDatabase()).append("&");
        }
        if (SystemState.getUser() != null) {
            sb.append("userid").append("=").append(SystemState.getUser().getId()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
