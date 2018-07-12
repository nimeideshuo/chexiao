package com.sunwuyou.swymcx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleItem {
    protected double cancelbasenum;
    protected String cancelremark;
    protected long fieldsaleid;
    protected String giftgoodsid;
    protected String giftgoodsname;
    protected double giftnum;
    protected String giftremark;
    protected String giftunitid;
    protected String giftunitname;
    protected double givenum;
    protected String giveremark;
    protected String giveunitid;
    protected String goodsid;
    protected boolean isexhibition;
    protected boolean ispromotion;
    protected int promotiontype;
    protected double salenum;
    protected double saleprice;
    protected String saleremark;
    protected String saleunitid;
    protected long serialid;
    protected String warehouseid;

    public double getCancelbasenum() {
        return cancelbasenum;
    }

    public void setCancelbasenum(double cancelbasenum) {
        this.cancelbasenum = cancelbasenum;
    }

    public String getCancelremark() {
        return cancelremark == null ? "" : cancelremark;
    }

    public void setCancelremark(String cancelremark) {
        this.cancelremark = cancelremark;
    }

    public long getFieldsaleid() {
        return fieldsaleid;
    }

    public void setFieldsaleid(long fieldsaleid) {
        this.fieldsaleid = fieldsaleid;
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

    public double getGiftnum() {
        return giftnum;
    }

    public void setGiftnum(double giftnum) {
        this.giftnum = giftnum;
    }

    public String getGiftremark() {
        return giftremark == null ? "" : giftremark;
    }

    public void setGiftremark(String giftremark) {
        this.giftremark = giftremark;
    }

    public String getGiftunitid() {
        return giftunitid == null ? "" : giftunitid;
    }

    public void setGiftunitid(String giftunitid) {
        this.giftunitid = giftunitid;
    }

    public String getGiftunitname() {
        return giftunitname == null ? "" : giftunitname;
    }

    public void setGiftunitname(String giftunitname) {
        this.giftunitname = giftunitname;
    }

    public double getGivenum() {
        return givenum;
    }

    public void setGivenum(double givenum) {
        this.givenum = givenum;
    }

    public String getGiveremark() {
        return giveremark == null ? "" : giveremark;
    }

    public void setGiveremark(String giveremark) {
        this.giveremark = giveremark;
    }

    public String getGiveunitid() {
        return giveunitid == null ? "" : giveunitid;
    }

    public void setGiveunitid(String giveunitid) {
        this.giveunitid = giveunitid;
    }

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public boolean isexhibition() {
        return isexhibition;
    }

    public void setIsexhibition(boolean isexhibition) {
        this.isexhibition = isexhibition;
    }

    public boolean ispromotion() {
        return ispromotion;
    }

    public void setIspromotion(boolean ispromotion) {
        this.ispromotion = ispromotion;
    }

    public int getPromotiontype() {
        return promotiontype;
    }

    public void setPromotiontype(int promotiontype) {
        this.promotiontype = promotiontype;
    }

    public double getSalenum() {
        return salenum;
    }

    public void setSalenum(double salenum) {
        this.salenum = salenum;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public String getSaleremark() {
        return saleremark == null ? "" : saleremark;
    }

    public void setSaleremark(String saleremark) {
        this.saleremark = saleremark;
    }

    public String getSaleunitid() {
        return saleunitid == null ? "" : saleunitid;
    }

    public void setSaleunitid(String saleunitid) {
        this.saleunitid = saleunitid;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
