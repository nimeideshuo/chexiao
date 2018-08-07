package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ReqSynQueryRecords {
    private int pageindex;
    private int pagesize;
    private long rversion;

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public long getRversion() {
        return rversion;
    }

    public void setRversion(long rversion) {
        this.rversion = rversion;
    }
}
