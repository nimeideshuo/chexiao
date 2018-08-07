package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemThin {
    protected String barcode;
    protected double cancelbasenum;
    protected long fieldsaleid;
    protected double giftbasenum;
    protected String giftgoodsid;
    protected String giftgoodsname;
    protected double givebasenum;
    protected String goodsid;
    protected String goodsname;
    protected boolean ispromotion;
    protected double salebasenum;
    protected long serialid;
    protected String specification;
    protected double subtotal;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getCancelbasenum() {
        return cancelbasenum;
    }

    public void setCancelbasenum(double cancelbasenum) {
        this.cancelbasenum = cancelbasenum;
    }

    public long getFieldsaleid() {
        return fieldsaleid;
    }

    public void setFieldsaleid(long fieldsaleid) {
        this.fieldsaleid = fieldsaleid;
    }

    public double getGiftbasenum() {
        return giftbasenum;
    }

    public void setGiftbasenum(double giftbasenum) {
        this.giftbasenum = giftbasenum;
    }

    public String getGiftgoodsid() {
        return giftgoodsid == null ? "" : giftgoodsid;
    }

    public void setGiftgoodsid(String giftgoodsid) {
        this.giftgoodsid = giftgoodsid;
    }

    public String getGiftgoodsname() {
        return giftgoodsname == null ? "" : giftgoodsname;
    }

    public void setGiftgoodsname(String giftgoodsname) {
        this.giftgoodsname = giftgoodsname;
    }

    public double getGivebasenum() {
        return givebasenum;
    }

    public void setGivebasenum(double givebasenum) {
        this.givebasenum = givebasenum;
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

    public boolean getIspromotion() {
        return ispromotion;
    }

    public void setIspromotion(boolean ispromotion) {
        this.ispromotion = ispromotion;
    }

    public double getSalebasenum() {
        return salebasenum;
    }

    public void setSalebasenum(double salebasenum) {
        this.salebasenum = salebasenum;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    public String getSpecification() {
        return specification == null ? "" : specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
