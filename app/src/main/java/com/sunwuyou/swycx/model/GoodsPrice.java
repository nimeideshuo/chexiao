package com.sunwuyou.swycx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class GoodsPrice implements Serializable {
    private String goodsid;
    private double price;
    private String pricesystemid;
    private String pricesystemname;
    private String unitid;
    private String unitname;

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPricesystemid() {
        return pricesystemid == null ? "" : pricesystemid;
    }

    public void setPricesystemid(String pricesystemid) {
        this.pricesystemid = pricesystemid;
    }

    public String getPricesystemname() {
        return pricesystemname == null ? "" : pricesystemname;
    }

    public void setPricesystemname(String pricesystemname) {
        this.pricesystemname = pricesystemname;
    }

    public String getUnitid() {
        return unitid == null ? "" : unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname == null ? "" : unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
