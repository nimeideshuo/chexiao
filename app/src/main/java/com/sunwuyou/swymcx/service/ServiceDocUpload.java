package com.sunwuyou.swymcx.service;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.app.SystemState;
import com.sunwuyou.swymcx.model.User;
import com.sunwuyou.swymcx.request.CheXiaoDocID;
import com.sunwuyou.swymcx.request.ReqDocAddCheXiao;
import com.sunwuyou.swymcx.request.ReqDocAddOtherSettleUp;
import com.sunwuyou.swymcx.request.ReqDocAddSettleUp;
import com.sunwuyou.swymcx.request.ReqDocAddTransferDoc;
import com.sunwuyou.swymcx.request.ReqDocSubmitCheXiao;
import com.sunwuyou.swymcx.request.ReqDocUploadForCheXiao;
import com.sunwuyou.swymcx.request.ReqDocUploadForSettleUp;
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

    public String doc_UploadSettleUp(ReqDocAddSettleUp arg8, List arg9, List arg10) {
        String url = Utils.getServiceAddress(this.baseAddress, "uploadsettleup");
        ReqDocUploadForSettleUp v1 = new ReqDocUploadForSettleUp();
        v1.setDoc(JSONUtil.object2Json(arg8));
        v1.setItems(JSONUtil.object2Json(arg9));
        v1.setPayTypes(JSONUtil.object2Json(arg10));
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }

    public String doc_UploadOtherSettleUp(ReqDocAddOtherSettleUp arg8, List arg9, List arg10) {
        String url = Utils.getServiceAddress(this.baseAddress, "uploadothersettleup");
        ReqDocUploadForSettleUp v1 = new ReqDocUploadForSettleUp();
        v1.setDoc(JSONUtil.object2Json(arg8));
        v1.setItems(JSONUtil.object2Json(arg9));
        v1.setPayTypes(JSONUtil.object2Json(arg10));
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }

    public String doc_UploadCheXiao(CheXiaoDocID arg8, ReqDocAddCheXiao arg9, List arg10, List arg11, List arg12) {
        String url = Utils.getServiceAddress(this.baseAddress, "uploadchexiao");
        ReqDocUploadForCheXiao v1 = new ReqDocUploadForCheXiao();
        v1.setVisitId(arg8.getVisitId());
        v1.setOutDocId(arg8.getOutDocId());
        v1.setInDocId(arg8.getInDocId());
        v1.setDoc(JSONUtil.object2Json(arg9));
        if (arg10 != null) {
            v1.setItems(JSONUtil.object2Json(arg10));
        } else {
            v1.setItems(null);
        }

        if (arg11 != null) {
            v1.setItemBatchs(JSONUtil.object2Json(arg11));
        } else {
            v1.setItemBatchs(null);
        }

        if (arg12 != null) {
            v1.setPayTypes(JSONUtil.object2Json(arg12));
        } else {
            v1.setPayTypes(null);
        }
        map.put("parameter", JSON.toJSONString(v1));
        return new Utils_help().getServiceInfor(url, map);
    }

    public String doc_SubmitCheXiao(long arg8, long arg10, long arg12, double arg14) {
        String url = Utils.getServiceAddress(this.baseAddress, "submitchexiao");
        ReqDocSubmitCheXiao v2 = new ReqDocSubmitCheXiao();
        v2.setVisitId(arg8);
        v2.setOutDocId(arg10);
        v2.setInDocId(arg12);
        v2.setSaleAmount(arg14);
        v2.setPerformerId(SystemState.getObject("cu_user", User.class).getId());
        map.put("parameter", JSON.toJSONString(v2));
        return new Utils_help().getServiceInfor(url, map);
    }

}
