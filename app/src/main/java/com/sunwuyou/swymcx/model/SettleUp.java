package com.sunwuyou.swymcx.model;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class SettleUp {
    private String builderid;
    private String buildername;
    private String buildtime;
    private String departmentid;
    private String departmentname;
    private long id;
    private String isnewobject;
    private boolean issubmit;
    private String objectid;
    private String objectname;
    private String objectoperator;
    private double preference;
    private String remark;
    private String type;

    public SettleUp() {
        super();
    }

    public SettleUp(String builderid, String buildername, String buildtime, String departmentid, String departmentname, long id, String isnewobject, boolean issubmit, String objectid, String objectname, String objectoperator, double preference, String remark, String type) {
        this.builderid = builderid;
        this.buildername = buildername;
        this.buildtime = buildtime;
        this.departmentid = departmentid;
        this.departmentname = departmentname;
        this.id = id;
        this.isnewobject = isnewobject;
        this.issubmit = issubmit;
        this.objectid = objectid;
        this.objectname = objectname;
        this.objectoperator = objectoperator;
        this.preference = preference;
        this.remark = remark;
        this.type = type;
    }
    public String getBuildTime() {
        return this.buildtime;
    }

    public String getBuilderId() {
        return this.builderid;
    }

    public String getBuilderName() {
        return this.buildername;
    }

    public String getDepartmentId() {
        return this.departmentid;
    }

    public String getDepartmentName() {
        return this.departmentname;
    }

    public long getId() {
        return this.id;
    }

    public boolean getIsSubmit() {
        return this.issubmit;
    }

    public String getIsnewobject() {
        return this.isnewobject;
    }

    public String getObjectId() {
        return this.objectid;
    }

    public String getObjectName() {
        return this.objectname;
    }

    public String getObjectOperator() {
        return this.objectoperator;
    }

    public double getPreference() {
        return this.preference;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getType() {
        return this.type;
    }

    public void set0bjectId(String arg1) {
        this.objectid = arg1;
    }

    public void setBuildTime(String arg1) {
        this.buildtime = arg1;
    }

    public void setBuilderId(String arg1) {
        this.builderid = arg1;
    }

    public void setBuilderName(String arg1) {
        this.buildername = arg1;
    }

    public void setDepartmentId(String arg1) {
        this.departmentid = arg1;
    }

    public void setDepartmentName(String arg1) {
        this.departmentname = arg1;
    }

    public void setId(long arg1) {
        this.id = arg1;
    }

    public void setIsSubmit(boolean arg1) {
        this.issubmit = arg1;
    }

    public void setIsnewobject(String arg1) {
        this.isnewobject = arg1;
    }

    public void setObjectName(String arg1) {
        this.objectname = arg1;
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

    public void setType(String arg1) {
        this.type = arg1;
    }
}
