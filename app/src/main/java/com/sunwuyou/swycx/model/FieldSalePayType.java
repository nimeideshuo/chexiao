package com.sunwuyou.swycx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSalePayType {
    private double amount;
    private long fieldsaleid;
    private String paytypeid;
    private String paytypename;
    private long serialid;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getFieldsaleid() {
        return fieldsaleid;
    }

    public void setFieldsaleid(long fieldsaleid) {
        this.fieldsaleid = fieldsaleid;
    }

    public String getPaytypeid() {
        return paytypeid == null ? "" : paytypeid;
    }

    public void setPaytypeid(String paytypeid) {
        this.paytypeid = paytypeid;
    }

    public String getPaytypename() {
        return paytypename == null ? "" : paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }
}
