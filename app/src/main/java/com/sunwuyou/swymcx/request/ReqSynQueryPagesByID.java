package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ReqSynQueryPagesByID {
    private String id;
    private int pagesize;
    private long rversion;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
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
