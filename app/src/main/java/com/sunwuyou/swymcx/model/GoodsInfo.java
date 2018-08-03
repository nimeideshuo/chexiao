package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class GoodsInfo extends Goods {
    private double price;
    private String unitname;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnitname() {
        return unitname == null ? "" : unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
