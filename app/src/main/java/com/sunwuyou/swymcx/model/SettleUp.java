package com.sunwuyou.swymcx.model;

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

    public String getBuilderid() {
        return builderid == null ? "" : builderid;
    }

    public void setBuilderid(String builderid) {
        this.builderid = builderid;
    }

    public String getBuildername() {
        return buildername == null ? "" : buildername;
    }

    public void setBuildername(String buildername) {
        this.buildername = buildername;
    }

    public String getBuildtime() {
        return buildtime == null ? "" : buildtime;
    }

    public void setBuildtime(String buildtime) {
        this.buildtime = buildtime;
    }

    public String getDepartmentid() {
        return departmentid == null ? "" : departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname == null ? "" : departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsnewobject() {
        return isnewobject == null ? "" : isnewobject;
    }

    public void setIsnewobject(String isnewobject) {
        this.isnewobject = isnewobject;
    }

    public boolean issubmit() {
        return issubmit;
    }

    public void setIssubmit(boolean issubmit) {
        this.issubmit = issubmit;
    }

    public String getObjectid() {
        return objectid == null ? "" : objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getObjectname() {
        return objectname == null ? "" : objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public String getObjectoperator() {
        return objectoperator == null ? "" : objectoperator;
    }

    public void setObjectoperator(String objectoperator) {
        this.objectoperator = objectoperator;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
