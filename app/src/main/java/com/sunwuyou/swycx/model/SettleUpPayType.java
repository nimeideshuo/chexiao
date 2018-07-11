package com.sunwuyou.swycx.model;

import java.io.Serializable;

/**
 * Created by liupiao on
 * 2018/7/11.
 * content
 */
public class SettleUpPayType implements Serializable {
    private double amount;
    private long id;
    private String paytypeid;
    private String paytypename;
    private String remark;
    private long settleupid;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaytypeid() {
        return paytypeid == null ? "" : paytypeid;
    }

    public void setPaytypeid(String paytypeid) {
        this.paytypeid = paytypeid;
    }

    public String getPaytypename() {
        return paytypename == null ? "" : paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSettleupid() {
        return settleupid;
    }

    public void setSettleupid(long settleupid) {
        this.settleupid = settleupid;
    }

}
