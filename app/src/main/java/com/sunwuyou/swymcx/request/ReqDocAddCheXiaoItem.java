package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocAddCheXiaoItem {
    private double cancelbasenum;
    private String cancelremark;
    private String giftgoodsid;
    private double giftnum;
    private String giftremark;
    private String giftunitid;
    private double givenum;
    private String giveremark;
    private String giveunitid;
    private String goodsid;
    private boolean isexhibition;
    private boolean ispromotion;
    private int promotiontype;
    private double salenum;
    private double saleprice;
    private String saleremark;
    private String saleunitid;
    private String warehouseid;

    public ReqDocAddCheXiaoItem() {
        super();
    }

    public ReqDocAddCheXiaoItem(String arg5, String arg6, double arg7, double arg9, String arg11, double arg12, String arg14, double arg15, String arg17, String arg18, String arg19, String arg20, boolean arg21, int arg22, String arg23, String arg24, double arg25) {
        super();
        this.goodsid = arg5;
        this.warehouseid = arg6;
        this.salenum = arg7;
        this.saleprice = Utils.normalizeDouble(arg9);
        this.saleunitid = arg11;
        this.givenum = arg12;
        this.giveunitid = arg14;
        this.cancelbasenum = arg15;
        this.saleremark = arg17;
        this.giftremark = arg18;
        this.cancelremark = arg19;
        this.giveremark = arg20;
        this.ispromotion = arg21;
        this.promotiontype = arg22;
        this.giftgoodsid = arg23;
        this.giftunitid = arg24;
        this.giftnum = arg25;
    }

    public double getCancelBaseNum() {
        return this.cancelbasenum;
    }

    public double getCancelNum() {
        return this.cancelbasenum;
    }

    public String getCancelremark() {
        return this.cancelremark;
    }

    public String getGiftGoodsId() {
        return this.giftgoodsid;
    }

    public double getGiftNum() {
        return this.giftnum;
    }

    public String getGiftUnitId() {
        return this.giftunitid;
    }

    public String getGiftremark() {
        return this.giftremark;
    }

    public double getGiveNum() {
        return this.givenum;
    }

    public String getGiveUnitId() {
        return this.giveunitid;
    }

    public String getGiveremark() {
        return this.giveremark;
    }

    public String getGoodsId() {
        return this.goodsid;
    }

    public boolean getIsPromotion() {
        return this.ispromotion;
    }

    public int getPromotionType() {
        return this.promotiontype;
    }

    public double getSaleNum() {
        return this.salenum;
    }

    public double getSalePrice() {
        return this.saleprice;
    }

    public String getSaleUnitId() {
        return this.saleunitid;
    }

    public String getSaleremark() {
        return this.saleremark;
    }

    public String getWarehouseId() {
        return this.warehouseid;
    }

    public boolean isIsexhibition() {
        return this.isexhibition;
    }

    public void setCancelBaseNum(double arg1) {
        this.cancelbasenum = arg1;
    }

    public void setCancelNum(double arg1) {
        this.cancelbasenum = arg1;
    }

    public void setCancelremark(String arg1) {
        this.cancelremark = arg1;
    }

    public void setGiftGoodsId(String arg1) {
        this.giftgoodsid = arg1;
    }

    public void setGiftNum(double arg1) {
        this.giftnum = arg1;
    }

    public void setGiftUnitId(String arg1) {
        this.giftunitid = arg1;
    }

    public void setGiftremark(String arg1) {
        this.giftremark = arg1;
    }

    public void setGiveNum(double arg1) {
        this.givenum = arg1;
    }

    public void setGiveUnitId(String arg1) {
        this.giveunitid = arg1;
    }

    public void setGiveremark(String arg1) {
        this.giveremark = arg1;
    }

    public void setGoodsId(String arg1) {
        this.goodsid = arg1;
    }

    public void setIsPromotion(boolean arg1) {
        this.ispromotion = arg1;
    }

    public void setIsexhibition(boolean arg1) {
        this.isexhibition = arg1;
    }

    public void setPromotionType(int arg1) {
        this.promotiontype = arg1;
    }

    public void setSaleNum(double arg1) {
        this.salenum = arg1;
    }

    public void setSalePrice(double arg3) {
        this.saleprice = Utils.normalizeDouble(arg3);
    }

    public void setSaleUnitId(String arg1) {
        this.saleunitid = arg1;
    }

    public void setSaleremark(String arg1) {
        this.saleremark = arg1;
    }

    public void setWarehouseId(String arg1) {
        this.warehouseid = arg1;
    }
}
