package com.sunwuyou.swymcx.utils;

import com.alibaba.fastjson.JSON;
import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.request.ReqDocAddTransferDoc;
import com.sunwuyou.swymcx.request.ReqDocAddTransferItem;
import com.sunwuyou.swymcx.response.RespDocAddEntity;
import com.sunwuyou.swymcx.service.ServiceDocUpload;

import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class UpLoadUtils {


    public String uploadTransferDoc(TransferDoc arg17) {
        String v12 = null;
        ServiceDocUpload v1 = new ServiceDocUpload();
        TransferItemDAO v13 = new TransferItemDAO();
        ReqDocAddTransferDoc v4 = new ReqDocAddTransferDoc(arg17);
        int v7 = v13.getCount(arg17.getId());
        int v11 = 50;
        int v14 = v7 / v11;
        int v6 = v7 % v11 > 0 ? 1 : 0;
        int v10 = v14 + v6;
        long v2 = 0;
        for (int i = 0; i < v10; i++) {
            List<ReqDocAddTransferItem> v5 = v13.getTransferItemForUpload(arg17.getId(), v11, i * v11);
            boolean v6_1 = i == v10 - 1;
            v12 = v1.doc_UploadTransferDoc(v2, v4, v5, v6_1);
            if (!RequestHelper.isSuccess(v12)) {
                return v12;
            }
            RespDocAddEntity v8_1 = JSONUtil.readValue(v12, RespDocAddEntity.class);
            v2 = v8_1.getId();
            new TransferDocDAO().submit(arg17.getId());

            if (!v8_1.getIsExcutePosted()) {
                MLog.d("上传成功，但未过账");
                v12 = null;
            }
            if (v8_1.getIsSubmitSuccess()) {
                MLog.d("上传成功，且过账成功");
                v12 = null;
            } else {
                MLog.d("上传成功，但过账失败");
            }
        }
        return v12;
    }
}
