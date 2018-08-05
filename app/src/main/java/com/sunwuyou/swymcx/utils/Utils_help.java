package com.sunwuyou.swymcx.utils;

import com.blankj.utilcode.util.LogUtils;
import com.sunwuyou.swymcx.app.AccountPreference;
import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.app.SystemState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class Utils_help {
    LinkedHashMap<String, String> linMap = new LinkedHashMap<String, String>();
    private AccountPreference ap = new AccountPreference();

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url      发送请求的 URL
     * @param ，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String getServiceInfor(final String url, final LinkedHashMap<String, String> map) {
        // 访问URL 地址
        String res = getServiceInfor(ap.getServerIp(), url, map);
        return res;
    }

    public String getServiceInfor(String ip, String url, LinkedHashMap<String, String> map) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        int number = 1;
        if (SystemState.getUser() != null) {
            map.put("userid", SystemState.getUser().getId());
        }
        if (SystemState.getAccountSet() != null) {
            map.put("accountset", SystemState.getAccountSet().getDatabase());
        }

        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key));
            if (number < map.size()) {
                sb.append("&");
                number++;
            }
        }

        String result = "";
        try {
            // ip地址url进行拼接
            URL realUrl = new URL(Service.getIpUrl(ip) + url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 加上http头信息
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("key", "mchexiaoban");
            conn.setRequestProperty("code", MyApplication.getInstance().getUniqueCode());
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(30 * 1000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            MLog.d(">>>do post url :" + Service.getIpUrl(ip) + url + "->params:" + sb.toString());
            // 发送请求参数
            out.print(sb.toString());
            // flush输出流的缓冲
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            MLog.d(">>>do post res :" + result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

//    public String getInfor(final String url, final LinkedHashMap<String, String> map) {
//        // 访问URL 地址
//        return getInfor(ap.getServerIp(), url, map);
//    }

//    private String getInfor(String ip, String url, LinkedHashMap<String, String> map) {
//
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            // ip地址url进行拼接
//            String u = Service.getIpUrl(ip) + getUrl(map);
//            URL realUrl = new URL(u);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            // 设置通用的请求属性 加上http头信息
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("key", "mchexiaoban");
//            conn.setRequestProperty("code", MyApplication.getInstance().getUniqueCode());
//            conn.setConnectTimeout(10 * 1000);
//            conn.setReadTimeout(30 * 1000);
//            // 发送POST请求必须设置如下两行
////            conn.setDoOutput(true);
////            conn.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            MLog.d(">>>do post url :" + Service.getIpUrl(ip) + url + "->params:" + u);
//            // flush输出流的缓冲
//            out.flush();
//
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            LogUtils.d("OkGo:>>>do post res :" + result);
//        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！" + e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return null;
//    }

//    private String getUrl(LinkedHashMap<String, String> map) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("?");
//        for (Map.Entry<String, String> m : map.entrySet()) {
//            sb.append(m.getKey()).append("=").append(m.getValue()).append("&");
//        }
//        return sb.deleteCharAt(sb.length()-1).toString();
//    }

}
