package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemForPrint {
    protected String barcode;
    protected double discountratio;
    protected double discountsubtotal;
    protected String goodsid;
    protected String goodsname;
    protected String itemtype;
    protected double num;
    protected double price;
    protected String remark;
    protected String specification;
    protected String unitname;

    public FieldSaleItemForPrint() {
    }

    public FieldSaleItemForPrint(String barcode, double discountratio, double discountsubtotal, String goodsid, String goodsname, String itemtype, double num, double price, String remark, String specification, String unitname) {
        this.barcode = barcode;
        this.discountratio = discountratio;
        this.discountsubtotal = discountsubtotal;
        this.goodsid = goodsid;
        this.goodsname = goodsname;
        this.itemtype = itemtype;
        this.num = num;
        this.price = price;
        this.remark = remark;
        this.specification = specification;
        this.unitname = unitname;
    }

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getDiscountratio() {
        return discountratio;
    }

    public void setDiscountratio(double discountratio) {
        this.discountratio = discountratio;
    }

    public double getDiscountsubtotal() {
        return discountsubtotal;
    }

    public void setDiscountsubtotal(double discountsubtotal) {
        this.discountsubtotal = discountsubtotal;
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

    public String getItemtype() {
        return itemtype == null ? "" : itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
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

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpecification() {
        return specification == null ? "" : specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnitname() {
        return unitname == null ? "" : unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
