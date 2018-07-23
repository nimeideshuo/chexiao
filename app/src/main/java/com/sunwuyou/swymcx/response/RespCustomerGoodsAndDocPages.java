package com.sunwuyou.swymcx.response;

/**
 * Created by admin
 * 2018/7/15.
 * content
 */

public class RespCustomerGoodsAndDocPages {
    private String customers;
    private int docpages;
    private int goodspages;

    public String getCustomers() {
        return customers == null ? "" : customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public int getDocPages() {
        return docpages;
    }

    public void setDocpages(int docpages) {
        this.docpages = docpages;
    }

    public int getGoodsPages() {
        return goodspages;
    }

    public void setGoodspages(int goodspages) {
        this.goodspages = goodspages;
    }
}
