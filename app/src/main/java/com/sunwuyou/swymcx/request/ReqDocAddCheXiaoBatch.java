package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocAddCheXiaoBatch {
    private String batch;
    private String goodsid;
    private boolean isout;
    private double num;
    private double price;
    private String productiondate;
    private String unitid;

    public ReqDocAddCheXiaoBatch() {
        super();
    }

    public String getBatch() {
        return this.batch;
    }

    public String getGoodsid() {
        return this.goodsid;
    }

    public double getNum() {
        return this.num;
    }

    public double getPrice() {
        return this.price;
    }

    public String getProductiondate() {
        return this.productiondate;
    }

    public String getUnitid() {
        return this.unitid;
    }

    public boolean isIsout() {
        return this.isout;
    }

    public void setBatch(String arg1) {
        this.batch = arg1;
    }

    public void setGoodsid(String arg1) {
        this.goodsid = arg1;
    }

    public void setIsout(boolean arg1) {
        this.isout = arg1;
    }

    public void setNum(double arg1) {
        this.num = arg1;
    }

    public void setPrice(double price) {
        this.price = Utils.normalizeDouble(price);
    }

    public void setProductiondate(String arg1) {
        this.productiondate = arg1;
    }

    public void setUnitid(String arg1) {
        this.unitid = arg1;
    }
}
