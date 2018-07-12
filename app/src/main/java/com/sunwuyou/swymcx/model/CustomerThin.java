package com.sunwuyou.swymcx.model;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */

import java.io.Serializable;

class CustomerThin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String address;
    private String contactmoblie;
    private int exhibitionterm;
    private String id;
    private boolean isfinish;
    private boolean isnew;
    private boolean isusecustomerprice;
    private long lastexhibition;
    private String name;
    private int orderno;
    private String promotionid;
    private String telephone;

    public CustomerThin() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactmoblie() {
        return contactmoblie == null ? "" : contactmoblie;
    }

    public void setContactmoblie(String contactmoblie) {
        this.contactmoblie = contactmoblie;
    }

    public int getExhibitionterm() {
        return exhibitionterm;
    }

    public void setExhibitionterm(int exhibitionterm) {
        this.exhibitionterm = exhibitionterm;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    public boolean isnew() {
        return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

    public boolean isusecustomerprice() {
        return isusecustomerprice;
    }

    public void setIsusecustomerprice(boolean isusecustomerprice) {
        this.isusecustomerprice = isusecustomerprice;
    }

    public long getLastexhibition() {
        return lastexhibition;
    }

    public void setLastexhibition(long lastexhibition) {
        this.lastexhibition = lastexhibition;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getPromotionid() {
        return promotionid == null ? "" : promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}