package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.model.SettleUp;

/**
 * Created by liupiao on
 * 2018/8/2.
 * content
 */
public class ReqDocAddOtherSettleUp {
    private String builderid;
    private String buildtime;
    private String departmentid;
    private boolean isprepay;
    private boolean isreceive;
    private String objectid;
    private String objectoperator;
    private String remark;

    public ReqDocAddOtherSettleUp(SettleUp arg3) {
        super();
        this.departmentid = arg3.getDepartmentId();
        this.objectid = arg3.getObjectId();
        this.objectoperator = arg3.getObjectOperator();
        this.buildtime = arg3.getBuildTime();
        this.remark = arg3.getRemark();
        this.isreceive = arg3.getType().equals("64");
        this.isprepay = false;
        this.builderid = arg3.getBuilderId();
    }

    public ReqDocAddOtherSettleUp() {
        super();
    }

    public ReqDocAddOtherSettleUp(String arg1, String arg2, String arg3, String arg4, Double arg5, String arg6, boolean arg7, boolean arg8, String arg9) {
        super();
        this.departmentid = arg1;
        this.objectid = arg2;
        this.objectoperator = arg3;
        this.buildtime = arg4;
        this.remark = arg6;
        this.isreceive = arg7;
        this.isprepay = arg8;
        this.builderid = arg9;
    }

    public String getBuildTime() {
        return this.buildtime;
    }

    public String getBuilderId() {
        return this.builderid;
    }

    public String getDepartmentId() {
        return this.departmentid;
    }

    public boolean getIsPrepay() {
        return this.isprepay;
    }

    public boolean getIsReceive() {
        return this.isreceive;
    }

    public String getObjectId() {
        return this.objectid;
    }

    public String getObjectOperator() {
        return this.objectoperator;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setBuildTime(String arg1) {
        this.buildtime = arg1;
    }

    public void setBuilderId(String arg1) {
        this.builderid = arg1;
    }

    public void setDepartmentId(String arg1) {
        this.departmentid = arg1;
    }

    public void setIsPrepay(boolean arg1) {
        this.isprepay = arg1;
    }

    public void setIsReceive(boolean arg1) {
        this.isreceive = arg1;
    }

    public void setObjectId(String arg1) {
        this.objectid = arg1;
    }

    public void setObjectOperator(String arg1) {
        this.objectoperator = arg1;
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }
}
