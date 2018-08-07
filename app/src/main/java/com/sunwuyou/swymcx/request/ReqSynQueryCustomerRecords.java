package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/21.
 * content
 */

public class ReqSynQueryCustomerRecords {
    private int pageindex;
    private int pagesize;

    public ReqSynQueryCustomerRecords() {
        super();
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

}
