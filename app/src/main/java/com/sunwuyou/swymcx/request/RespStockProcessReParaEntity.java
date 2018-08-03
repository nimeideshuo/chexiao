package com.sunwuyou.swymcx.request;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class RespStockProcessReParaEntity {
    private String doctype;
    private String info;
    private boolean issubmitsuccess;

    public RespStockProcessReParaEntity() {
        super();
    }

    public RespStockProcessReParaEntity(boolean arg1, String arg2, String arg3) {
        super();
        this.issubmitsuccess = arg1;
        this.info = arg2;
        this.info = arg3;
    }

    public String getDocType() {
        return this.doctype;
    }

    public String getInfo() {
        return this.info;
    }

    public boolean getIsSubmitSuccess() {
        return this.issubmitsuccess;
    }

    public void setDocType(String arg1) {
        this.doctype = arg1;
    }

    public void setInfo(String arg1) {
        this.info = arg1;
    }

    public void setIsSubmitSuccess(boolean arg1) {
        this.issubmitsuccess = arg1;
    }
}
