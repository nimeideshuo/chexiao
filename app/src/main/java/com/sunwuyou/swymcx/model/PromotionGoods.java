package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class PromotionGoods {
    private String giftgoodsid;
    private String giftgoodsname;
    private double giftnum;
    private String giftunitid;
    private String goodsid;
    private double initnum;
    private double leftnum;
    private double num;
    private double price;
    private String promotionid;
    private String summary;
    private int type;
    private String unitid;

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

    public String getGiftunitid() {
        return giftunitid == null ? "" : giftunitid;
    }

    public void setGiftunitid(String giftunitid) {
        this.giftunitid = giftunitid;
    }

    public String getGoodsid() {
        return goodsid == null ? "" : goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public double getInitnum() {
        return initnum;
    }

    public void setInitnum(double initnum) {
        this.initnum = initnum;
    }

    public double getLeftnum() {
        return leftnum;
    }

    public void setLeftnum(double leftnum) {
        this.leftnum = leftnum;
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

    public String getPromotionid() {
        return promotionid == null ? "" : promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnitid() {
        return unitid == null ? "" : unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }
}
