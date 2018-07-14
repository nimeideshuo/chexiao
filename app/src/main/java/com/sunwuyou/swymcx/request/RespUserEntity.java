package com.sunwuyou.swymcx.request;

/**
 * Created by admin
 * 2018/7/14.
 * content
 */

public class RespUserEntity {
    private String goodsclassid;
    private int gpsinterval;
    private String id;
    private boolean isaccountmanager;
    private boolean isavailable;
    private String name;
    private String visitlineid;

    public String getGoodsclassid() {
        return goodsclassid == null ? "" : goodsclassid;
    }

    public void setGoodsclassid(String goodsclassid) {
        this.goodsclassid = goodsclassid;
    }

    public int getGpsinterval() {
        return gpsinterval;
    }

    public void setGpsinterval(int gpsinterval) {
        this.gpsinterval = gpsinterval;
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

    public boolean isavailable() {
        return isavailable;
    }

    public void setIsavailable(boolean isavailable) {
        this.isavailable = isavailable;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitlineid() {
        return visitlineid == null ? "" : visitlineid;
    }

    public void setVisitlineid(String visitlineid) {
        this.visitlineid = visitlineid;
    }
}
