package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.request.ReqUsrCheckAuthority;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class ServiceUser {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    private String baseAddress;

    public ServiceUser() {
        super();
        this.baseAddress = "user";
    }

    public String usr_CheckAuthority(String arg8) {
        String url = Utils.getServiceAddress(this.baseAddress, "checkauthority");
        ReqUsrCheckAuthority v1 = new ReqUsrCheckAuthority();
        v1.setUserid(SystemState.getObject("cu_user", User.class).getId());
        v1.setAuthority(arg8);
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }


}
