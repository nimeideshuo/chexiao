package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.request.ReqDocAddTransferDoc;
import com.sunwuyou.swymcx.request.ReqDocUploadForTransferDoc;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class ServiceDocUpload {
    private final int itemSize;
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    private String baseAddress;

    public ServiceDocUpload() {
        super();
        this.baseAddress = "docupload";
        this.itemSize = 50;
    }

    public String doc_UploadTransferDoc(long arg8, ReqDocAddTransferDoc arg10, List arg11, boolean arg12) {
        String url = Utils.getServiceAddress(this.baseAddress, "uploadtransferdoc");
        ReqDocUploadForTransferDoc v1 = new ReqDocUploadForTransferDoc();
        v1.setDocId(arg8);
        v1.setDoc(JSONUtil.object2Json(arg10));
        v1.setItems(JSONUtil.object2Json(arg11));
        v1.setIsSubmit(arg12);
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }


}
