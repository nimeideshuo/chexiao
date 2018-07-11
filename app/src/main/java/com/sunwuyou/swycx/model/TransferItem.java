package com.sunwuyou.swycx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class TransferItem  implements Serializable {
    private String batch;
    private String goodsid;
    private double num;
    private String productiondate;
    private String remark;
    private long serialid;
    private long transferdocid;
    private String unitid;
    private String warehouseid;

    public String getBatch() {
        return batch == null ? "" : batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getProductiondate() {
        return productiondate == null ? "" : productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    public long getTransferdocid() {
        return transferdocid;
    }

    public void setTransferdocid(long transferdocid) {
        this.transferdocid = transferdocid;
    }

    public String getUnitid() {
        return unitid == null ? "" : unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
