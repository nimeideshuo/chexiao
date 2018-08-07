package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSaleForPrint implements Serializable {
    private String buildername;

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

    public String getCustomername() {
        return customername == null ? "" : customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDepartmentname() {
        return departmentname == null ? "" : departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getDoctype() {
        return doctype == null ? "" : doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum() {
        return num == null ? "" : num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPreference() {
        return preference == null ? "" : preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getReceivable() {
        return receivable == null ? "" : receivable;
    }

    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    public String getReceived() {
        return received == null ? "" : received;
    }

    public void setReceived(String received) {
        this.received = received;
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

    public String getSumamount() {
        return sumamount == null ? "" : sumamount;
    }

    public void setSumamount(String sumamount) {
        this.sumamount = sumamount;
    }

    private String buildtime;
    private String customername;
    private String departmentname;
    private String doctype;
    private long id;
    private String num;
    private String preference;
    private String receivable;
    private String received;
    private String remark;
    private String showid;
    private String sumamount;

}
