package com.sunwuyou.swymcx.model;

import java.io.Serializable;

public class CustomerFieldSaleGoods
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String barcode;
    private String customerid;
    private String goodsid;
    private String goodsname;
    private String goodsthirdclassid;
    private boolean ispass;
    private boolean issale;
    private double price;
    private String unitid;
    private String unitname;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCustomerid() {
        return customerid == null ? "" : customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname == null ? "" : goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsthirdclassid() {
        return goodsthirdclassid == null ? "" : goodsthirdclassid;
    }

    public void setGoodsthirdclassid(String goodsthirdclassid) {
        this.goodsthirdclassid = goodsthirdclassid;
    }

    public boolean ispass() {
        return ispass;
    }

    public void setIspass(boolean ispass) {
        this.ispass = ispass;
    }

    public boolean issale() {
        return issale;
    }

    public void setIssale(boolean issale) {
        this.issale = issale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
