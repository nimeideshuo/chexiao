package com.sunwuyou.swymcx.request;

/**
 * Created by admin on
 * 2018/7/30.
 * content
 */
public class ReqDocUploadForTransferDoc {
   private String doc;
    private long docid;
    private boolean issubmit;
    private String items;

    public ReqDocUploadForTransferDoc() {
        super();
    }

    public ReqDocUploadForTransferDoc(long arg1, String arg3, String arg4) {
        super();
        this.docid = arg1;
        this.doc = arg3;
        this.items = arg4;
    }

     public String getDoc() {
        return this.doc;
    }

     public long getDocId() {
        return this.docid;
    }

     public boolean getIsSubmit() {
        return this.issubmit;
    }

     public String getItems() {
        return this.items;
    }

     public void setDoc(String arg1) {
        this.doc = arg1;
    }

     public void setDocId(long arg1) {
        this.docid = arg1;
    }

     public void setIsSubmit(boolean arg1) {
        this.issubmit = arg1;
    }

     public void setItems(String arg1) {
        this.items = arg1;
    }

}
