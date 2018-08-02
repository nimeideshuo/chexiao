package com.sunwuyou.swymcx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class FieldSaleImage {
    private long fieldsaleid;
    private String imagepath;
    private boolean issignature;
    private String remark;
    private long serialid;

    public long getFieldsaleid() {
        return fieldsaleid;
    }

    public void setFieldsaleid(long fieldsaleid) {
        this.fieldsaleid = fieldsaleid;
    }

    public String getImagepath() {
        return imagepath == null ? "" : imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public boolean isIssignature() {
        return issignature;
    }

    public void setIssignature(boolean issignature) {
        this.issignature = issignature;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }
}
