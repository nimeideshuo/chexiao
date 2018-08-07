package com.sunwuyou.swymcx.request;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocUploadForSettleUp {
    private String doc;
    private String items;
    private String paytypes;

    public ReqDocUploadForSettleUp() {
        super();
    }

    public ReqDocUploadForSettleUp(String arg1, String arg2, String arg3) {
        super();
        this.doc = arg1;
        this.items = arg2;
        this.paytypes = arg3;
    }

    public String getDoc() {
        return this.doc;
    }

    public String getItems() {
        return this.items;
    }

    public String getPayTypes() {
        return this.paytypes;
    }

    public void setDoc(String arg1) {
        this.doc = arg1;
    }

    public void setItems(String arg1) {
        this.items = arg1;
    }

    public void setPayTypes(String arg1) {
        this.paytypes = arg1;
    }
}
