package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class ServerDoc implements Serializable {
    private String custmername;
    private String customerid;
    private long docid;
    private String docshowid;
    private String doctime;
    private String doctype;
    private String doctypename;
    private double leftamount;
    private double receivableamount;
    private double receivedamount;

    public String getCustmername() {
        return custmername == null ? "" : custmername;
    }

    public void setCustmername(String custmername) {
        this.custmername = custmername;
    }

    public String getCustomerid() {
        return customerid == null ? "" : customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public long getDocid() {
        return docid;
    }

    public void setDocid(long docid) {
        this.docid = docid;
    }

    public String getDocshowid() {
        return docshowid == null ? "" : docshowid;
    }

    public void setDocshowid(String docshowid) {
        this.docshowid = docshowid;
    }

    public String getDoctime() {
        return doctime == null ? "" : doctime;
    }

    public void setDoctime(String doctime) {
        this.doctime = doctime;
    }

    public String getDoctype() {
        return doctype == null ? "" : doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDoctypename() {
        return doctypename == null ? "" : doctypename;
    }

    public void setDoctypename(String doctypename) {
        this.doctypename = doctypename;
    }

    public double getLeftamount() {
        return leftamount;
    }

    public void setLeftamount(double leftamount) {
        this.leftamount = leftamount;
    }

    public double getReceivableamount() {
        return receivableamount;
    }

    public void setReceivableamount(double receivableamount) {
        this.receivableamount = receivableamount;
    }

    public double getReceivedamount() {
        return receivedamount;
    }

    public void setReceivedamount(double receivedamount) {
        this.receivedamount = receivedamount;
    }

}
