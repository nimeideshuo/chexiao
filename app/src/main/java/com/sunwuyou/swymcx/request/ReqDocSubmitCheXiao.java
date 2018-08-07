package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocSubmitCheXiao {
    private long indocid;
    private long outdocid;
    private String performerid;
    private double saleamount;
    private long visitid;

    public ReqDocSubmitCheXiao() {
        super();
    }

    public ReqDocSubmitCheXiao(long arg3, long arg5, long arg7, double arg9, String arg11) {
        super();
        this.visitid = arg3;
        this.outdocid = arg5;
        this.indocid = arg7;
        this.saleamount = Utils.normalizeDouble(arg9);
        this.performerid = arg11;
    }

    public long getInDocId() {
        return this.indocid;
    }

    public long getOutDocId() {
        return this.outdocid;
    }

    public String getPerformerId() {
        return this.performerid;
    }

    public double getSaleAmount() {
        return this.saleamount;
    }

    public long getVisitId() {
        return this.visitid;
    }

    public void setInDocId(long arg1) {
        this.indocid = arg1;
    }

    public void setOutDocId(long arg1) {
        this.outdocid = arg1;
    }

    public void setPerformerId(String arg1) {
        this.performerid = arg1;
    }

    public void setSaleAmount(double arg3) {
        this.saleamount = Utils.normalizeDouble(arg3);
    }

    public void setVisitId(long arg1) {
        this.visitid = arg1;
    }
}
