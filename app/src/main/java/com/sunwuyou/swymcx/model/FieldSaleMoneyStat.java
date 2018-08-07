package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleMoneyStat {
    private String customerid;
    private String customername;
    private double netsaleamount;
    private double preference;
    private double receivable;
    private double received;

    public String getCustomerid() {
        return customerid == null ? "" : customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername == null ? "" : customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public double getNetsaleamount() {
        return netsaleamount;
    }

    public void setNetsaleamount(double netsaleamount) {
        this.netsaleamount = netsaleamount;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public double getReceivable() {
        return receivable;
    }

    public void setReceivable(double receivable) {
        this.receivable = receivable;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }
}
