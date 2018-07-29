package com.sunwuyou.swymcx.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleItemTotal {
    private String barcode;
    private String goodsid;
    private String goodsname;
    private double inbasicnum;
    private boolean isusebatch;
    private List<FieldSaleItemBatchEx> items;
    private double outbasicnum;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public double getInbasicnum() {
        return inbasicnum;
    }

    public void setInbasicnum(double inbasicnum) {
        this.inbasicnum = inbasicnum;
    }

    public boolean getIsusebatch() {
        return isusebatch;
    }

    public void setIsusebatch(boolean isusebatch) {
        this.isusebatch = isusebatch;
    }

    public List<FieldSaleItemBatchEx> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<FieldSaleItemBatchEx> items) {
        this.items = items;
    }

    public double getOutbasicnum() {
        return outbasicnum;
    }

    public void setOutbasicnum(double outbasicnum) {
        this.outbasicnum = outbasicnum;
    }
}
