package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class FieldSale implements Serializable {
    private String address;
    private String builderid;
    private String buildername;
    private String buildtime;
    private String customerid;
    private String customername;
    private String departmentid;
    private String departmentname;
    private long id;
    private boolean isnewcustomer;
    private double latitude;
    private double longitude;
    private String mobile;
    private double preference;
    private String pricesystemid;
    private double printnum;
    private String promotionid;
    private String remark;
    private String showid;
    private int status;
    private String warehouseid;
    private String warehousename;

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilderid() {
        return builderid == null ? "" : builderid;
    }

    public void setBuilderid(String builderid) {
        this.builderid = builderid;
    }

    public String getBuildername() {
        return buildername == null ? "" : buildername;
    }

    public void setBuildername(String buildername) {
        this.buildername = buildername;
    }

    public String getBuildtime() {
        return buildtime == null ? "" : buildtime;
    }

    public void setBuildtime(String buildtime) {
        this.buildtime = buildtime;
    }

    public String getCustomerid() {
        return customerid == null ? "" : customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername == null ? "" : customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDepartmentid() {
        return departmentid == null ? "" : departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname == null ? "" : departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIsnewcustomer() {
        return isnewcustomer;
    }

    public void setIsnewcustomer(boolean isnewcustomer) {
        this.isnewcustomer = isnewcustomer;
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

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public String getPricesystemid() {
        return pricesystemid == null ? "" : pricesystemid;
    }

    public void setPricesystemid(String pricesystemid) {
        this.pricesystemid = pricesystemid;
    }

    public double getPrintnum() {
        return printnum;
    }

    public void setPrintnum(double printnum) {
        this.printnum = printnum;
    }

    public String getPromotionid() {
        return promotionid == null ? "" : promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShowid() {
        return showid == null ? "" : showid;
    }

    public void setShowid(String showid) {
        this.showid = showid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWarehouseid() {
        return warehouseid == null ? "" : warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehousename() {
        return warehousename == null ? "" : warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }
}
