package com.sunwuyou.swymcx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleSum {
    private double cancelamount;
    private double cancelnum;
    private double givenum;
    private boolean ispromotion;

    public double getCancelamount() {
        return cancelamount;
    }

    public void setCancelamount(double cancelamount) {
        this.cancelamount = cancelamount;
    }

    public double getCancelnum() {
        return cancelnum;
    }

    public void setCancelnum(double cancelnum) {
        this.cancelnum = cancelnum;
    }

    public double getGivenum() {
        return givenum;
    }

    public void setGivenum(double givenum) {
        this.givenum = givenum;
    }

    public boolean getIsPromotion() {
        return ispromotion;
    }

    public void setIsPromotion(boolean ispromotion) {
        this.ispromotion = ispromotion;
    }

    public double getPromotionnum() {
        return promotionnum;
    }

    public void setPromotionnum(double promotionnum) {
        this.promotionnum = promotionnum;
    }

    public double getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(double saleamount) {
        this.saleamount = saleamount;
    }

    public double getSalenum() {
        return salenum;
    }

    public void setSalenum(double salenum) {
        this.salenum = salenum;
    }

    private double promotionnum;
    private double saleamount;
    private double salenum;
}
