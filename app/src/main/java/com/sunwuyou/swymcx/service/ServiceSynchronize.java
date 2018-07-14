package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.http.BaseUrl;
import com.sunwuyou.swymcx.http.HttpConnect;
import com.sunwuyou.swymcx.http.HttpListener;
import com.sunwuyou.swymcx.model.Department;
import com.sunwuyou.swymcx.request.ReqSupQueryDepartment;
import com.sunwuyou.swymcx.request.ReqSynUpdateInfo;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ServiceSynchronize {

    public static String tables = "log_deleterecord,sz_unit,sz_department,sz_warehouse,sz_paytype,sz_account,sz_pricesystem,cu_customertype,sz_region,sz_visitline,sz_goods,sz_goodsunit,sz_goodsprice,sz_goodsimage";
    private final int pagesize = 1000;
    private final int pagesizeForID = 2000;
    private String baseAddress = "synchronize";
    private boolean ischexiao = true;
    private long rversion = 0L;


}
