package com.sunwuyou.swymcx.model;


/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemSource extends FieldSaleItem {
    private String barcode;
    private String giveunitname;
    private String goodsname;
    private boolean isusebatch;
    private String saleunitname;
    private String specification;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGiveunitname() {
        return giveunitname == null ? "" : giveunitname;
    }

    public void setGiveunitname(String giveunitname) {
        this.giveunitname = giveunitname;
    }

    public String getGoodsname() {
        return goodsname == null ? "" : goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public boolean isusebatch() {
        return isusebatch;
    }

    public void setIsusebatch(boolean isusebatch) {
        this.isusebatch = isusebatch;
    }

    public String getSaleunitname() {
        return saleunitname == null ? "" : saleunitname;
    }

    public void setSaleunitname(String saleunitname) {
        this.saleunitname = saleunitname;
    }

    public String getSpecification() {
        return specification == null ? "" : specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

}
