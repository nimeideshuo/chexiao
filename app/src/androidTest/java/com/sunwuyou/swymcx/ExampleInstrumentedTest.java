package com.sunwuyou.swymcx;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String json="[{\"tablename\":\"log_deleterecord\",\"pages\":1},{\"tablename\":\"sz_department\",\"pages\":1},{\"tablename\":\"sz_warehouse\",\"pages\":1},{\"tablename\":\"sz_paytype\",\"pages\":1},{\"tablename\":\"cu_customer\",\"pages\":1},{\"tablename\":\"cu_allcustomer\",\"pages\":1},{\"tablename\":\"cu_customertype\",\"pages\":1},{\"tablename\":\"sz_region\",\"pages\":1},{\"tablename\":\"sz_visitline\",\"pages\":0},{\"tablename\":\"sz_goods\",\"pages\":1},{\"tablename\":\"sz_goodsunit\",\"pages\":1},{\"tablename\":\"sz_goodsclass\",\"pages\":1},{\"tablename\":\"sz_pricesystem\",\"pages\":1},{\"tablename\":\"sz_unit\",\"pages\":1},{\"tablename\":\"cu_customerfieldsalegoods\",\"pages\":1},{\"tablename\":\"sz_goodsprice\",\"pages\":1},{\"tablename\":\"sz_goodsimage\",\"pages\":0},{\"tablename\":\"rversion\",\"pages\":156566},{\"tablename\":\"pagesize\",\"pages\":1000}]";
        List<ReqSynUpdateInfo> reqSynUpdateInfos = JSON.parseArray(json, ReqSynUpdateInfo.class);
//        List<T> list = new ArrayList<T>();
//        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(content);
//
//        for (Object object :jsonArray) {
//            list.add(JSON.parseObject(object.toString(),paramClass));
//        }


        LogUtils.e(reqSynUpdateInfos.toString());



    }
}
