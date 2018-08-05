package com.sunwuyou.swymcx.utils;

import android.util.Base64;

import com.sunwuyou.swymcx.app.RequestHelper;
import com.sunwuyou.swymcx.dao.FieldSaleDAO;
import com.sunwuyou.swymcx.dao.FieldSaleImageDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemBatchDAO;
import com.sunwuyou.swymcx.dao.FieldSaleItemDAO;
import com.sunwuyou.swymcx.dao.FieldSalePayTypeDAO;
import com.sunwuyou.swymcx.dao.OtherSettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpDAO;
import com.sunwuyou.swymcx.dao.SettleUpItemDAO;
import com.sunwuyou.swymcx.dao.SettleUpPayTypeDAO;
import com.sunwuyou.swymcx.dao.TransferDocDAO;
import com.sunwuyou.swymcx.dao.TransferItemDAO;
import com.sunwuyou.swymcx.model.FieldSaleThin;
import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.model.TransferDoc;
import com.sunwuyou.swymcx.request.CheXiaoDocID;
import com.sunwuyou.swymcx.request.ReqDocAddCheXiao;
import com.sunwuyou.swymcx.request.ReqDocAddCheXiaoBatch;
import com.sunwuyou.swymcx.request.ReqDocAddCheXiaoItem;
import com.sunwuyou.swymcx.request.ReqDocAddOtherSettleUp;
import com.sunwuyou.swymcx.request.ReqDocAddSettleUp;
import com.sunwuyou.swymcx.request.ReqDocAddTransferDoc;
import com.sunwuyou.swymcx.request.ReqDocAddTransferItem;
import com.sunwuyou.swymcx.request.ReqDocUpdatePayType;
import com.sunwuyou.swymcx.request.ReqVstAddVisitCustomerJobImage;
import com.sunwuyou.swymcx.request.RespStockProcessReParaEntity;
import com.sunwuyou.swymcx.response.RespDocAddEntity;
import com.sunwuyou.swymcx.service.ServiceDocUpload;
import com.sunwuyou.swymcx.service.ServiceVisit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on
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

    public String uploadCheXiao(FieldSaleThin arg46) {

        String v9_1;
        CheXiaoDocID v4_1 = null;
        String v42 = "";
        ServiceDocUpload v3 = new ServiceDocUpload();
        ServiceVisit v43 = new ServiceVisit();
        FieldSaleDAO v25 = new FieldSaleDAO();
        FieldSaleItemDAO v26 = new FieldSaleItemDAO();
        FieldSaleItemBatchDAO v24 = new FieldSaleItemBatchDAO();
        FieldSaleImageDAO v23 = new FieldSaleImageDAO();
        CheXiaoDocID v4 = new CheXiaoDocID();
        ReqDocAddCheXiao v5 = v25.getFieldSaleForUpload(arg46.getId());
        int v21 = v26.getCount(arg46.getId());
        if (v21 > 0) {
            int v39 = 20;
            int v10 = v21 / v39;
            int v9 = v21 % v39 > 0 ? 1 : 0;
            int v38 = v10 + v9;

            for (int i = 0; i < v38; i++) {
                List<ReqDocAddCheXiaoItem> v6 = v26.getFieldSaleItemForUpload(arg46.getId(), v39, i * v39);
                ArrayList<String> v34 = new ArrayList<>();

                for (int j = 0; j < v6.size(); j++) {
                    ReqDocAddCheXiaoItem v31 = v6.get(j);
                    if (!v34.contains(v31.getGoodsId())) {
                        v34.add(v31.getGoodsId());
                    }
                    if ((v31.getIsPromotion()) && v31.getPromotionType() == 1 && !v34.contains(v31.getGiftGoodsId())) {
                        v34.add(v31.getGiftGoodsId());
                    }
                }
                ArrayList<ReqDocAddCheXiaoBatch> v7 = new ArrayList<>();
                for (int j = 0; j < v34.size(); j++) {
                    double v18 = 0;
                    if (i > 0) {
                        v18 = v26.getGoodsOutSumNum(arg46.getId(), v34.get(j), i * v39);
                    }
                    List<ReqDocAddCheXiaoBatch> v36 = v24.getFieldSaleItemBatchForUpload(arg46.getId(), v34.get(j));
                    for (int k = 0; k < v36.size(); k++) {
                        ReqDocAddCheXiaoBatch v31 = v36.get(k);
                        if (!v31.isIsout() || v18 == 0) {
                            v7.add(v31);
                        } else if (v18 >= v31.getNum()) {
                            v18 -= v31.getNum();
                        } else {
                            v31.setNum(v31.getNum() - v18);
                            v18 = 0;
                            v7.add(v31);
                        }


                    }
                }
                List<ReqDocUpdatePayType> v8 = null;
                if (i == 0) {
                    v8 = new FieldSalePayTypeDAO().getPayTypeForUpload(arg46.getId());
                }
                v42 = v3.doc_UploadCheXiao(v4, v5, v6, v7, v8);
                if (RequestHelper.isSuccess(v42)) {
                    v4_1 = JSONUtil.readValue(v42, CheXiaoDocID.class);
                }
            }
        } else {
            v42 = v3.doc_UploadCheXiao(v4, v5, null, null, null);
            if (RequestHelper.isSuccess(v42)) {
                v4_1 = JSONUtil.readValue(v42, CheXiaoDocID.class);
            }
        }
        List<ReqVstAddVisitCustomerJobImage> v35 = v23.getFieldSaleImageForUpload(arg46.getId());
        if (v35 != null && v35.size() > 0) {
            FileUtils v27 = new FileUtils();
            for (int i = 0; i < v35.size(); i++) {
                ReqVstAddVisitCustomerJobImage v32 = v35.get(i);
                assert v4_1 != null;
                v32.setVisitJobId(v4_1.getVisitItemId());
                File v40 = v27.findPicture(v32.getImagePath());
                if (v40 != null) {
                    int v44 = ((int) v40.length());
                    byte[] v20 = new byte[v44];
                    try {
                        new FileInputStream(v40).read(v20, 0, v44);
                        v32.setImageFile(Base64.encodeToString(v20, 0));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    v42 = v43.vst_UploadVisitImage(v32);
                    if (RequestHelper.isSuccess(v42)) {

                    } else {
                        return v42;
                    }

                }

            }
        }
        v42 = v3.doc_SubmitCheXiao(v4_1.getVisitId(), v4_1.getOutDocId(), v4_1.getInDocId(), v5.getSaleAmount());
        if (!RequestHelper.isSuccess(v42)) {
            MLog.d(v42);
            return v42;
        }
        arg46.setStatus(2);
        v25.submit(arg46.getId());
        if ("success".equals(v42)) {
            MLog.d("上传成功");
            return null;
        }
        RespStockProcessReParaEntity v41 = JSONUtil.readValue(v42, RespStockProcessReParaEntity.class);
        if (!v41.getIsSubmitSuccess()) {
            MLog.d("上传成功，但过账失败");
            v9_1 = "上传成功，但" + v41.getInfo();
        } else {
            MLog.d("上传成功，且过账成功");
            return null;
        }
        return v9_1;
    }

    public String uploadSettleUp(SettleUp arg13) {
        ServiceDocUpload v5 = new ServiceDocUpload();
        SettleUpDAO v6 = new SettleUpDAO();
        String v4 = v5.doc_UploadSettleUp(new ReqDocAddSettleUp(arg13), new SettleUpItemDAO().getSettleUpForUpload(arg13.getId()), new SettleUpPayTypeDAO().getPayTypeForUpload(arg13.getId()));
        if (RequestHelper.isSuccess(v4)) {
            v6.submit(arg13.getId());
            if ("success".equals(v4)) {
                MLog.d("上传成功");
                v4 = null;
            } else {
                Object v3 = JSONUtil.readValue(v4, RespStockProcessReParaEntity.class);
                if (!((RespStockProcessReParaEntity) v3).getIsSubmitSuccess()) {
                    MLog.d("上传成功，但过账失败");
                    v4 = "上传成功，但" + ((RespStockProcessReParaEntity) v3).getInfo();
                } else {
                    MLog.d("上传成功，过账成功");
                    v4 = null;
                }
            }
        } else {
            MLog.d(v4);
        }

        return v4;
    }

    public String uploadOtherSettleUp(SettleUp arg13) {
        ServiceDocUpload v6 = new ServiceDocUpload();
        SettleUpDAO v7 = new SettleUpDAO();
        String v5 = v6.doc_UploadOtherSettleUp(new ReqDocAddOtherSettleUp(arg13), new OtherSettleUpItemDAO().getOtherSettleUpForUpload(arg13.getId()), new SettleUpPayTypeDAO().getPayTypeForUpload(arg13.getId()));
        if (RequestHelper.isSuccess(v5)) {
            v7.submit(arg13.getId());
            if ("success".equals(v5)) {
                MLog.d("上传成功");
                v5 = null;
            } else {
                RespStockProcessReParaEntity v4 = JSONUtil.readValue(v5, RespStockProcessReParaEntity.class);
                if (!v4.getIsSubmitSuccess()) {
                    MLog.d("上传成功，但过账失败");
                    v5 = "上传成功，但" + v4.getInfo();
                } else {
                    MLog.d("上传成功，过账成功");
                    v5 = null;
                }
            }
        } else {
            MLog.d(v5);
        }
        return v5;
    }

}
