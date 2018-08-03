package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class TransferDoc {
    private String builderid;
    private String buildername;
    private String buildtime;
    private String departmentid;
    private String departmentname;
    private long id;
    private String inwarehouseid;
    private String inwarehousename;
    private boolean isposted;
    private boolean isupload;
    private String remark;
    private String showid;

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

    public String getInwarehouseid() {
        return inwarehouseid == null ? "" : inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public String getInwarehousename() {
        return inwarehousename == null ? "" : inwarehousename;
    }

    public void setInwarehousename(String inwarehousename) {
        this.inwarehousename = inwarehousename;
    }

    public boolean isposted() {
        return isposted;
    }

    public void setIsposted(boolean isposted) {
        this.isposted = isposted;
    }

    public boolean isIsupload() {
        return isupload;
    }

    public void setIsupload(boolean isupload) {
        this.isupload = isupload;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShowid() {
        return showid == null ? "" : showid;
    }

    public void setShowid(String showid) {
        this.showid = showid;
    }
}
