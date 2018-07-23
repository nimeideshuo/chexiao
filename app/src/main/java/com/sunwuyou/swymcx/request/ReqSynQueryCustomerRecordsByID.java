package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/23.
 * content
 */

public class ReqSynQueryCustomerRecordsByID {

    private String customers;
    private boolean isupdate;
    private int maxorderno;

    public ReqSynQueryCustomerRecordsByID() {
        super();
    }

    public ReqSynQueryCustomerRecordsByID(int maxorderno, boolean isupdate, String customers) {
        super();
        this.maxorderno = maxorderno;
        this.isupdate = isupdate;
        this.customers = customers;
    }

    public String getCustomers() {
        return this.customers;
    }

    public void setCustomers(String arg1) {
        this.customers = arg1;
    }

    public boolean getIsUpdate() {
        return this.isupdate;
    }

    public void setIsUpdate(boolean arg1) {
        this.isupdate = arg1;
    }

    public int getMaxOrderNo() {
        return this.maxorderno;
    }

    public void setMaxOrderNo(int arg1) {
        this.maxorderno = arg1;
    }
}
