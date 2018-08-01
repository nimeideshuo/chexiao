package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class Account implements Serializable {
    private String aid;
    private String aname;

    public String getAid() {
        return aid == null ? "" : aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname == null ? "" : aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getParentaccountid() {
        return parentaccountid == null ? "" : parentaccountid;
    }

    public void setParentaccountid(String parentaccountid) {
        this.parentaccountid = parentaccountid;
    }

    private String parentaccountid;

}
