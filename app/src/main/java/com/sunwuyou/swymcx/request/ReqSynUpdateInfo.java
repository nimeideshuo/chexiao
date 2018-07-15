package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class ReqSynUpdateInfo {


    /**
     * tablename : log_deleterecord
     * pages : 1
     */

    private String tablename;
    private int pages;

    public ReqSynUpdateInfo(String tablename, int pages) {
        this.tablename = tablename;
        this.pages = pages;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
