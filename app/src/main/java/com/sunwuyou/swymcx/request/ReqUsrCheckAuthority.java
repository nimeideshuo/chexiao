package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class ReqUsrCheckAuthority {
    private String authority;
    private String userid;

    public ReqUsrCheckAuthority() {
    }

    public ReqUsrCheckAuthority(String authority, String userid) {
        this.authority = authority;
        this.userid = userid;
    }

    public String getAuthority() {
        return authority == null ? "" : authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
