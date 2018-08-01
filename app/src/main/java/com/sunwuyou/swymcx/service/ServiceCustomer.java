package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.model.Customer;
import com.sunwuyou.swymcx.request.ReqCommonPara;
import com.sunwuyou.swymcx.request.ReqCuCustomer;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;

/**
 * Created by liupiao on
 * 2018/8/1.
 * content
 */
public class ServiceCustomer {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    private String baseAddress;

    public ServiceCustomer() {
        super();
        this.baseAddress = "customer";
    }

    public String cu_AddCustomer(Customer arg8, String arg9, boolean arg10, boolean arg11, boolean arg12, boolean arg13) {
        String url = Utils.getServiceAddress(this.baseAddress, "addcustomer");
        ReqCuCustomer v1 = new ReqCuCustomer();
        v1.setCustomer(arg8);
        v1.setBuilderId(arg9);
        v1.setIsCustomer(arg10);
        v1.setIsSupplier(arg11);
        v1.setIsIgnorSameName(arg12);
        v1.setIsIgnorSameTel(arg13);
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }

    public String cu_FilterCustomer(String arg8) {
        String url = Utils.getServiceAddress(this.baseAddress, "filtercustomer");
        ReqCommonPara v1 = new ReqCommonPara();
        v1.setStringValue(arg8);
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }


}
