package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class User implements Serializable {
    private String goodsclassid;
    private String id;
    private boolean isaccountmanager;
    private String name;
    private String offlinepassword;
    private String password;
    private String visitlineid;

    public String getGoodsclassid() {
        return goodsclassid == null ? "" : goodsclassid;
    }

    public void setGoodsclassid(String goodsclassid) {
        this.goodsclassid = goodsclassid;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isaccountmanager() {
        return isaccountmanager;
    }

    public void setIsaccountmanager(boolean isaccountmanager) {
        this.isaccountmanager = isaccountmanager;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfflinepassword() {
        return offlinepassword == null ? "" : offlinepassword;
    }

    public void setOfflinepassword(String offlinepassword) {
        this.offlinepassword = offlinepassword;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVisitLineId() {
        return visitlineid == null ? "" : visitlineid;
    }

    public void setVisitlineid(String visitlineid) {
        this.visitlineid = visitlineid;
    }
}
