package com.sunwuyou.swymcx.utils;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin
 * 2018/7/11.
 * content
 */

public class JSONUtil {
    public static boolean isJson(String isJson) {
        return (isJson != null) && (!TextUtils.isEmpty(isJson)) && ((isJson.startsWith("{")) || (isJson.startsWith("[")));
    }

    // json 转 list hashMap
    public static List<HashMap<String, String>> parse2ListMap(String paramString) {
        try {
            List<HashMap<String, String>> list_map = new ArrayList<HashMap<String, String>>();
            JSONArray localJSONArray = new JSONArray(paramString);

            for (int i = 0; i < localJSONArray.length(); i++) {
                list_map.add(parse2Map(localJSONArray.get(i).toString()));
            }
            return list_map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // json 转 hashMap
    public static HashMap<String, String> parse2Map(String paramString) {
        HashMap<String, String> localHashMap = new HashMap<String, String>();
        try {
            JSONObject localJSONObject = new JSONObject(paramString);
            Iterator<String> localIterator = localJSONObject.keys();
            while ((localIterator.hasNext())) {
                String str = (String) localIterator.next();
                localHashMap.put(str, localJSONObject.getString(str));
            }
            return localHashMap;
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> str2list(String content, Class<T> paramClass) {
        if (TextUtils.isEmptyS(content)) {
            return new ArrayList<>();
        }
        try {
            return JSON.parseArray(content, paramClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    public static String object2Json(Object object) {
        return JSON.toJSONString(object);
    }
}
