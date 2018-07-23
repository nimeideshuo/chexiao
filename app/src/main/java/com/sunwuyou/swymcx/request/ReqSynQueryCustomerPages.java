package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/21.
 * content
 */

public class ReqSynQueryCustomerPages {

    private int pagesize;

    public ReqSynQueryCustomerPages() {
        super();
    }

    public ReqSynQueryCustomerPages(int arg1) {
        super();
        this.pagesize = arg1;
    }

    public int getPageSize() {
        return this.pagesize;
    }

    public void setPageSize(int arg1) {
        this.pagesize = arg1;
    }

}
