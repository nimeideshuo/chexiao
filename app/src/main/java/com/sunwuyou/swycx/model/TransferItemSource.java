package com.sunwuyou.swycx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class TransferItemSource {
    private String barcode;
    private String goodsname;
    private boolean isusebatch;
    private String specification;
    private String unitname;
    private String warehousename;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getWarehousename() {
        return warehousename == null ? "" : warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

}
