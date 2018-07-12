package com.sunwuyou.swymcx.model;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class SettleupThin {
    private long id;
    private boolean issubmit;
    private String objectid;
    private String objectname;
    private double preference;
    private double receivableamount;
    private double receivedamount;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean issubmit() {
        return issubmit;
    }

    public void setIssubmit(boolean issubmit) {
        this.issubmit = issubmit;
    }

    public String getObjectid() {
        return objectid == null ? "" : objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getObjectname() {
        return objectname == null ? "" : objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public double getPreference() {
        return preference;
    }

    public void setPreference(double preference) {
        this.preference = preference;
    }

    public double getReceivableamount() {
        return receivableamount;
    }

    public void setReceivableamount(double receivableamount) {
        this.receivableamount = receivableamount;
    }

    public double getReceivedamount() {
        return receivedamount;
    }

    public void setReceivedamount(double receivedamount) {
        this.receivedamount = receivedamount;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
