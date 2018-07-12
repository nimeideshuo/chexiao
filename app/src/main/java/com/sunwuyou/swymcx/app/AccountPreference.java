package com.sunwuyou.swymcx.app;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.utils.JSONUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/11.
 * content
 */

public class AccountPreference {
    public static final String AllowChangeSalePrice = "AllowChangeSalePrice";
    public static final String BASIC_DATA_UPDATETIME = "basic_data_updatitme";
    public static final String BIZINFO = "bizinfo";
    public static final String CLEARLASTZERO = "clearlastzero";
    public static final String CUSTOMER_CHECK_SELECT = "customer_check_select";
    public static final String CUSTOMER_DATA_UPDATETIME = "customer_data_updateime";
    public static final String GOODS_CHECK_SELECT = "goods_check_select";
    public static final String GOODS_RESULT_SELECT = "goods_result_select";
    public static final String GOODS_SELECT_MORE = "goods_select_more";
    public static final String ITEM_ORDER = "item_order";
    public static final String MAX_DBRVERSION = "max_rversion";
    public static final String MinusTuiHuo = "minustuihuo";
    public static final String NET_SETTING = "net_setting";
    public static final String PRINTERMODEL_DEFAULT = "printermodel_default";
    public static final String PrintBarCode = "printbarcode";
    public static final String ViewKCStockBrowse = "ViewKCStockBrowse";
    public static final String WORK_DEPARTMENT = "wrok_department";
    private final String BIZPARAMETER_INFO = "bizparameter_info";
    private final String SETTING = "se_do";
    private SharedPreferences bizparamiterPreferences = MyApplication.getInstance().getSharedPreferences("bizparameter_info", 0);
    private SharedPreferences setPreferences = MyApplication.getInstance().getSharedPreferences("se_do", 0);

    public List<HashMap<String, String>> getBizInfoMap() {
        return JSONUtil.parse2ListMap(this.bizparamiterPreferences.getString("bizinfo", "[]"));
    }

//    public BTPrinter getPrinter() {
//        Object localObject2 = null;
//        String str1 = this.setPreferences.getString("printername", null);
//        String str2 = this.setPreferences.getString("printeradress", null);
//        Object localObject1 = localObject2;
//        if (str1 != null) {
//            localObject1 = localObject2;
//            if (str2 != null) {
//                localObject1 = new BTPrinter(str1, str2);
//            }
//        }
//        return localObject1;
//    }

    public String getServerIp() {
        return getValue("ip");
    }

    public <T> T getValue(String json, Class<T> tClass) {
        return JSON.parseObject(json,tClass);
    }

    public String getValue(String paramString) {
        return this.setPreferences.getString(paramString, "");
    }

    public String getValue(String paramString1, String paramString2) {
        return this.setPreferences.getString(paramString1, paramString2);
    }

    public boolean saveBizInfo(String paramString) {
        SharedPreferences.Editor localEditor = this.bizparamiterPreferences.edit();
        localEditor.clear();
        localEditor.putString("bizinfo", paramString);
        return localEditor.commit();
    }

//    public boolean savePrinter(BTPrinter paramBTPrinter) {
//        return this.setPreferences.edit().putString("printername", paramBTPrinter.getName()).putString("printeradress", paramBTPrinter.getAddress()).commit();
//    }

    public boolean setServerIp(String paramString) {
        return setValue("ip", paramString);
    }

    public boolean setValue(String paramString, Object paramObject) {
        return this.setPreferences.edit().putString(paramString, JSON.toJSONString(paramObject)).commit();
    }

    public boolean setValue(String paramString1, String paramString2) {
        return this.setPreferences.edit().putString(paramString1, paramString2).commit();
    }
}
