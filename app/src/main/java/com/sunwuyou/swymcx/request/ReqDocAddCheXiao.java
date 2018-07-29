package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin
 * 2018/7/25.
 * content
 */

public class ReqDocAddCheXiao {
    private String address;
    private String builderid;
    private String buildtime;
    private double cancelamount;
    private String customerid;
    private String deliverytime;
    private String departmentid;
    private boolean iscarsell;
    private double latitude;
    private double longitude;
    private String mobile;
    private Double preference;
    private String pricesystemid;
    private int printnum;
    private String promotionid;
    private String remark;
    private double saleamount;
    private String settletime;
    private String warehouseid;

    public ReqDocAddCheXiao() {
        super();
        this.deliverytime = Utils.formatDate(Utils.getCurrentTime(false));
        this.settletime = Utils.formatDate(Utils.getCurrentTime(false));
    }

    public ReqDocAddCheXiao(boolean arg5, String arg6, String arg7, String arg8, Double arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, int arg16, double arg17, double arg19, double arg21, double arg23) {
        super();
        this.iscarsell = arg5;
        this.customerid = arg6;
        this.departmentid = arg7;
        this.warehouseid = arg8;
        this.preference = Double.valueOf(Utils.normalizeDouble(arg9.doubleValue()));
        this.pricesystemid = arg10;
        this.promotionid = arg11;
        this.deliverytime = Utils.formatDate(Utils.getCurrentTime(false));
        this.settletime = Utils.formatDate(Utils.getCurrentTime(false));
        this.builderid = arg12;
        this.buildtime = arg13;
        this.mobile = arg14;
        this.remark = arg15;
        this.printnum = arg16;
        this.longitude = arg17;
        this.latitude = arg19;
        this.saleamount = Utils.normalizeDouble(arg21);
        this.cancelamount = Utils.normalizeDouble(arg23);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String arg1) {
        this.address = arg1;
    }

    public String getBuildTime() {
        return this.buildtime;
    }

    public void setBuildTime(String arg1) {
        this.buildtime = arg1;
    }

    public String getBuilderId() {
        return this.builderid;
    }

    public void setBuilderId(String arg1) {
        this.builderid = arg1;
    }

    public double getCancelAmount() {
        return this.cancelamount;
    }

    public void setCancelAmount(double arg3) {
        this.cancelamount = Utils.normalizeDouble(arg3);
    }

    public String getCustomerId() {
        return this.customerid;
    }

    public void setCustomerId(String arg1) {
        this.customerid = arg1;
    }

    public String getDeliveryTime() {
        return this.deliverytime;
    }

    public void setDeliveryTime(String arg1) {
        this.deliverytime = arg1;
    }

    public String getDepartmentId() {
        return this.departmentid;
    }

    public void setDepartmentId(String arg1) {
        this.departmentid = arg1;
    }

    public boolean getIsCarsell() {
        return this.iscarsell;
    }

    public void setIsCarsell(boolean arg1) {
        this.iscarsell = arg1;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double arg1) {
        this.latitude = arg1;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double arg1) {
        this.longitude = arg1;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String arg1) {
        this.mobile = arg1;
    }

    public double getPreference() {
        return this.preference.doubleValue();
    }

    public void setPreference(double arg3) {
        this.preference = Double.valueOf(Utils.normalizeDouble(arg3));
    }

    public String getPriceSystemId() {
        return this.pricesystemid;
    }

    public void setPriceSystemId(String arg1) {
        this.pricesystemid = arg1;
    }

    public int getPrintNum() {
        return this.printnum;
    }

    public void setPrintNum(int arg1) {
        this.printnum = arg1;
    }

    public String getPromotionId() {
        return this.promotionid;
    }

    public void setPromotionId(String arg1) {
        this.promotionid = arg1;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }

    public double getSaleAmount() {
        return this.saleamount;
    }

    public void setSaleAmount(double arg3) {
        this.saleamount = Utils.normalizeDouble(arg3);
    }

    public String getSettleTime() {
        return this.settletime;
    }

    public void setSettleTime(String arg1) {
        this.settletime = arg1;
    }

    public String getWarehouseId() {
        return this.warehouseid;
    }

    public void setWarehouseId(String arg1) {
        this.warehouseid = arg1;
    }
}
