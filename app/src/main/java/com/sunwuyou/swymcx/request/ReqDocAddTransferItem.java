package com.sunwuyou.swymcx.request;

/**
 * Created by liupiao on
 * 2018/7/30.
 * content
 */
public class ReqDocAddTransferItem {

    protected String batch;
    protected String goodsid;
    protected double num;
    protected String productiondate;
    protected String remark;
    protected String unitid;
    protected String warehouseid;

    public ReqDocAddTransferItem() {
        super();
    }

    public String getBatch() {
        return this.batch;
    }

    public String getGoodsid() {
        return this.goodsid;
    }

    public double getNum() {
        return this.num;
    }

    public String getProductiondate() {
        return this.productiondate;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getUnitid() {
        return this.unitid;
    }

    public String getWarehouseId() {
        return this.warehouseid;
    }

    public void setBatch(String arg1) {
        this.batch = arg1;
    }

    public void setGoodsid(String arg1) {
        this.goodsid = arg1;
    }

    public void setNum(double arg1) {
        this.num = arg1;
    }

    public void setProductiondate(String arg1) {
        this.productiondate = arg1;
    }

    public void setRemark(String arg1) {
        this.remark = arg1;
    }

    public void setUnitid(String arg1) {
        this.unitid = arg1;
    }

    public void setWarehouseId(String arg1) {
        this.warehouseid = arg1;
    }


}
