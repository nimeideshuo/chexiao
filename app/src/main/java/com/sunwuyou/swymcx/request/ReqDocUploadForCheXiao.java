package com.sunwuyou.swymcx.request;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocUploadForCheXiao {
    private String doc;
    private long indocid;
    private String itembatchs;
    private String items;
    private long outdocid;
    private String paytypes;
    private long visitid;

    public ReqDocUploadForCheXiao() {
        super();
    }

    public ReqDocUploadForCheXiao(long arg1, long arg3, long arg5, String arg7, String arg8, String arg9, String arg10) {
        super();
        this.visitid = arg1;
        this.outdocid = arg3;
        this.indocid = arg5;
        this.doc = arg7;
        this.items = arg8;
        this.itembatchs = arg9;
        this.paytypes = arg10;
    }

    public String getDoc() {
        return this.doc;
    }

    public long getInDocId() {
        return this.indocid;
    }

    public String getItemBatchs() {
        return this.itembatchs;
    }

    public String getItems() {
        return this.items;
    }

    public long getOutDocId() {
        return this.outdocid;
    }

    public String getPayTypes() {
        return this.paytypes;
    }

    public long getVisitId() {
        return this.visitid;
    }

    public void setDoc(String arg1) {
        this.doc = arg1;
    }

    public void setInDocId(long arg1) {
        this.indocid = arg1;
    }

    public void setItemBatchs(String arg1) {
        this.itembatchs = arg1;
    }

    public void setItems(String arg1) {
        this.items = arg1;
    }

    public void setOutDocId(long arg1) {
        this.outdocid = arg1;
    }

    public void setPayTypes(String arg1) {
        this.paytypes = arg1;
    }

    public void setVisitId(long arg1) {
        this.visitid = arg1;
    }
}
