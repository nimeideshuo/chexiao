package com.sunwuyou.swycx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */

import java.io.Serializable;

public class Customer
        implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankingaccount() {
        return bankingaccount == null ? "" : bankingaccount;
    }

    public void setBankingaccount(String bankingaccount) {
        this.bankingaccount = bankingaccount;
    }

    public String getContact() {
        return contact == null ? "" : contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactmoblie() {
        return contactmoblie == null ? "" : contactmoblie;
    }

    public void setContactmoblie(String contactmoblie) {
        this.contactmoblie = contactmoblie;
    }

    public String getCustomertypeid() {
        return customertypeid == null ? "" : customertypeid;
    }

    public void setCustomertypeid(String customertypeid) {
        this.customertypeid = customertypeid;
    }

    public String getDepositbank() {
        return depositbank == null ? "" : depositbank;
    }

    public void setDepositbank(String depositbank) {
        this.depositbank = depositbank;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getPinyin() {
        return pinyin == null ? "" : pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPricesystemid() {
        return pricesystemid == null ? "" : pricesystemid;
    }

    public void setPricesystemid(String pricesystemid) {
        this.pricesystemid = pricesystemid;
    }

    public String getPromotionid() {
        return promotionid == null ? "" : promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getRegionid() {
        return regionid == null ? "" : regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTelephone() {
        return telephone == null ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVisitlineid() {
        return visitlineid == null ? "" : visitlineid;
    }

    public void setVisitlineid(String visitlineid) {
        this.visitlineid = visitlineid;
    }

}