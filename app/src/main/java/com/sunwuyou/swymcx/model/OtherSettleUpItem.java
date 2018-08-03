package com.sunwuyou.swymcx.model;

import java.io.Serializable;

/**
 * Created by admin on
 * 2018/7/11.
 * content
 */
public class OtherSettleUpItem implements Serializable {
    private String accountid;

    public String getAccountid() {
        return accountid == null ? "" : accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAccountname() {
        return accountname == null ? "" : accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getOthersettleupid() {
        return othersettleupid;
    }

    public void setOthersettleupid(long othersettleupid) {
        this.othersettleupid = othersettleupid;
    }

    public long getSerialid() {
        return serialid;
    }

    public void setSerialid(long serialid) {
        this.serialid = serialid;
    }

    private String accountname;
    private double amount;
    private long othersettleupid;
    private long serialid;
}
