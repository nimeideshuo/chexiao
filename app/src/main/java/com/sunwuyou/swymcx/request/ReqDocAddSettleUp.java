package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.model.SettleUp;
import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by liupiao on
 * 2018/8/2.
 * content
 */
public class ReqDocAddSettleUp {
    private String builderid;
    private String buildtime;
    private String departmentid;
    private boolean isreceive;
    private String objectid;
    private String objectoperator;
    private Double preference;
    private String remark;

    public ReqDocAddSettleUp(SettleUp arg3) {
        super();
        this.departmentid = arg3.getDepartmentId();
        this.objectid = arg3.getObjectId();
        this.objectoperator = arg3.getObjectOperator();
        this.buildtime = arg3.getBuildTime();
        this.preference = arg3.getPreference();
        this.remark = arg3.getRemark();
        this.isreceive = true;
        this.builderid = arg3.getBuilderId();
    }

    public ReqDocAddSettleUp() {
        super();
    }

    public ReqDocAddSettleUp(String arg3, String arg4, String arg5, String arg6, Double arg7, String arg8, boolean arg9, String arg10) {
        super();
        this.departmentid = arg3;
        this.objectid = arg4;
        this.objectoperator = arg5;
        this.buildtime = arg6;
        this.preference = Utils.normalizeDouble(arg7);
        this.remark = arg8;
        this.isreceive = arg9;
        this.builderid = arg10;
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

    public boolean getIsReceive() {
        return this.isreceive;
    }

    public String getObjectId() {
        return this.objectid;
    }

    public String getObjectOperator() {
        return this.objectoperator;
    }

    public double getPreference() {
        return this.preference.doubleValue();
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

    public void setIsReceive(boolean arg1) {
        this.isreceive = arg1;
    }

    public void setObjectId(String arg1) {
        this.objectid = arg1;
    }

    public void setObjectOperator(String arg1) {
        this.objectoperator = arg1;
    }

    public void setPreference(double arg3) {
        this.preference = Utils.normalizeDouble(arg3);
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }
}
