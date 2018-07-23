package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/22.
 * content
 */

public class ReqSynQueryRecordsByID {
    private String id;
    private int pageindex;
    private int pagesize;
    private long rversion;

    public ReqSynQueryRecordsByID() {
        super();
    }

    public ReqSynQueryRecordsByID(int pageindex, int pagesize, String id, long rversion) {
        super();
        this.pageindex = pageindex;
        this.pagesize = pagesize;
        this.id = id;
        this.rversion = rversion;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String arg1) {
        this.id = arg1;
    }

    public int getPageIndex() {
        return this.pageindex;
    }

    public void setPageIndex(int arg1) {
        this.pageindex = arg1;
    }

    public int getPageSize() {
        return this.pagesize;
    }

    public void setPageSize(int arg1) {
        this.pagesize = arg1;
    }

    public long getRVersion() {
        return this.rversion;
    }

    public void setRVersion(long arg1) {
        this.rversion = arg1;
    }

}
