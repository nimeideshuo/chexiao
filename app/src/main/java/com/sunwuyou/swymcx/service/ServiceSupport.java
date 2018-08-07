package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.request.ReqCommonPara;
import com.sunwuyou.swymcx.response.RespCustomerGoodsAndDocPages;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.TextUtils;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;

/**
 * Created by admin
 * 2018/7/21.
 * content
 */

public class ServiceSupport {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    private String baseAddress = "support";

    public ServiceSupport() {
        super();
    }

    public String QueryVisitLineCustomers(String arg8) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryvisitlinecustomers");
        ReqCommonPara v1 = new ReqCommonPara();
        v1.setStringValue(arg8);
        map.put("parameter", JSON.toJSONString(v1));
        String infor = new Utils_help().getServiceInfor(url, map);
        if (RequestHelper.isSuccess(infor)) {
            return JSONUtil.object2Json(v1);
        }
        return "";
    }

    public String QueryRegionCustomers(String arg8) {
        String url = Utils.getServiceAddress(this.baseAddress, "queryregioncustomers");
        ReqCommonPara v1 = new ReqCommonPara();
        v1.setStringValue(arg8);
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }

}
