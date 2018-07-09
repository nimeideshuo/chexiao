package swymcx.sunwuyou.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.immo.libcomm.utils.SPUtils;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */

public class NetUtils {
    public static boolean isConnected(Context context) {
        return (context != null) && ((isWifiConnected(context)) || ((isMobileConnected(context)) && (!"0".equals(SPUtils.get(context, "net_setting", "0")))));
    }

    @SuppressLint("WrongConstant")
    public static boolean isMobileConnected(Context context) {
        NetworkInfo con;
        if (context != null) {
            con = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0);
            return con.isAvailable();
        }
        return false;
    }

    public static boolean isNetConnect(Context context) {
        if (context != null) {
            @SuppressLint("WrongConstant") NetworkInfo con = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (con != null) {
                return con.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            @SuppressLint("WrongConstant") NetworkInfo con = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1);
            if (con != null) {
                return con.isAvailable();
            }
        }
        return false;
    }
}
