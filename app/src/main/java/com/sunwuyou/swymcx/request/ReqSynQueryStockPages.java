package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class ReqSynQueryStockPages {
    private int pagesize;
    private String warehouseid;

    public ReqSynQueryStockPages() {
    }

    public ReqSynQueryStockPages(int pagesize, String warehouseid) {
        this.pagesize = pagesize;
        this.warehouseid = warehouseid;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
