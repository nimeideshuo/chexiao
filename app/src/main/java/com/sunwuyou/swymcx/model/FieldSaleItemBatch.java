package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemBatch {
    private String batch;
    private long fieldsaleid;
    private String goodsid;
    private boolean isout;
    private double num;
    private double price;
    private String productiondate;
    private long serialid;
    private String unitid;

    public String getBatch() {
        return batch == null ? "" : batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public long getFieldsaleid() {
        return fieldsaleid;
    }

    public void setFieldsaleid(long fieldsaleid) {
        this.fieldsaleid = fieldsaleid;
    }

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public boolean getIsout() {
        return isout;
    }

    public void setIsout(boolean isout) {
        this.isout = isout;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductiondate() {
        return productiondate == null ? "" : productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    public String getUnitid() {
        return unitid == null ? "" : unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }
}
