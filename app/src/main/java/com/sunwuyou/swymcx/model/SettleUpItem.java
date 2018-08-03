package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class SettleUpItem implements Serializable {
    private long docid;
    private String docshowid;
    private String doctime;
    private String doctype;
    private String doctypename;
    private double leftamount;
    private double receivableamount;
    private double receivedamount;
    private long serialid;
    private long settleupid;
    private double thisamount;

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

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    public long getSettleupid() {
        return settleupid;
    }

    public void setSettleupid(long settleupid) {
        this.settleupid = settleupid;
    }

    public double getThisamount() {
        return thisamount;
    }

    public void setThisamount(double thisamount) {
        this.thisamount = thisamount;
    }
}
