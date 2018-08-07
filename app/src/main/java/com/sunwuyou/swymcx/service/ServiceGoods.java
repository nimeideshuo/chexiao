package com.sunwuyou.swymcx.service;

import com.sunwuyou.swymcx.request.ReqStrGetGoodsPrice;
import com.sunwuyou.swymcx.utils.JSONUtil;
import com.sunwuyou.swymcx.utils.Utils;
import com.sunwuyou.swymcx.utils.Utils_help;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class ServiceGoods {
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    private String baseAddress;

    public ServiceGoods() {
        super();
        this.baseAddress = "goods";
    }

    public String gds_GetGoodsBatch(String goodsid, String warehouseid) {
        String url = Utils.getServiceAddress(this.baseAddress, "getgoodsbatch");
        map.put("goodsid", goodsid);
        map.put("warehouseid", warehouseid);
        String infor = new Utils_help().getServiceInfor(url, map);
        return infor == null ? "错误?" : infor;
    }

    public String gds_GetGoodsWarehouses(String goodsid, boolean isgetbatch) {
        String url = Utils.getServiceAddress(this.baseAddress, "getgoodswarehouses");
        map.put("goodsid", goodsid);
        map.put("isgetbatch", String.valueOf(isgetbatch));
        String infor = new Utils_help().getServiceInfor(url, map);
        return infor == null ? "错误?" : infor;
    }

    public String gds_GetMultiGoodsPrice(List<ReqStrGetGoodsPrice> arg7, boolean ischeckwarehouse, boolean isgetbatch) {
        String url = Utils.getServiceAddress(this.baseAddress, "getmultigoodsprice");
        map.put("parameter", JSONUtil.object2Json(arg7));
        map.put("ischeckwarehouse", String.valueOf(ischeckwarehouse));
        map.put("isgetbatch", String.valueOf(isgetbatch));
        String infor = new Utils_help().getServiceInfor(url, map);
        return infor == null ? "错误?" : infor;

    }
}
