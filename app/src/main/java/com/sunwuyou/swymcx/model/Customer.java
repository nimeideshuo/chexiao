package com.sunwuyou.swymcx.model;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1;
    private String address;
    private String bankingaccount;
    private String contact;
    private String contactmoblie;
    private String customertypeid;
    private String depositbank;
    private int exhibitionterm;
    private String id;
    private boolean isfinish;
    private boolean isnew;
    private boolean isusecustomerprice;
    private long lastexhibition;
    private double latitude;
    private double longitude;
    private String name;
    private int orderno;
    private String pinyin;
    private String pricesystemid;
    private String promotionid;
    private String regionid;
    private String remark;
    private String telephone;
    private String visitlineid;

    public Customer() {
        super();
    }

    public Customer(String address, String bankingaccount, String contact, String contactmoblie, String customertypeid, String depositbank, int exhibitionterm, String id, boolean isfinish, boolean isnew, boolean isusecustomerprice, long lastexhibition, double latitude, double longitude, String name, int orderno, String pinyin, String pricesystemid, String promotionid, String regionid, String remark, String telephone, String visitlineid) {
        this.address = address;
        this.bankingaccount = bankingaccount;
        this.contact = contact;
        this.contactmoblie = contactmoblie;
        this.customertypeid = customertypeid;
        this.depositbank = depositbank;
        this.exhibitionterm = exhibitionterm;
        this.id = id;
        this.isfinish = isfinish;
        this.isnew = isnew;
        this.isusecustomerprice = isusecustomerprice;
        this.lastexhibition = lastexhibition;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.orderno = orderno;
        this.pinyin = pinyin;
        this.pricesystemid = pricesystemid;
        this.promotionid = promotionid;
        this.regionid = regionid;
        this.remark = remark;
        this.telephone = telephone;
        this.visitlineid = visitlineid;
    }

    public String getAddress() {
        return this.address;
    }

    public String getBankingAccount() {
        return this.bankingaccount;
    }

    public String getContact() {
        return this.contact;
    }

    public String getContactMoblie() {
        return this.contactmoblie;
    }

    public String getCustomerTypeId() {
        return this.customertypeid;
    }

    public String getDepositBank() {
        return this.depositbank;
    }

    public int getExhibitionTerm() {
        return this.exhibitionterm;
    }

    public String getId() {
        return this.id;
    }

    public boolean getIsFinish() {
        return this.isfinish;
    }

    public boolean getIsNew() {
        return this.isnew;
    }

    public boolean getIsusecustomerprice() {
        return this.isusecustomerprice;
    }

    public long getLastExhibition() {
        return this.lastexhibition;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getName() {
        return this.name;
    }

    public int getOrderNo() {
        return this.orderno;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public String getPriceSystemId() {
        return this.pricesystemid;
    }

    public String getPromotionId() {
        return this.promotionid;
    }

    public String getRegionId() {
        return this.regionid;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String getVisitLineId() {
        return this.visitlineid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBankingAccount(String bankingaccount) {
        this.bankingaccount = bankingaccount;
    }

    public void setContact(String arg1) {
        this.contact = arg1;
    }

    public void setContactMoblie(String contactmoblie) {
        this.contactmoblie = contactmoblie;
    }

    public void setCustomerTypeId(String customertypeid) {
        this.customertypeid = customertypeid;
    }

    public void setDepositBank(String depositbank) {
        this.depositbank = depositbank;
    }

    public void setExhibitionTerm(int exhibitionterm) {
        this.exhibitionterm = exhibitionterm;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsFinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    public void setIsNew(boolean isnew) {
        this.isnew = isnew;
    }

    public void setIsusecustomerprice(boolean isusecustomerprice) {
        this.isusecustomerprice = isusecustomerprice;
    }

    public void setLastExhibition(long arg1) {
        this.lastexhibition = arg1;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String arg1) {
        this.name = arg1;
    }

    public void setOrderNo(int orderno) {
        this.orderno = orderno;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setPriceSystemId(String pricesystemid) {
        this.pricesystemid = pricesystemid;
    }

    public void setPromotionId(String promotionid) {
        this.promotionid = promotionid;
    }

    public void setRegionId(String regionid) {
        this.regionid = regionid;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setVisitLineId(String visitlineid) {
        this.visitlineid = visitlineid;
    }

}