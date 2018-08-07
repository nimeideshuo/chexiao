package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocUpdatePayType {
   private double amount;
    private String paytypeid;

    public ReqDocUpdatePayType() {
        super();
    }

    public ReqDocUpdatePayType(String arg3, double arg4) {
        super();
        this.paytypeid = arg3;
        this.amount = Utils.normalizeDouble(arg4);
    }

    public double getAmount() {
        return this.amount;
    }

    public String getPayTypeId() {
        return this.paytypeid;
    }

    public void setAmount(double arg3) {
        this.amount = Utils.normalizeDouble(arg3);
    }

    public void setPayTypeId(String arg1) {
        this.paytypeid = arg1;
    }
}
