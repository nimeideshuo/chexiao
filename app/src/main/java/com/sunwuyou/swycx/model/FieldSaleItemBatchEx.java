package com.sunwuyou.swycx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemBatchEx extends FieldSaleItemBatch {
    private String barcode;
    private String bigstocknumber;
    private String goodsname;
    private boolean isusebatch;
    private double ratio;
    private String specification;
    private double stocknumber;
    private String unitname;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBigstocknumber() {
        return bigstocknumber == null ? "" : bigstocknumber;
    }

    public void setBigstocknumber(String bigstocknumber) {
        this.bigstocknumber = bigstocknumber;
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

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getSpecification() {
        return specification == null ? "" : specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getStocknumber() {
        return stocknumber;
    }

    public void setStocknumber(double stocknumber) {
        this.stocknumber = stocknumber;
    }

    public String getUnitname() {
        return unitname == null ? "" : unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
