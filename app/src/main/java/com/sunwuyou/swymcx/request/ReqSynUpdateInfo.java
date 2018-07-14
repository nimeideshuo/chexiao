package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ReqSynUpdateInfo {
    private String tablename;
    private long pages;

    public ReqSynUpdateInfo(String tablename, long pages) {
        this.tablename = tablename;
        this.pages = pages;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public String getTablename() {
        return tablename == null ? "" : tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }


}
