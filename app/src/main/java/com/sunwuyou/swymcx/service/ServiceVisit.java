package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.request.ReqVstAddVisitCustomerJobImage;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ServiceVisit {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

    private String baseAddress;

    public ServiceVisit() {
        super();
        this.baseAddress = "visit";
    }

    public String vst_UploadVisitImage(ReqVstAddVisitCustomerJobImage arg7) {
        String url = Utils.getServiceAddress(this.baseAddress, "uploadvisitimage");
        map.put("parameter", JSON.toJSONString(arg7));
        return new Utils_help().getServiceInfor(url, map);
    }
}
