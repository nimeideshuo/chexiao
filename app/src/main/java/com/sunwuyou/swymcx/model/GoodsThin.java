package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class GoodsThin implements Serializable {
    private String barcode;

    public String getBarcode() {
        return barcode == null ? "" : barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBiginitnumber() {
        return biginitnumber == null ? "" : biginitnumber;
    }

    public void setBiginitnumber(String biginitnumber) {
        this.biginitnumber = biginitnumber;
    }

    public String getBigstocknumber() {
        return bigstocknumber == null ? "" : bigstocknumber;
    }

    public void setBigstocknumber(String bigstocknumber) {
        this.bigstocknumber = bigstocknumber;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInitnumber() {
        return initnumber;
    }

    public void setInitnumber(double initnumber) {
        this.initnumber = initnumber;
    }

    public boolean isusebatch() {
        return isusebatch;
    }

    public void setIsusebatch(boolean isusebatch) {
        this.isusebatch = isusebatch;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
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

    private String biginitnumber;
    private String bigstocknumber;
    private String id;
    private double initnumber;
    private boolean isusebatch;
    private String name;
    private String pinyin;
    private String specification;
    private double stocknumber;
}
