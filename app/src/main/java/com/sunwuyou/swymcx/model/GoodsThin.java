package com.sunwuyou.swymcx.model;

import java.io.Serializable;

public class GoodsThin implements Serializable {


    private static final long serialVersionUID = 1;
    private String barcode;
    private String biginitnumber;
    private String bigstocknumber;
    private String id;
    private double initnumber;
    private boolean isusebatch;
    private String name;
    private String pinyin;
    private String specification;
    private double stocknumber;

    public GoodsThin() {
        super();
    }

    public static long getSerialversionuid() {
        return 1;
    }


    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String arg1) {
        this.barcode = arg1;
    }

    public String getBiginitnumber() {
        return this.biginitnumber;
    }

    public void setBiginitnumber(String arg1) {
        this.biginitnumber = arg1;
    }

    public String getBigstocknumber() {
        return this.bigstocknumber;
    }

    public void setBigstocknumber(String arg1) {
        this.bigstocknumber = arg1;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String arg1) {
        this.id = arg1;
    }

    public double getInitnumber() {
        return this.initnumber;
    }

    public void setInitnumber(double arg1) {
        this.initnumber = arg1;
    }

    public boolean getIsusebatch() {
        return this.isusebatch;
    }

    public void setIsusebatch(boolean arg1) {
        this.isusebatch = arg1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String arg1) {
        this.name = arg1;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String arg1) {
        this.pinyin = arg1;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification(String arg1) {
        this.specification = arg1;
    }

    public double getStocknumber() {
        return this.stocknumber;
    }

    public void setStocknumber(double arg1) {
        this.stocknumber = arg1;
    }


}