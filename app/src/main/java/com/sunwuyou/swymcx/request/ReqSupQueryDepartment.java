package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class ReqSupQueryDepartment {
    private boolean isonlystore;
    private String userid;

    public ReqSupQueryDepartment(boolean isonlystore, String userid) {
        this.isonlystore = isonlystore;
        this.userid = userid;
    }

    public boolean isonlystore() {
        return isonlystore;
    }

    public void setIsonlystore(boolean isonlystore) {
        this.isonlystore = isonlystore;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
