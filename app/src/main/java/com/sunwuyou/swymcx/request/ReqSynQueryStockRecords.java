package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/17.
 * content
 */

public class ReqSynQueryStockRecords {
    private int pageindex;
    private int pagesize;
    private String warehouseid;

    public ReqSynQueryStockRecords() {
    }

    public ReqSynQueryStockRecords(int pageindex, int pagesize, String warehouseid) {
        this.pageindex = pageindex;
        this.pagesize = pagesize;
        this.warehouseid = warehouseid;
    }

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

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
}
