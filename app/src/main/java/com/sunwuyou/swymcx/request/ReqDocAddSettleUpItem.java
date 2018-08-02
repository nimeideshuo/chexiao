package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by liupiao on
 * 2018/8/2.
 * content
 */
public class ReqDocAddSettleUpItem {
    private long docid;
    private String docshowid;
    private String doctime;
    private String doctype;
    private String doctypename;
    private double leftamount;
    private double receivableamount;
    private double thisamount;

    public ReqDocAddSettleUpItem() {
        super();
    }

    public ReqDocAddSettleUpItem(String arg3, long arg4, String arg6, String arg7, String arg8, double arg9, double arg11, double arg13) {
        super();
        this.doctime = arg3;
        this.docid = arg4;
        this.docshowid = arg6;
        this.doctype = arg7;
        this.doctypename = arg8;
        this.receivableamount = Utils.normalizeDouble(arg9);
        this.leftamount = Utils.normalizeDouble(arg11);
        this.thisamount = Utils.normalizeDouble(arg13);
    }

    public long getDocId() {
        return this.docid;
    }

    public String getDocShowId() {
        return this.docshowid;
    }

    public String getDocTime() {
        return this.doctime;
    }

    public String getDocType() {
        return this.doctype;
    }

    public String getDocTypeName() {
        return this.doctypename;
    }

    public double getLeftAmount() {
        return this.leftamount;
    }

    public double getReceivableAmount() {
        return this.receivableamount;
    }

    public double getThisAmount() {
        return this.thisamount;
    }

    public void setDocId(long arg1) {
        this.docid = arg1;
    }

    public void setDocShowId(String arg1) {
        this.docshowid = arg1;
    }

    public void setDocTime(String arg1) {
        this.doctime = arg1;
    }

    public void setDocType(String arg1) {
        this.doctype = arg1;
    }

    public void setDocTypeName(String arg1) {
        this.doctypename = arg1;
    }

    public void setLeftAmount(double arg3) {
        this.leftamount = Utils.normalizeDouble(arg3);
    }

    public void setReceivableAmount(double arg3) {
        this.receivableamount = Utils.normalizeDouble(arg3);
    }

    public void setThisAmount(double arg3) {
        this.thisamount = Utils.normalizeDouble(arg3);
    }
}
