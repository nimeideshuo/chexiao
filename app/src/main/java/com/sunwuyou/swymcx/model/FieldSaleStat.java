package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleStat {
    private String barcode;
    private double cancelamount;
    private double cancelbasenum;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getCancelamount() {
        return cancelamount;
    }

    public void setCancelamount(double cancelamount) {
        this.cancelamount = cancelamount;
    }

    public double getCancelbasenum() {
        return cancelbasenum;
    }

    public void setCancelbasenum(double cancelbasenum) {
        this.cancelbasenum = cancelbasenum;
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

    public double getNetsaleamount() {
        return netsaleamount;
    }

    public void setNetsaleamount(double netsaleamount) {
        this.netsaleamount = netsaleamount;
    }

    public double getNetsalebasenum() {
        return netsalebasenum;
    }

    public void setNetsalebasenum(double netsalebasenum) {
        this.netsalebasenum = netsalebasenum;
    }

    public double getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(double saleamount) {
        this.saleamount = saleamount;
    }

    public double getSalebasenum() {
        return salebasenum;
    }

    public void setSalebasenum(double salebasenum) {
        this.salebasenum = salebasenum;
    }

    private String goodsid;
    private String goodsname;
    private double netsaleamount;
    private double netsalebasenum;
    private double saleamount;
    private double salebasenum;
}
