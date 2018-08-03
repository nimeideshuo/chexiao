package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleThin {
    private String buildtime;
    private double cancelamount;
    private String customerid;
    private String customername;
    private long id;
    private boolean isnewcustomer;
    private double preference;
    private double receivedamount;
    private double saleamount;
    private String showid;
    private int status;

    public String getBuildtime() {
        return buildtime == null ? "" : buildtime;
    }

    public void setBuildtime(String buildtime) {
        this.buildtime = buildtime;
    }

    public double getCancelamount() {
        return cancelamount;
    }

    public void setCancelamount(double cancelamount) {
        this.cancelamount = cancelamount;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIsnewcustomer() {
        return isnewcustomer;
    }

    public void setIsnewcustomer(boolean isnewcustomer) {
        this.isnewcustomer = isnewcustomer;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public double getReceivedamount() {
        return receivedamount;
    }

    public void setReceivedamount(double receivedamount) {
        this.receivedamount = receivedamount;
    }

    public double getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(double saleamount) {
        this.saleamount = saleamount;
    }

    public String getShowid() {
        return showid == null ? "" : showid;
    }

    public void setShowid(String showid) {
        this.showid = showid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
