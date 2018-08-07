package com.sunwuyou.swymcx.request;

import com.sunwuyou.swymcx.model.Customer;

/**
 * Created by admin on
 * 2018/8/1.
 * content
 */
public class ReqCuCustomer extends Customer {
    private String builderid;
    private boolean iscustomer;
    private boolean isignorsamename;
    private boolean isignorsametel;
    private boolean issupplier;

    public ReqCuCustomer() {
        super();
    }


    public String getBuilderId() {
        return this.builderid;
    }

    public boolean getIsCustomer() {
        return this.iscustomer;
    }

    public boolean getIsIgnorSameName() {
        return this.isignorsamename;
    }

    public boolean getIsIgnorSameTel() {
        return this.isignorsametel;
    }

    public boolean getIsSupplier() {
        return this.issupplier;
    }

    public void setBuilderId(String arg1) {
        this.builderid = arg1;
    }

    public void setCustomer(Customer arg3) {
        this.setId(arg3.getId());
        this.setName(arg3.getName());
        this.setPinyin(arg3.getPinyin());
        this.setOrderNo(arg3.getOrderNo());
        this.setContact(arg3.getContact());
        this.setContactMoblie(arg3.getContactMoblie());
        this.setTelephone(arg3.getTelephone());
        this.setRegionId(arg3.getRegionId());
        this.setCustomerTypeId(arg3.getCustomerTypeId());
        this.setVisitLineId(arg3.getVisitLineId());
        this.setDepositBank(arg3.getDepositBank());
        this.setBankingAccount(arg3.getBankingAccount());
        this.setPromotionId(arg3.getPromotionId());
        this.setAddress(arg3.getAddress());
        this.setPriceSystemId(arg3.getPriceSystemId());
        this.setLongitude(arg3.getLongitude());
        this.setLatitude(arg3.getLatitude());
        this.setRemark(arg3.getRemark());
        this.setIsNew(arg3.getIsNew());
        this.setIsFinish(arg3.getIsFinish());
        this.setIsusecustomerprice(arg3.getIsusecustomerprice());
    }

    public void setIsCustomer(boolean arg1) {
        this.iscustomer = arg1;
    }

    public void setIsIgnorSameName(boolean arg1) {
        this.isignorsamename = arg1;
    }

    public void setIsIgnorSameTel(boolean arg1) {
        this.isignorsametel = arg1;
    }

    public void setIsSupplier(boolean arg1) {
        this.issupplier = arg1;
    }
}
