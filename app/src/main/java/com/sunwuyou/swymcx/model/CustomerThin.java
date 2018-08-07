package com.sunwuyou.swymcx.model;

/**
 * Created by admin
 * 2018/7/9.
 * content
 */

import java.io.Serializable;

public class CustomerThin implements Serializable {
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

    public String getContactMoblie() {
        return contactmoblie == null ? "" : contactmoblie;
    }

    public void setContactMoblie(String contactmoblie) {
        this.contactmoblie = contactmoblie;
    }

    public int getExhibitionTerm() {
        return exhibitionterm;
    }

    public void setExhibitionTerm(int exhibitionterm) {
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

    public boolean getIsNew() {
        return isnew;
    }

    public void setIsNew(boolean isnew) {
        this.isnew = isnew;
    }

    public boolean isusecustomerprice() {
        return isusecustomerprice;
    }

    public void setIsusecustomerprice(boolean isusecustomerprice) {
        this.isusecustomerprice = isusecustomerprice;
    }

    public long getLastExhibition() {
        return lastexhibition;
    }

    public void setLastExhibition(long lastexhibition) {
        this.lastexhibition = lastexhibition;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNo() {
        return orderno;
    }

    public void setOrderNo(int orderno) {
        this.orderno = orderno;
    }

    public String getPromotionId() {
        return promotionid == null ? "" : promotionid;
    }

    public void setPromotionId(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}