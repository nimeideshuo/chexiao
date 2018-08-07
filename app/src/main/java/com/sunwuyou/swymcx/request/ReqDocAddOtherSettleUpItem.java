package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.utils.Utils;

/**
 * Created by admin on
 * 2018/8/2.
 * content
 */
public class ReqDocAddOtherSettleUpItem {
     private String accountid;
   private Double amount;

    public ReqDocAddOtherSettleUpItem() {
        super();
    }

    public ReqDocAddOtherSettleUpItem(String arg3, Double arg4) {
        super();
        this.accountid = arg3;
        this.amount = Utils.normalizeDouble(arg4);
    }

    public String getAccountId() {
        return this.accountid;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAccountId(String arg1) {
        this.accountid = arg1;
    }

    public void setAmount(Double arg3) {
        this.amount = Utils.normalizeDouble(arg3);
    }
}
