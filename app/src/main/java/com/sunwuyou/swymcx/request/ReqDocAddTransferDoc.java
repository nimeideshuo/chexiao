package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.model.TransferDoc;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class ReqDocAddTransferDoc {
    private String builderid;
    private String buildtime;
    private String departmentid;
    private String inwarehouseid;
    private String remark;

    public ReqDocAddTransferDoc(TransferDoc arg2) {
        super();
        this.departmentid = arg2.getDepartmentid();
        this.inwarehouseid = arg2.getInwarehouseid();
        this.remark = arg2.getRemark();
        this.builderid = arg2.getBuilderid();
        this.buildtime = arg2.getBuildtime();
    }

    public ReqDocAddTransferDoc() {
        super();
    }

    public String getBuilderid() {
        return this.builderid;
    }

    public String getBuildtime() {
        return this.buildtime;
    }

    public String getDepartmentid() {
        return this.departmentid;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setBuilderid(String arg1) {
        this.builderid = arg1;
    }

    public void setBuildtime(String arg1) {
        this.buildtime = arg1;
    }

    public void setDepartmentid(String arg1) {
        this.departmentid = arg1;
    }

    public void setInwarehouseid(String arg1) {
        this.inwarehouseid = arg1;
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }


}
