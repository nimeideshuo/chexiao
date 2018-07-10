package swymcx.sunwuyou.com.app;

import android.content.SharedPreferences;
import android.os.StrictMode;

import com.alibaba.fastjson.JSON;
import com.immo.libcomm.base.BaseApplication;

import org.json.JSONObject;

import swymcx.sunwuyou.com.model.AccountSetEntity;
import swymcx.sunwuyou.com.model.Department;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */

public class SystemState {
    public static final String GPS_INTERVAL = "gpsinterval";
    public static final String KEY_CODE = "keycode";
    public static final String P_ACCOUNTSET = "accountset";
    public static final String P_CUR_USER = "cu_user";
    public static final String P_STORE = "department";
    public static final String P_WAREHOUSE = "warehouse";
    public static final String SETTING = "basic_setting";
    public static SharedPreferences basic_setting = BaseApplication.getInstance().getSharedPreferences("basic_setting", 0);
    public static final String[] customer_select_items;
    public static final String[] customer_select_keys;
    public static final String[] goods_select_items;
    public static final String[] goods_select_keys = { "id", "pinyin", "name", "barcode" };
    public static String random;

    static
    {
        goods_select_items = new String[] { "编号", "拼音", "名称", "条形码" };
        customer_select_keys = new String[] { "id", "pinyin", "name" };
        customer_select_items = new String[] { "编号", "拼音", "名称" };
        random = "";
    }

    public static void enableStrictMode()
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().build());
    }

    public static AccountSetEntity getAccountSet()
    {
        return (AccountSetEntity)getObject("accountset", AccountSetEntity.class);
    }

    public static Department getDepartment()
    {
        return (Department)getObject("department", Department.class);
    }

    public static <T> T getObject(String paramString, Class<T> paramClass)
    {
        return JSON.parseObject(basic_setting.getString(paramString, ""), paramClass);
    }

    public static String getValue(String paramString)
    {
        return basic_setting.getString(paramString, "");
    }

//    public static Warehouse getWarehouse()
//    {
//        return (Warehouse)getObject("warehouse", Warehouse.class);
//    }

    public static boolean saveObject(String paramString, Object paramObject)
    {
        SharedPreferences.Editor localEditor = basic_setting.edit();
        localEditor.putString(paramString, JSON.toJSONString(paramObject));
        return localEditor.commit();
    }

    public static boolean setValue(String paramString1, String paramString2)
    {
        return basic_setting.edit().putString(paramString1, paramString2).commit();
    }
}
