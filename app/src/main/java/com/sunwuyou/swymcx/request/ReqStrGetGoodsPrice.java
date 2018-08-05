package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/8/5.
 * content
 */

public class ReqStrGetGoodsPrice {
    private String batch;
    private String customerid;
    private String goodsid;
    private boolean isdiscount;
    private double price;
    private String productiondate;
    private int type;
    private String unitid;
    private String warehouseid;

    public ReqStrGetGoodsPrice() {
        super();
    }

    public String getBatch() {
        return this.batch;
    }

    public String getCustomerid() {
        return this.customerid;
    }

    public String getGoodsid() {
        return this.goodsid;
    }

    public boolean getIsdiscount() {
        return this.isdiscount;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProductiondate() {
        return this.productiondate;
    }

    public int getType() {
        return this.type;
    }

    public String getUnitid() {
        return this.unitid;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setBatch(String arg1) {
        this.batch = arg1;
    }

    public void setCustomerid(String arg1) {
        this.customerid = arg1;
    }

    public void setGoodsid(String arg1) {
        this.goodsid = arg1;
    }

    public void setIsdiscount(boolean arg1) {
        this.isdiscount = arg1;
    }

    public void setPrice(double arg1) {
        this.price = arg1;
    }

    public void setProductiondate(String arg1) {
        this.productiondate = arg1;
    }

    public void setType(int arg1) {
        this.type = arg1;
    }

    public void setUnitid(String arg1) {
        this.unitid = arg1;
    }

    public void setWarehouseid(String arg1) {
        this.warehouseid = arg1;
    }
}
