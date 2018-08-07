package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class ReqUsrUserLogin {

    private String key;
    private String password;
    private String userid;

    public ReqUsrUserLogin(String key, String password, String userid) {
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
