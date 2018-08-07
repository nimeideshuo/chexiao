package com.sunwuyou.swymcx.request;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class CheXiaoDocID {
    private long indocid;
    private long outdocid;
    private long visitid;
    private long visititemid;

    public CheXiaoDocID() {
        super();
        this.visitid = 0;
        this.outdocid = 0;
        this.indocid = 0;
    }

    public CheXiaoDocID(long arg1, long arg3, long arg5, long arg7) {
        super();
        this.visitid = arg1;
        this.visititemid = arg3;
        this.outdocid = arg5;
        this.indocid = arg7;
    }

    public long getInDocId() {
        return this.indocid;
    }

    public long getOutDocId() {
        return this.outdocid;
    }

    public long getVisitId() {
        return this.visitid;
    }

    public long getVisitItemId() {
        return this.visititemid;
    }

    public void setInDocId(long arg1) {
        this.indocid = arg1;
    }

    public void setOutDocId(long arg1) {
        this.outdocid = arg1;
    }

    public void setVisitId(long arg1) {
        this.visitid = arg1;
    }

    public void setVisitItemId(long arg1) {
        this.visititemid = arg1;
    }
}
